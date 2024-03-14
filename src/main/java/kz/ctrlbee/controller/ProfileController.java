package kz.ctrlbee.controller;

import kz.ctrlbee.model.dto.ProfileCreateDTO;
import kz.ctrlbee.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> createProfile(@ModelAttribute ProfileCreateDTO profileCreateDTO,
                                                    Principal principal) {
        System.out.println("=============================");
        System.out.println(UUID.fromString(principal.getName()));
        System.out.println("=============================");
        profileService.createProfile(UUID.fromString(principal.getName()), profileCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
