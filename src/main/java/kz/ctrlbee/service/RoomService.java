package kz.ctrlbee.service;


import kz.ctrlbee.model.entity.Room;
import kz.ctrlbee.model.dto.RoomDTO;
import kz.ctrlbee.model.entity.User;
import kz.ctrlbee.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<RoomDTO> getRooms(UUID userId) {
        User user = userService.findById(userId);
        List<RoomDTO> roomDTOS = new ArrayList<>();
        for (Room room : user.getRooms()) {
            roomDTOS.add(new RoomDTO(room));
        }
        return roomDTOS;
    }

    @Transactional
    public RoomDTO createRoom(UUID userId, RoomDTO roomDTO) {
        User user = userService.findById(userId);
        var room = Room.builder()
                .name(roomDTO.getName())
                .isPrivate(roomDTO.getIsPrivate())
                .owner(user)
                .members(Arrays.asList(user))
                .build();
        var savedRoom = roomRepository.save(room);
        user.getRooms().add(savedRoom);
        userService.updateUser(user);
        return new RoomDTO(room);
    }

    @Transactional(readOnly = true)
    public List<RoomDTO> searchRoom(String searchParam) {
        List<Room> rooms = roomRepository.findAllByNameContainingIgnoreCase(searchParam);
        List<RoomDTO> roomDTOS = new ArrayList<>();
        for (Room room : rooms) {
            roomDTOS.add(new RoomDTO(room));
        }
        return roomDTOS;
    }
}
