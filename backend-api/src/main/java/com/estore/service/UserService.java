package com.estore.service;

import com.estore.dto.RegisterDto;
import com.estore.entity.Role;
import com.estore.entity.User;
import com.estore.repository.RoleRepository;
import com.estore.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;


    @Transactional
    public void registerUser(RegisterDto registerDto) throws Exception{
        List<User> users = userRepository.findByEmail(registerDto.getEmail());
        if(users != null){
            throw new EntityExistsException("User email registered!");
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
        user.setCreatedAt(CurrentDateTime());
        user.setUpdatedAt(CurrentDateTime());

        user.setPhone(registerDto.getPhone());
        System.out.println(user);
        userRepository.save(user);
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

    static String CurrentDateTime(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newDate= formatter.format(date);
        return newDate;
    }

}
