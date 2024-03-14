package kz.ctrlbee.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SMSRequestDTO {
    private String email;
    private SMSRequestType smsRequestType;
}
