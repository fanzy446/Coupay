package com.subsystem.serverpush;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MailHelper extends HttpServlet {
	private static final long serialVersionUID = -7303906369133218844L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String from="lgb452721007@sina.cn";
		String to=(String) request.getAttribute("email");
		String subject="邮箱验证！";
		
		//正文内容为发送的验证码
		String messageText="请点击以下地址确认验证信息 http://110.64.89.205:8080/futurePayment/ReceiveVarificationByEmailCt?"+(String)request.getAttribute("data");

		System.out.println("email="+to);
		String password="lgb13539783913";
		String S ="lgb452721007@sina.cn";
	    int n =S.indexOf('@');
	    int m=S.length() ;
	    String server =S.substring(n+1,m);
	    //建立邮件会话
	    /* Session类定义了基本的邮件会话。就像Http会话那样，我们进行收发邮件的工作都是基于这个
	       会话的。Session对象利用了java.util.Properties对象获得了邮件服务器、用户名、密码信息和整
	       个应用程序都要使用到的共享信息。
	      Session类的构造方法是私有的，所以我们可以使用Session类提供的getDefaultInstance()这个静
	        态工厂方法获得一个默认的Session对象*/
	    Properties pro=new Properties();//创建一个无默认值的空属性列表。
	    pro.put("mail.smtp.host","smtp."+server);//Properties 对象应用 put 和 putAll 方法。
	                    // 但不建议使用这两个方法因为它们允许调用者插入其键或值不是 String 的项
	    pro.put("mail.smtp.auth","true");
	    Session sess=Session.getDefaultInstance(pro);
	    sess.setDebug(true);//在发送邮件过程中监控mail命令
	  //新建一个消息对象
        /*当 我们建立了Session对象后，便可以被发送的构造信息体了。在这里SUN提供了Message类型
                     来帮助开发者完成这项工作。由于Message是一 个抽象类，大多数情况下，我们使用
        javax.mail.internet.MimeMessage这个子类，该类是使用MIME类型、MIME信息头的 邮箱信息。
        信息头只能使用US-ASCII字符，而非ASCII字符将通过编码转换为ASCII的方式使用。
                为了建立一个MimeMessage对象，我们必须将Session对象作为MimeMessage构造方法的参数传入：*/
        
        MimeMessage message=new MimeMessage(sess);
        //设置发件人
        /*到这里，我们已经建立了Session和Message，下面将介绍如何使用邮件地址类：Address。
                     像Message一样，Address类也是一个抽象类，所以我们将使用
        javax.mail.internet.InternetAddress这个子类。*/
        try {
			InternetAddress from_mail=new InternetAddress(from);
			//我们通过message的setFrom()和setReplyTo()两种方法设置邮件的发信人：
			//addForm()方法增加发信人
			message.setFrom(from_mail);
			//设置收件人
			//为了设置收信人，我们使用addRecipient()方法增加收信人，此方法需要使用
			//Message.RecipientType的常量来区分收信人的类型：
            InternetAddress to_mail1=new InternetAddress(to);
            InternetAddress to_mail2=new InternetAddress(from);
            for(int i=1;i<=2;i++)
            {
            	if(i==1){ message.setRecipient(Message.RecipientType.TO ,to_mail1);
            	}
            	if(i==2){ message.setRecipient(Message.RecipientType.TO ,to_mail2);
            	}
			//设置主题
			message.setSubject(subject);
			//设置内容
			message.setText(messageText);
			//设置发送时间
			message.setSentDate(new Date());
			//发送邮件
			message.saveChanges();  //保存邮件信息
			/*在发送信息时，Transport类将被用到。这个类实现了发送信息的协议（通称为SMTP），
			  此类是一个抽象类，我们可以使用这个类的静态方法send()来发送消息：
			我们也可由Session获得相应协议对应的Transport实例。并通过传递用户名、密码、邮件服务器
			主机名等参数建立与邮件服务器的连接，并使用sendMessage()方法将信息发送，最后关闭连接：*/
			   
			Transport transport = sess.getTransport("smtp");
			transport.connect("smtp."+server,from,password);
			transport.sendMessage(message,message.getAllRecipients());
			transport.close();
			}
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        out.println("等待验证！");
	}

}
