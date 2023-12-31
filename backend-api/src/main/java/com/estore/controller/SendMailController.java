package com.estore.controller;

import com.estore.dto.ReceiverDto;
import com.estore.mail.VerificationMail;
import com.estore.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMailController {
	@Autowired
	VerificationMail verificationMail;

	@GetMapping("/api/send-mail")
	public void sendMail() throws Exception {
		ReceiverDto receiverDto = new ReceiverDto();
		receiverDto.setEmail("mail-test@mail.com");
		receiverDto.setName("Mail Test");
		receiverDto.setUrl("http://google.com");
		verificationMail.sendMail(receiverDto);
	}
}
