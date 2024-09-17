package com.example.contactmanager.helper;


import jakarta.mail.*;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
public class Mail1 {
  int i;
  Message m;
    public Mail1(String g,String l) {
     try{   
        String h="smtp.gmail.com"; 
        Properties pt=System.getProperties();
        pt.put("mail.smtp.host",h);
        pt.put("mail.smtp.port","587");
        pt.put("mail.smtp.starttls.enable","true");
        pt.put("mail.smtp.auth","true");
        Session s=Session.getInstance(pt, new Authenticator(){
         @Override
         protected PasswordAuthentication getPasswordAuthentication(){
           return new PasswordAuthentication("bibek.bhattacherjee3","rwlf guur nzjf oeol");
         }
        });i=6;
        s.setDebug(true);
         m=new MimeMessage(s);
        try{
            String f="bibek.bhattacherjee3@gmail.com";
            m.setFrom(new InternetAddress(f));
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(g));
            m.setSubject("security checkup");
            m.setText("auto generated text from our side is "+l);
             Transport.send(m);i=11;//error
        }
            catch (MessagingException ex) {
            //Logger.getLogger(mail1.class.getName()).log(Level.SEVERE, null, ex);
            System.out.print(ex);
            //System.out.print("2"+i);
        }
     }
     catch(Exception e1){System.out.print(e1);
     System.out.print("3"+i);}
        
    }

}

