package kz.ctrlbee.service;


import kz.ctrlbee.controller.TaskMessageDTO;
import kz.ctrlbee.model.entity.Task;
import kz.ctrlbee.model.entity.User;
import kz.ctrlbee.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;


    @Transactional
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public List<TaskMessageDTO> getTasks(User user, LocalDate time) {
        List<TaskMessageDTO> tasks = new ArrayList<>();
        for (Task task : taskRepository.findAllByOwnerAndTime(user, time)) {
            tasks.add(new TaskMessageDTO(
                    task.getMessage(),
                    task.getTime()
            ));
        }
        return tasks;
    }
}
