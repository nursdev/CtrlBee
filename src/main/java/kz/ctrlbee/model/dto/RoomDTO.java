package kz.ctrlbee.model.dto;

import kz.ctrlbee.model.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class RoomDTO {
    private String name;
    private Boolean isPrivate;

    public RoomDTO(Room room) {
        this.name = room.getName();
        this.isPrivate = room.getIsPrivate();
    }
}
