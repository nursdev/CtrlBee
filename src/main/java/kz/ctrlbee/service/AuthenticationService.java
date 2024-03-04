package kz.ctrlbee.service;

import kz.ctrlbee.model.dto.SignInRequestDTO;
import kz.ctrlbee.model.dto.SignUpRequestDTO;
import kz.ctrlbee.model.dto.TokenResponseDTO;
import kz.ctrlbee.model.entity.User;
import kz.ctrlbee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final EmailVerificationService verificationService;


    @SneakyThrows
    public TokenResponseDTO signIn(SignInRequestDTO signInRequestDTO) {
        User user = userRepository.findByUsername(signInRequestDTO.getUsername())
                .orElseThrow(() -> new NotFoundException(
                        String.format("user with phoneNumber %s not found", signInRequestDTO.getUsername())));

        var access = jwtService.generateToken(user);
        var refresh = jwtService.generateRefreshToken(new HashMap<>(), user);
        if(!passwordEncoder.matches(signInRequestDTO.getPassword(), user.getPassword())){
            throw new IllegalAccessException("password is in correct");
        }
        TokenResponseDTO tokens = new TokenResponseDTO();
        tokens.setAccessToken(access);
        tokens.setRefreshToken(refresh);
        return tokens;
    }

    @SneakyThrows
    @Transactional
    public void createUser(SignUpRequestDTO signUpRequestDTO) {
        if (!verificationService.isVerificationCodeValid(
                signUpRequestDTO.getUsername(),
                signUpRequestDTO.getVerificationCode()
        )) {
            throw new IllegalAccessException("sms verification code incorrect");
        }

        if (userRepository.findByUsername(signUpRequestDTO.getUsername()).isPresent()) {
            throw new IllegalAccessException("there is already a seller with email");
        }

        User user = new User();
        user.setUsername(signUpRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
        userRepository.save(user);
        verificationService.invalidateVerificationCode(signUpRequestDTO.getUsername());
    }
}
