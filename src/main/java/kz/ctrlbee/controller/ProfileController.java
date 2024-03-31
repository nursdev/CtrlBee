package kz.ctrlbee.controller;

import kz.ctrlbee.model.dto.ProfileCreateDTO;
import kz.ctrlbee.model.dto.ProfileReadDTO;
import kz.ctrlbee.model.dto.StatusUpdateDTO;
import kz.ctrlbee.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> createProfile(@ModelAttribute ProfileCreateDTO profileCreateDTO,
                                                    Principal principal) throws IOException {
        profileService.createProfile(UUID.fromString(principal.getName()), profileCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/status")
    public ResponseEntity<ProfileReadDTO> updateStatus(@RequestBody StatusUpdateDTO statusUpdateDTO,
                                                       Principal principal) throws IOException {
        return new ResponseEntity<>(profileService.updateStatus(UUID.fromString(principal.getName()), statusUpdateDTO), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<ProfileReadDTO> getProfile(Principal principal) throws IOException {
        return ResponseEntity.ok(profileService.getProfile(UUID.fromString(principal.getName())));
    }


}
