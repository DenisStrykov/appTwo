package ru.denis_strykov.apptwo.service.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;
import ru.denis_strykov.apptwo.config.LocalDateTimeDeserializer;
import ru.denis_strykov.apptwo.model.Data;
import ru.denis_strykov.apptwo.service.KafkaDataReceiver;
import ru.denis_strykov.apptwo.service.KafkaDataService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KafkaDataReceiverImpl implements KafkaDataReceiver {

    private final KafkaReceiver<String, Object> receiver;
    private final LocalDateTimeDeserializer localDateTimeDeserializer;
    private final KafkaDataService kafkaDataService;

    @PostConstruct
    private void init() {
        fetch();
    }

    @Override
    public void fetch() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(
                        LocalDateTime.class,
                        localDateTimeDeserializer
                )
                .create();
        receiver.receive()
                .subscribe(r -> {
                    Data data = gson.fromJson(
                            r.value().toString(),
                            Data.class
                    );
                    kafkaDataService.handle(data);
                    r.receiverOffset().acknowledge();
                });
    }

}
