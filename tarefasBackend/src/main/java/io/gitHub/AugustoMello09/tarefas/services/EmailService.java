package io.gitHub.AugustoMello09.tarefas.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import io.gitHub.AugustoMello09.tarefas.domain.dtos.EmailDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	@Value(value = "${spring.mail.username}")
	private String emailFrom;

	private final JavaMailSender emailSender;

	@Async
	public void sendEmail(String email, String usuarioName) {
		EmailDTO emailModel = new EmailDTO();
		emailModel.setSendDateEmail(LocalDateTime.now());
		emailModel.setEmailFrom(emailFrom);
		emailModel.setEmailTo(email);
		emailModel.setSubject("Nova tarefa criada! ");
		emailModel.setText("Olá " + usuarioName + " Sua tarefa foi criada com sucesso. ");

		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(emailModel.getEmailTo());
		message.setSubject(emailModel.getSubject());
		message.setText(emailModel.getText());
		emailSender.send(message);
	}
}
