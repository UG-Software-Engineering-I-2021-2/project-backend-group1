package business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String[] to, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(body, true); // Use this or above line.
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("proyectocompetencias@gmail.com");
        mailSender.send(mimeMessage);
    }
}
