package com.sendmail.consumer;

import com.sendmail.dto.ReceiverDto;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendMail {
	@Autowired
	JavaMailSender javaMailSender;
	@Value("${spring.mail.sender}")
	private String mailSender;
	@RabbitListener(queues = "estore_queue")
	public void consumeAdminSendMailQueue(ReceiverDto receiverDto) throws Exception {
		System.out.println(receiverDto);
		System.out.println("Sending mail: " + receiverDto.getName());
		MimeMessage message = javaMailSender.createMimeMessage();
		message.setFrom(new InternetAddress(mailSender));
		message.setRecipients(MimeMessage.RecipientType.TO, receiverDto.getEmail());
		message.setSubject(receiverDto.getSubject());
		message.setContent(receiverDto.getBody(), "text/html; charset=utf-8");
		javaMailSender.send(message);
	}
}
