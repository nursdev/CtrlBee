package kz.ctrlbee.model.dto;


import kz.ctrlbee.model.entity.User;
import kz.ctrlbee.model.util.FileManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
public class ProfileReadDTO {


    private String name;
    private String username;
    private byte[] profileImage;
    private String status;
    public ProfileReadDTO(User user) {
        this.name = user.getName();
        this.username = user.getUsername();
        try {
            this.profileImage = FileManager.getFile(user.getProfileImagePath());
        } catch (IOException ignored) {}
        this.status = user.getStatus();
    }
}
