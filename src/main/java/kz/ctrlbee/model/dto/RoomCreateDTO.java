package kz.ctrlbee.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomCreateDTO {
    private String name;
    private Boolean isPrivate;
}
