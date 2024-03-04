package kz.ctrlbee.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDTO {

    private String username;
    private String password;
    private String verificationCode;
}
