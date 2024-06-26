package kz.ctrlbee.model.dto;

import kz.ctrlbee.model.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RoomDTO {
    private UUID id;
    private String name;
    private Boolean isPrivate;
    private Integer membersCount;

    public RoomDTO(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.isPrivate = room.getIsPrivate();
        this.membersCount = room.getMembers().size();
    }
}
