package ru.denis_strykov.apptwo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.denis_strykov.apptwo.model.Data;
import ru.denis_strykov.apptwo.repository.DataRepository;
import ru.denis_strykov.apptwo.service.DataService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final DataRepository dataRepository;

    @Override
    public void handle(Data data) {
        log.info("Data object {} was saved", data);
        dataRepository.save(data);
    }

    @Override
    public List<Data> getWithBatch(long batchSize) {
        List<Data> data = dataRepository.findAllWithOffset(batchSize);
        if (!data.isEmpty()) {
            dataRepository.incrementOffset(Long.min(batchSize, data.size()));
        }
        return data;
    }

}
