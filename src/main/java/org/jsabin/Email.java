package org.jsabin;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
    private final Properties props = System.getProperties();

    public Email(String smtpServer, int smtpPort) {
        props.put("mail.smtp.host", smtpServer);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.starttls.enable", true);
    }

    public void send(final String username, final String password, String from, String emailAddresses, String subject, String body) throws MessagingException {

        Session session;
        if (password != null && password.length() > 0) {
            Authenticator authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };
            props.put("mail.smtp.auth", true);
            session = Session.getInstance(props, authenticator);
        }
        else
        {
            props.put("mail.smtp.auth", false);
            session = Session.getInstance(props);
        }

        MimeMessage message = new MimeMessage(session);

        message.setSubject(subject);
        message.setFrom(new InternetAddress(from));
        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddresses));
        message.setText(body);

        Transport.send(message);
    }
}
