package ru.denis_strykov.apptwo.service;

import ru.denis_strykov.apptwo.model.Data;

public interface KafkaDataService {

    void handle(Data data);

}
