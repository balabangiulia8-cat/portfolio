package com.giuliabalaban.portfolio.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

private final JavaMailSender mailSender;

@Value("${portfolio.contact.email}")
private String destinationEmail;

public EmailService(JavaMailSender mailSender){
this.mailSender=mailSender;
}

public void sendContactEmail(
String name,
String email,
String message){

String safeName=sanitizeHeader(name);
String safeEmail=sanitizeHeader(email);

SimpleMailMessage mail=new SimpleMailMessage();

mail.setTo(destinationEmail);
mail.setSubject("Nuovo messaggio dal portfolio - "+safeName);
mail.setReplyTo(safeEmail);

mail.setText(
"Nome: "+safeName+
"\nEmail: "+safeEmail+
"\n\nMessaggio:\n"+
message
);

mailSender.send(mail);
}

private String sanitizeHeader(String value){

if(value==null){
return "";
}

return value
.replace("\r"," ")
.replace("\n"," ")
.trim();
}

}