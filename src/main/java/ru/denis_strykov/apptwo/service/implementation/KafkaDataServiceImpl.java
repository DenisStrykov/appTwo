package ru.denis_strykov.apptwo.service.implementation;

import org.springframework.stereotype.Service;
import ru.denis_strykov.apptwo.model.Data;
import ru.denis_strykov.apptwo.service.KafkaDataService;

@Service
public class KafkaDataServiceImpl implements KafkaDataService {

    @Override
    public void handle(Data data) {
        System.out.println("Data object is received: " + data.toString());
    }

}
