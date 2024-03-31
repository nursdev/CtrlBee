package kz.ctrlbee.service;


import kz.ctrlbee.model.dto.ProfileCreateDTO;
import kz.ctrlbee.model.dto.ProfileReadDTO;
import kz.ctrlbee.model.entity.User;
import kz.ctrlbee.model.util.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserService userService;

    @Transactional
    public void createProfile(UUID userId, ProfileCreateDTO profileCreateDTO) throws IOException {
        User user = userService.findById(userId);
        user.setCountry(profileCreateDTO.getCountry());
        user.setBirthDay(profileCreateDTO.getDateOfBirthday());
        user.setUsername(profileCreateDTO.getUsername());
        user.setName(profileCreateDTO.getName());

        String subFolder = "/profile";
        MultipartFile image = profileCreateDTO.getProfileImage();
        String hashImage = FileManager.hashFile(image.getInputStream());
        String path = FileManager.saveFile(
                image.getInputStream(),
                subFolder.concat("/".concat(hashImage.substring(0, 12))),
                hashImage.substring(2)
        );

        user.setProfileImagePath(path);
        userService.updateUser(user);
    }

    @Transactional(readOnly = true)
    public ProfileReadDTO getProfile(UUID userId) {
        var user = userService.findById(userId);
        return new ProfileReadDTO(user);
    }
}
