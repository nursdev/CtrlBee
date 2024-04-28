package kz.ctrlbee.model.dto;


import kz.ctrlbee.model.entity.User;
import kz.ctrlbee.model.util.FileManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String username;
    private byte[] profileImage;
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        try {
            this.profileImage = FileManager.getFile(user.getProfileImagePath());
        } catch (Exception ignored) {}
    }
}
