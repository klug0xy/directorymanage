package fr.amu.directorymanage.business;

import org.springframework.mail.javamail.JavaMailSender;

public interface MailService {
	
	public void init();
	public JavaMailSender getMailSender();
	public void setMailSender(JavaMailSender mailSender);
	public void sendMail(String emailId, Long personId, String token);
	public void close();

}
