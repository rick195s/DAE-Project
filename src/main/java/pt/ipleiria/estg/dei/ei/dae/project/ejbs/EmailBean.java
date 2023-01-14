package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Stateless
public class EmailBean {

    @Resource(name = "java:/jboss/mail/fakeSMTP")
    private Session mailSession;

    public void send(String to, String subject, String text) throws MessagingException {
        var msg = new MimeMessage(mailSession);

        // Adjust the recipients. Here we have only one recipient.
        // The recipient's address must be an object of the InternetAddress class.
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

        // Set the message's subject
        msg.setSubject(subject);

        // Insert the message's body
        msg.setText(text);

        // Adjust the date of sending the message
        var timeStamp = new Date();
        msg.setSentDate(timeStamp);

        // Use the 'send' static method of the Transport class to send the message
        Transport.send(msg);
    }
}
