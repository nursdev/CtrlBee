package kz.ctrlbee.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import kz.ctrlbee.model.entity.Room;
import kz.ctrlbee.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RoomDetailDTO {
    private UUID id;
    private String name;
    private String ownerUserName;
    private List<UserDTO> members = new ArrayList<>();
    public RoomDetailDTO(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.ownerUserName = room.getOwner().getUsername();
        room.getMembers().forEach(member -> this.members.add(new UserDTO(member)));
    }
}
