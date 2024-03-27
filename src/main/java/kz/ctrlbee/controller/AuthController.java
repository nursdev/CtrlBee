package kz.ctrlbee.controller;

import jakarta.servlet.http.HttpServletResponse;
import kz.ctrlbee.model.dto.ResetPasswordDTO;
import kz.ctrlbee.model.dto.SignInRequestDTO;
import kz.ctrlbee.model.dto.SignUpRequestDTO;
import kz.ctrlbee.model.dto.TokenResponseDTO;
import kz.ctrlbee.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signin")
    public ResponseEntity<TokenResponseDTO> signIn(@RequestBody SignInRequestDTO signInRequestDTO) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequestDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        authenticationService.createUser(signUpRequestDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO){
        authenticationService.resetPassword(resetPasswordDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
