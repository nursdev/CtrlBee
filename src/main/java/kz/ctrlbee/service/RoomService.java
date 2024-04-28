package kz.ctrlbee.service;


import kz.ctrlbee.exception.AuthenticationException;
import kz.ctrlbee.exception.NotFoundException;
import kz.ctrlbee.exception.PasswordNotDeclaredException;
import kz.ctrlbee.model.dto.RoomCreateDTO;
import kz.ctrlbee.model.dto.RoomDetailDTO;
import kz.ctrlbee.model.entity.Room;
import kz.ctrlbee.model.dto.RoomDTO;
import kz.ctrlbee.model.entity.User;
import kz.ctrlbee.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<RoomDTO> getRooms(UUID userId, String filter) {
        User user = userService.findById(userId);
        List<RoomDTO> roomDTOS = new ArrayList<>();
        if (filter != null && filter.equalsIgnoreCase("all")) {
            user.getJoinedRooms().forEach(room -> roomDTOS.add(new RoomDTO(room)));
        } else {
            user.getRooms().forEach(room -> roomDTOS.add(new RoomDTO(room)));
        }
        return roomDTOS;
    }

    @Transactional
    public RoomDTO createRoom(UUID userId, RoomCreateDTO roomDTO, String password) {
        User user = userService.findById(userId);
        if (roomDTO.getIsPrivate() && password == null) {
            throw new PasswordNotDeclaredException("password must be given");
        }
        var room = Room.builder()
                .name(roomDTO.getName())
                .isPrivate(roomDTO.getIsPrivate())
                .password(password)
                .members(new ArrayList<>())
                .owner(user)
                .build();
        room.getMembers().add(user);
        var savedRoom = roomRepository.save(room);
        user.getRooms().add(savedRoom);
        user.getJoinedRooms().add(savedRoom);
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


    @Transactional
    public void joinRoom(UUID userId, UUID roomId, String password) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException(
                String.format("room with %s not found", roomId)
        ));
        User user = userService.findById(userId);
        if (room.getIsPrivate()) {
            if (room.getPassword().equals(password)) {
                user.joinRoom(room);
            } else {
                throw new AuthenticationException("rooms password incorrect");
            }
        } else {
            user.joinRoom(room);
        }
    }

    @Transactional(readOnly = true)
    public RoomDetailDTO getRoom(UUID roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException(String.format("room with %s not found", roomId)));
        return new RoomDetailDTO(room);
    }
}
