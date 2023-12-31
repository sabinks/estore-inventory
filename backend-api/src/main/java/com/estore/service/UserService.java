package com.estore.service;

import com.estore.dto.ReceiverDto;
import com.estore.dto.RegisterDto;
import com.estore.entity.Role;
import com.estore.entity.User;
import com.estore.mail.VerificationMail;
import com.estore.repository.RoleRepository;
import com.estore.repository.UserRepository;
import com.estore.utility.MyUtilityClass;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
        String verificationToken = getAlphaNumericString(10);
        user.setVerificationToken(verificationToken);
        Role role = roleRepository.findByName("ROLE_CLIENT");

        user.addRole(role);
        user.setCreatedAt(myUtilityClass.currentDateTime());
        user.setUpdatedAt(myUtilityClass.currentDateTime());

        user.setPhone(registerDto.getPhone());
        System.out.println(user);

        User savedUser = userRepository.save(user);

        String verify_url = "/email/verify/" + savedUser.getId() + "/" + verificationToken;

        ReceiverDto receiverDto = new ReceiverDto();
        receiverDto.setEmail(user.getEmail());
        receiverDto.setName(user.getName());
        receiverDto.setUrl(verify_url);

        verificationMail.sendMail(receiverDto);
    }

    static String getAlphaNumericString(int n)
    {

        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
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

}
