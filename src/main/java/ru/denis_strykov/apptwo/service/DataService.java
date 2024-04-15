package ru.denis_strykov.apptwo.service;

import ru.denis_strykov.apptwo.model.Data;

import java.util.List;

public interface DataService {

    void handle(Data data);

    List<Data> getWithBatch(long batchSize);

}
