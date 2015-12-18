package com.subsystem.serverpush;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {
	private String server;
	private String from;
	private String to;
	private String password;
	private String subject;
	private String content;
	private Properties properties;
	private Session session;
	private MimeMessage message;
	private LinkedList<InternetAddress> receivers;
	
	public Mailer(){
		properties = new Properties();
	}

	/*public boolean sendMail(){
		try{
			properties.put("mail.smtp.host","smtp."+server);
			properties.put("mail.smtp.auth","true");
		    session =Session.getDefaultInstance(properties);
		    session.setDebug(true);
		    message = new MimeMessage(session);
		    if(receivers.size() > 0){
		    	InternetAddress[] address = new InternetAddress[receivers.size()];
		    	for(int i = 0; i < receivers.size(); i++)
		    		address[i] = receivers.get(i);
		    	
		    	message.setRecipients(Message.RecipientType.TO, address);
		    	message.setSubject(subject);
				message.setText(content);
				message.setSentDate(new Date());
				message.saveChanges();
		    }
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}*/
	
	public void addReceiver(String to){
		try {
			InternetAddress reciver = new InternetAddress(to);
			receivers.add(reciver);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
