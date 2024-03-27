package kz.ctrlbee.service;

import kz.ctrlbee.exception.AuthenticationException;
import kz.ctrlbee.model.dto.SMSRequestType;
import kz.ctrlbee.model.entity.Verification;
import kz.ctrlbee.model.util.GmailSMSSender;
import kz.ctrlbee.repository.UserRepository;
import kz.ctrlbee.repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {
    public static final int SMS_REQUEST_LIMIT = 10;
    public static final int LIMIT_RELIEVE_TIME = 24; //unit: hour
    public static final int EXPIRED_TIME = 30; //unit: minute

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;
    private final GmailSMSSender gmailSMSSender;
    private final Logger log = LogManager.getLogger(EmailVerificationService.class);

    @SneakyThrows
    @Transactional
    public void requestSMS(String email, SMSRequestType smsType){
        if(smsType != SMSRequestType.FORGOT_PASSWORD){
            if (userRepository.findByUsername(email).isPresent()){
                throw new AuthenticationException(String.format("user with %s email already registered", email));
            }
        }

        Verification verification = verificationRepository.findById(email)
                .orElseGet(() -> new Verification(email, null));

        if (verification.getCount() >= SMS_REQUEST_LIMIT){
            var now = getTime();
            var nextRequestTime = verification.getCreationDate().plusHours(LIMIT_RELIEVE_TIME);
            if(now.isBefore(nextRequestTime)){
                throw new AuthenticationException("sms request limit");
            } else {
                verification.setCount(0);
            }
        }

        verification.setCode(generateVerificationCode());
        verification.setCount(verification.getCount() + 1);
        verification.setCreationDate(getTime());
        verification.setValid(true);
        gmailSMSSender.smsSender(verification.getEmail(), verification.getCode());
        verificationRepository.save(verification);
    }

    @Transactional(readOnly = true)
    public boolean isVerificationCodeValid(String email, String verificationCode){
        Optional<Verification> optionalVerification = verificationRepository.findById(email);
        if(optionalVerification.isEmpty()){
            return false;
        }

        Verification verification = optionalVerification.get();

        var now = getTime();
        if(now.isAfter(verification.getCreationDate().plusMinutes(EXPIRED_TIME))){
            verification.setValid(false);
        }

        return verification.isValid() && verification.getCode().equals(verificationCode);
    }

    @Transactional
    public void invalidateVerificationCode(String email){
        Optional<Verification> optionalVerification = verificationRepository.findById(email);

        if (optionalVerification.isEmpty()){
            return;
        }

        Verification verification = optionalVerification.get();
        verification.setValid(false);
        verificationRepository.save(verification);
    }

    public LocalDateTime getTime(){
        return LocalDateTime.now(ZoneId.of("UTC"));
    }
    private String generateVerificationCode(){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i< Verification.VERIFICATION_CODE_LENGTH; i++){
            builder.append((int)(Math.random()*10));
        }
        return builder.toString();
    }
}
