package server;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


class JavaMail {
    static final String ENCODING = "UTF-8";

    public static void registration(String email, String reg_token){
        String subject = "Confirm registration";
        String content = "Registration token: " + reg_token;
        String smtpHost="mail.0hcow.com";
        String from= "sadiya.nooriya@0hcow.com";
        String login="sadiya.nooriya";
        String password="";
        String smtpPort="25";
        try {
            sendSimpleMessage(login, password, from, email, content, subject, smtpPort, smtpHost);
        } catch (Exception e) {
            System.out.println("Error whilst sending email");
        }
    }

    public static void main(String args[]) {
        String subject = "Confirm registration";
        String content = "Registration token: ";
        String smtpHost = "mail.0hcow.com";
        String from = "sadiya.nooriya@0hcow.com";
        String login = "sadiya.nooriya";
        String password = "";
        String smtpPort = "25";
        try {
            sendSimpleMessage(login, password, from, "kyani.kayslie@0hcow.com", content, subject, smtpPort, smtpHost);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error whilst sending email");
        }
    }

    public static void sendSimpleMessage(String login, String password, String from, String to, String content, String subject, String smtpPort, String smtpHost)
            throws MessagingException {
        Authenticator auth = new Auth(login, password);

        Properties props = System.getProperties();
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.mime.charset", ENCODING);
        Session session = Session.getDefaultInstance(props, auth);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);
        msg.setText(content);
        Transport.send(msg);
    }
}