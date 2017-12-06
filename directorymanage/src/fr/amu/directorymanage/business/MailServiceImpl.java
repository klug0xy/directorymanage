package fr.amu.directorymanage.business;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

	protected final Log logger = LogFactory.getLog(getClass());
	private JavaMailSender mailSender;

	@Override
	public JavaMailSender getMailSender() {
		// TODO Auto-generated method stub
		return mailSender;
	}

	@Override
	public void setMailSender(JavaMailSender mailSender) {
		// TODO Auto-generated method stub
		this.mailSender = mailSender;
	}

	@Override
	@PostConstruct
	public void init() {
		logger.info("Init " + this.getClass());
	}

	@Override
	public void sendMail(String emailId, Long personId, String token) {
		// TODO Auto-generated method stub

		// MimeMessage message = mailSender.createMimeMessage();
		// MimeMessageHelper mimeHelper;

		final String username = "hous12256@gmail.com";
		final String password = "passwd007.";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		// Recipient's email ID needs to be mentioned.
		String to = emailId;

		// Sender's email ID needs to be mentioned
		String from = "hous12256@gmail.com";

		// Assuming you are sending email from localhost
		String host = "localhost";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		// Session session = Session.getDefaultInstance(properties);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Password reset for directory manage");
			// Now set the actual message
			message.setContent("<html><body>Hello,<br/><a href="
					+ "'http://localhost:8080/directorymanage/newpassword"
					+ "?personId="+ personId + "&token=" + token
					+ "'>" + "Click here</a> to reset password</body>"
							+ "</html>", "text/html");

			Transport.send(message);

			logger.info("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@PreDestroy
	public void close() {
		logger.info("Close " + this.getClass());

	}

}
