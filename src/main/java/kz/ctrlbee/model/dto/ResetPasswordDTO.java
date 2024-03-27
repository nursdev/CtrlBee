package kz.ctrlbee.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {

    private String email;
    private String password;
    private String verificationCode;
}
