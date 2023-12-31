package com.estore.mail;

import com.estore.dto.ReceiverDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetPassword {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMail(ReceiverDto receiverDto) throws Exception {

        receiverDto.setSubject("Password Reset");
        String htmlContent = "<h1>Please click link below to reset your password</h1>" +
                "<a href='" + receiverDto.getUrl()  +"'>Reset Password</a>" +
                "<p></p>";
        receiverDto.setBody(htmlContent);
        rabbitTemplate.convertAndSend("estore_exchange", "estore_routingkey", receiverDto);
    }
}
