package kz.ctrlbee.model.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
public class PostCreateDTO {

    private String description;
    private MultipartFile media;

}
