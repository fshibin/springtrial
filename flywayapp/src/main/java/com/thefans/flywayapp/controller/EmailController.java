package com.thefans.flywayapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.Multipart;
import javax.mail.Transport;

import javax.mail.internet.AddressException;
import javax.mail.MessagingException;
import java.io.IOException;

@RestController
public class EmailController {
   @RequestMapping(value = "/sendemail")
   public String sendEmail() {
      try {
          sendmail();
      } catch (Exception e) {
          return e.toString();
      }
      return "Email sent successfully";
   }   

   private void sendmail() throws AddressException, MessagingException, IOException {
       Properties props = new Properties();
       props.put("mail.smtp.auth", "true");
       props.put("mail.smtp.starttls.enable", "true");
       props.put("mail.smtp.host", "smtp.gmail.com");
       props.put("mail.smtp.port", "587");
       
       Session session = Session.getInstance(props, new Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
             return new PasswordAuthentication("shibin.fan@gmail.com", "mypassword");
          }
       });
       Message msg = new MimeMessage(session);
       msg.setFrom(new InternetAddress("shibin.fan@gmail.com", false));
    
       msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("shibin.fan@gmail.com"));
       msg.setSubject("Photo taken today");
       msg.setContent("Kia ora,\nPlease check the photo!\nCheers,\nSpring Boot\n", "text/html");
       msg.setSentDate(new Date());
    
       MimeBodyPart messageBodyPart = new MimeBodyPart();
       messageBodyPart.setContent("Spring Boot Email", "text/html");
    
       Multipart multipart = new MimeMultipart();
       multipart.addBodyPart(messageBodyPart);
       MimeBodyPart attachPart = new MimeBodyPart();
    
       attachPart.attachFile("C:\\Users\\Public\\Pictures\\Sample Pictures\\Desert.jpg");
       multipart.addBodyPart(attachPart);
       msg.setContent(multipart);
       Transport.send(msg);   
    }
}
