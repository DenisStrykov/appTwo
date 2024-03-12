package ru.denis_strykov.apptwo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.denis_strykov.apptwo.model.Data;

public interface DataRepository extends JpaRepository<Data, Long> {
}
