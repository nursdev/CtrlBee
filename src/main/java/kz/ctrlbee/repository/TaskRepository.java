package kz.ctrlbee.repository;

import kz.ctrlbee.model.entity.Task;
import kz.ctrlbee.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {


    List<Task> findAllByOwnerAndTime(User owner, LocalDate time);
}
