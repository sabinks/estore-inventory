package com.estore.service;

import com.estore.dto.ReceiverDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMailService {

	@Autowired
	RabbitTemplate rabbitTemplate;

	public void sendMail() throws Exception {

		ReceiverDto receiverDto = new ReceiverDto();
		receiverDto.setEmail("test-name@mail.com");
		receiverDto.setName("Test Name");
		receiverDto.setSubject("Test Mail");
		String htmlContent = "<h1>This is a test Spring Boot email</h1>" +
					"<p>It can contain <strong>HTML</strong> content.</p>";
		receiverDto.setBody(htmlContent);
		rabbitTemplate.convertAndSend("estore_exchange", "estore_routingkey", receiverDto);
	}
}

