package kz.ctrlbee.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SMSVerificationDTO {
    private String email;
    private String verificationCode;
}
