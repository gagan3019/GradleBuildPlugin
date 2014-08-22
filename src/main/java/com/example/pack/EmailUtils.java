package com.example.pack;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by gagandeep on 19/8/14.
 */
public class EmailUtils {

    private static EmailUtils mInstance;
    private Session mSession;
    private EmailUtils() {

    }
    public static EmailUtils getInstance() {
        if(mInstance == null) {
            mInstance = new EmailUtils();
        }
        return mInstance;
    }

    public void initSession(String host,String port,final String username, final String password) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Get the Session object.
        mSession = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    public boolean sendEmailWithoutAttachment(String fromAddresses,String toAddresses,String ccAddresses,String bccAddress,String subject,String bodyText) {
        System.out.println("Please wait while system is sending email");
        boolean sendSuccess = true;
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(mSession);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(fromAddresses));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toAddresses));
            if(ccAddresses != null) {
                message.setRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(ccAddresses));
            }
            if(bccAddress != null) {
                message.setRecipients(Message.RecipientType.BCC,
                        InternetAddress.parse(bccAddress));
            }


            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(bodyText);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            sendSuccess = false;
            e.printStackTrace();
        }
        return sendSuccess;
    }

    public boolean sendEmailWithAttachment(String fromAddresses,String toAddresses,String ccAddresses,String bccAddress,String subject,String bodyText,String filename) {
        boolean sendSuccess = true;
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(mSession);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(fromAddresses));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toAddresses));

            if(ccAddresses != null) {
                message.setRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(ccAddresses));
            }
            if(bccAddress != null) {
                message.setRecipients(Message.RecipientType.BCC,
                        InternetAddress.parse(bccAddress));
            }
            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(bodyText);

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename.substring(filename.lastIndexOf("/")));
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            sendSuccess = false;
            e.printStackTrace();
        }
        return sendSuccess;
    }
}
