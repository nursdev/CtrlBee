package kz.ctrlbee.service;


import kz.ctrlbee.controller.TaskMessageDTO;
import kz.ctrlbee.model.entity.Task;
import kz.ctrlbee.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CalendarService {


    private final UserService userService;
    private final TaskService taskService;
    @Transactional
    public void createTask(UUID userId, TaskMessageDTO todoMessageDTO) {
        User user = userService.findById(userId);


        var task = Task.builder()
                .message(todoMessageDTO.getMessage())
                .owner(user)
                .time(todoMessageDTO.getTime())
                .build();

        user.getTasks().add(taskService.createTask(task));
        userService.updateUser(user);
    }

    @Transactional(readOnly = true)
    public List<TaskMessageDTO>
    getTasks(UUID userId, LocalDate time) {
        User user = userService.findById(userId);
        return taskService.getTasks(user, time);
    }
}
