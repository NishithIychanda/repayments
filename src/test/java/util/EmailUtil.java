package util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {

    public static void sendEmailWithAttachments(String host, String port,
                                                String mailFrom, String password, String[] mailTo,
                                                String subject, String message, String[] attachFiles)
            throws AddressException, MessagingException {

        // Sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailFrom, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        // Creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(mailFrom));
        
        InternetAddress[] addressTo = new InternetAddress[mailTo.length];
        for (int i = 0; i < mailTo.length; i++) {
            addressTo[i] = new InternetAddress(mailTo[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);
        
        msg.setSubject(subject);
        msg.setSentDate(new java.util.Date());

        // Creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");

        // Creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Adds attachments
        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();

                try {
                    attachPart.attachFile(filePath);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                multipart.addBodyPart(attachPart);
            }
        }

        // Sets the multi-part as e-mail's content
        msg.setContent(multipart);

        // Sends the e-mail
        Transport.send(msg);
    }
}
