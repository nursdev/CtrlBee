package kz.ctrlbee.model.dto;


import kz.ctrlbee.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileReadDTO {


    private String name;
    private String username;
    private String imagePath;
    private String status;
    public ProfileReadDTO(User user) {
        this.name = user.getName();
        this.username = user.getUsername();
        this.imagePath = user.getProfileImagePath();
        this.status = user.getStatus();
    }
}
