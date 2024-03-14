package kz.ctrlbee.controller;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskMessageDTO {

    private String message;
    private LocalDate time;

    public TaskMessageDTO(String message, LocalDate time) {
        this.message = message;
        this.time = time;
    }
}
