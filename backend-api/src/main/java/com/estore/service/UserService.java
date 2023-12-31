package com.estore.service;

import com.estore.dto.ReceiverDto;
import com.estore.dto.RegisterDto;
import com.estore.dto.user.ForgotPasswordDto;
import com.estore.dto.user.ResetPasswordDto;
import com.estore.entity.Role;
import com.estore.entity.User;
import com.estore.mail.ResetPassword;
import com.estore.mail.VerificationMail;
import com.estore.repository.RoleRepository;
import com.estore.repository.UserRepository;
import com.estore.utility.MyUtilityClass;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MyUtilityClass myUtilityClass;
    @Autowired
    VerificationMail verificationMail;

    @Autowired
    ResetPassword resetPassword;

    @Value("${spring.app.url}")
    private String app_url;

    @Transactional
    public void registerUser(RegisterDto registerDto) throws Exception{
        List<User> users = userRepository.findByEmail(registerDto.getEmail());
        if(users.size() > 0){
            throw new EntityExistsException("User email already registered!");
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setActive(false);
        user.setEmailVerifiedAt("");
        String verificationToken = myUtilityClass.getAlphaNumericString(10);
        user.setVerificationToken(verificationToken);
        Role role = roleRepository.findByName("ROLE_CLIENT");

        user.addRole(role);
        user.setCreatedAt(myUtilityClass.currentDateTime());
        user.setUpdatedAt(myUtilityClass.currentDateTime());

        user.setPhone(registerDto.getPhone());
        System.out.println(user);

        User savedUser = userRepository.save(user);

        String verify_url = app_url +"/email/verify/" + savedUser.getId() + "/" + verificationToken;

        ReceiverDto receiverDto = new ReceiverDto();
        receiverDto.setEmail(user.getEmail());
        receiverDto.setName(user.getName());
        receiverDto.setUrl(verify_url.toString());

        verificationMail.sendMail(receiverDto);
    }

    @Transactional
    public void verifyEmail(Long id, String verification_token) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        if(user.get().getVerificationToken().matches("")){
            throw new Exception("Email verified, if issue, please reset password");
        }
        if(!user.get().getVerificationToken().matches(verification_token)){
            throw new Exception("Provided token mismatch");
        }

        user.get().setVerificationToken("");
        user.get().setActive(true);
        user.get().setEmailVerifiedAt(myUtilityClass.currentDateTime());
        user.get().setUpdatedAt(myUtilityClass.currentDateTime());

        userRepository.save(user.get());
    }

    public void forgotPassword(ForgotPasswordDto forgotPasswordDto) throws Exception {
        String email = forgotPasswordDto.getEmail();
        List<User> users = userRepository.findByEmail(email);
        if(users.isEmpty()){
            throw new EntityNotFoundException("User email not registered, please check email address.");
        }
        String resetToken = myUtilityClass.getAlphaNumericString(15);
        users.get(0).setResetToken(resetToken);
        users.get(0).setUpdatedAt(myUtilityClass.currentDateTime());
        userRepository.save(users.get(0));

        String reset_url = app_url + "/reset-password/" + users.get(0).getId() + "/" + resetToken;
        ReceiverDto receiverDto = new ReceiverDto();
        receiverDto.setName(users.get(0).getName());
        receiverDto.setEmail(users.get(0).getEmail());
        receiverDto.setUrl(reset_url);

        resetPassword.sendMail(receiverDto);
    }

    public void resetPassword(ResetPasswordDto resetPasswordDto) throws Exception {
        Optional<User> user = userRepository.findById(resetPasswordDto.getId());
        if(user.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        if(!user.get().getResetToken().matches(resetPasswordDto.getResetToken())){
            throw new Exception("Provided reset token mismatch");
        }
        user.get().setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
        user.get().setResetToken("");
        user.get().setUpdatedAt(myUtilityClass.currentDateTime());

        userRepository.save(user.get());
    }
}
