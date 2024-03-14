package kz.ctrlbee.controller;


import kz.ctrlbee.service.CalendarService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/calendars")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;


    @PostMapping
    public ResponseEntity<HttpStatus> createTask(@RequestBody TaskMessageDTO todoMessageDTO,
                                                 Principal principal) {
        calendarService.createTask(UUID.fromString(principal.getName()), todoMessageDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<TaskMessageDTO>> getTasks(@RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                         LocalDate time,
                                                         Principal principal) {
        return ResponseEntity.ok(calendarService.getTasks(UUID.fromString(principal.getName()), time));
    }
}
