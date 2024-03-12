package ru.denis_strykov.apptwo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.denis_strykov.apptwo.model.Data;
import ru.denis_strykov.apptwo.repository.DataRepository;
import ru.denis_strykov.apptwo.service.KafkaDataService;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaDataServiceImpl implements KafkaDataService {

    private final DataRepository dataRepository;

    @Override
    public void handle(Data data) {
        log.info("Data object {} was saved: ", data);
        dataRepository.save(data);
    }

}
