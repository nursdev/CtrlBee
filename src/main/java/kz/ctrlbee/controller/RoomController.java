package kz.ctrlbee.controller;


import kz.ctrlbee.model.dto.RoomCreateDTO;
import kz.ctrlbee.model.dto.RoomDTO;
import kz.ctrlbee.model.dto.RoomDetailDTO;
import kz.ctrlbee.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    @GetMapping
    public ResponseEntity<List<RoomDTO>> getRooms(Principal principal) {
        return ResponseEntity.ok(roomService.getRooms(UUID.fromString(principal.getName())));
    }

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(Principal principal,
                                              @RequestBody RoomCreateDTO roomDTO,
                                              @RequestParam(value = "password",required = false)
                                              String password) {
        return new ResponseEntity<>(roomService.createRoom(UUID.fromString(principal.getName()),roomDTO, password), HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RoomDTO>> getRooms(@RequestParam("q") String searchParam) {
        return ResponseEntity.ok(roomService.searchRoom(searchParam));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDetailDTO> getRoom(@PathVariable UUID roomId) {
        return ResponseEntity.ok(roomService.getRoom(roomId));
    }

    @PostMapping("/join/{roomId}")
    public void joinRoom(Principal principal,@PathVariable("roomId") UUID roomId,
                         @RequestParam(value = "password", required = false) String password) {
        roomService.joinRoom(UUID.fromString(principal.getName()), roomId, password);
    }

}
