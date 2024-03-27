package kz.ctrlbee.service;

import kz.ctrlbee.exception.AuthenticationException;
import kz.ctrlbee.exception.NotFoundException;
import kz.ctrlbee.exception.UserInputException;
import kz.ctrlbee.model.dto.ResetPasswordDTO;
import kz.ctrlbee.model.dto.SignInRequestDTO;
import kz.ctrlbee.model.dto.SignUpRequestDTO;
import kz.ctrlbee.model.dto.TokenResponseDTO;
import kz.ctrlbee.model.entity.User;
import kz.ctrlbee.model.enumuration.Role;
import kz.ctrlbee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final EmailVerificationService verificationService;



    @Transactional(readOnly = true)
    public TokenResponseDTO signIn(SignInRequestDTO signInRequestDTO) {
        User user = userRepository.findByEmail(signInRequestDTO.getEmail())
                .orElseThrow(() -> new NotFoundException(
                        String.format("user with email %s not found", signInRequestDTO.getEmail())));

        var access = jwtService.generateToken(user);
        var refresh = jwtService.generateRefreshToken(new HashMap<>(), user);
        if(!passwordEncoder.matches(signInRequestDTO.getPassword(), user.getPassword())){
            throw new AuthenticationException("password is incorrect");
        }
        TokenResponseDTO tokens = new TokenResponseDTO();
        tokens.setAccessToken(access);
        tokens.setRefreshToken(refresh);
        return tokens;
    }


    @Transactional
    public void createUser(SignUpRequestDTO signUpRequestDTO) {
        if (!verificationService.isVerificationCodeValid(
                signUpRequestDTO.getEmail(),
                signUpRequestDTO.getVerificationCode()
        )) {
            throw new AuthenticationException("sms verification code incorrect");
        }

        if (userRepository.findByEmail(signUpRequestDTO.getEmail()).isPresent()) {
            throw new AuthenticationException(String.format("%s email already registered", signUpRequestDTO.getEmail()));
        }

        User user = new User();
        user.setEmail(signUpRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        verificationService.invalidateVerificationCode(signUpRequestDTO.getEmail());
    }

    @Transactional
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        String newPassword = resetPasswordDTO.getPassword();
        String verificationCode = resetPasswordDTO.getVerificationCode();
        String email = resetPasswordDTO.getEmail();

        if(!verificationService.isVerificationCodeValid(email, verificationCode)){
            throw new AuthenticationException("sms verification code incorrect");
        }

        if(newPassword == null || newPassword.isEmpty()){
            throw new UserInputException("Password should not be empty");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("user with email %s not found", email)));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.saveAndFlush(user);
        verificationService.invalidateVerificationCode(email);
    }
}
