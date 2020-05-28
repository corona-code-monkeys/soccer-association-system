/**
 * The class represents notification handler - which responsible for sending emails
 */

package com.SAS.User;

import com.SAS.crudoperations.UsersCRUD;
import com.SAS.systemLoggers.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.List;
import java.util.Properties;

public class NotificationsHandler {

    private String from = "deymcaapp@gmail.com";
    private String pass = "Sasapp123";
    private String subject = "You have new message from DeYMCA app";
    private LoggerFactory logger = LoggerFactory.getInstance();

    /**
     * The function receives list of userNames, subject and body and sends the message to the users
     * by email
     * @param userNames
     * @param body
     * @return true if succeeded
     */
    public boolean sendEmailToUser(List<String> userNames, String body) {

        //email of recipient
        String[] to = getUsersEmails(userNames);

        if (to == null) {
            return false;
        }

        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            logger.logEvent("Email has been sent with the message: " + body);
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
        return true;
    }

    /**
     * The function receives a list of users and returns a list of their emails
     * @param userNames
     * @return list of emails
     */
    private String[] getUsersEmails(List<String> userNames) {
        String[] emails = null;
        if (userNames != null && userNames.size() > 0) {
            emails = new String[userNames.size()];

            //get all the emails of the users
            int i = 0;
            for (String user: userNames) {
                emails[i] = UsersCRUD.getEmailByUserName(user);
                i++;
            }

        }

        return emails;
    }
}