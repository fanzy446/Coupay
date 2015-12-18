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
		String subject="������֤��";
		
		//��������Ϊ���͵���֤��
		String messageText="�������µ�ַȷ����֤��Ϣ http://110.64.89.205:8080/futurePayment/ReceiveVarificationByEmailCt?"+(String)request.getAttribute("data");

		System.out.println("email="+to);
		String password="lgb13539783913";
		String S ="lgb452721007@sina.cn";
	    int n =S.indexOf('@');
	    int m=S.length() ;
	    String server =S.substring(n+1,m);
	    //�����ʼ��Ự
	    /* Session�ඨ���˻������ʼ��Ự������Http�Ự���������ǽ����շ��ʼ��Ĺ������ǻ������
	       �Ự�ġ�Session����������java.util.Properties���������ʼ����������û�����������Ϣ����
	       ��Ӧ�ó���Ҫʹ�õ��Ĺ�����Ϣ��
	      Session��Ĺ��췽����˽�еģ��������ǿ���ʹ��Session���ṩ��getDefaultInstance()�����
	        ̬�����������һ��Ĭ�ϵ�Session����*/
	    Properties pro=new Properties();//����һ����Ĭ��ֵ�Ŀ������б�
	    pro.put("mail.smtp.host","smtp."+server);//Properties ����Ӧ�� put �� putAll ������
	                    // ��������ʹ��������������Ϊ������������߲��������ֵ���� String ����
	    pro.put("mail.smtp.auth","true");
	    Session sess=Session.getDefaultInstance(pro);
	    sess.setDebug(true);//�ڷ����ʼ������м��mail����
	  //�½�һ����Ϣ����
        /*�� ���ǽ�����Session����󣬱���Ա����͵Ĺ�����Ϣ���ˡ�������SUN�ṩ��Message����
                     ��������������������������Message��һ �������࣬���������£�����ʹ��
        javax.mail.internet.MimeMessage������࣬������ʹ��MIME���͡�MIME��Ϣͷ�� ������Ϣ��
        ��Ϣͷֻ��ʹ��US-ASCII�ַ�������ASCII�ַ���ͨ������ת��ΪASCII�ķ�ʽʹ�á�
                Ϊ�˽���һ��MimeMessage�������Ǳ��뽫Session������ΪMimeMessage���췽���Ĳ������룺*/
        
        MimeMessage message=new MimeMessage(sess);
        //���÷�����
        /*����������Ѿ�������Session��Message�����潫�������ʹ���ʼ���ַ�ࣺAddress��
                     ��Messageһ����Address��Ҳ��һ�������࣬�������ǽ�ʹ��
        javax.mail.internet.InternetAddress������ࡣ*/
        try {
			InternetAddress from_mail=new InternetAddress(from);
			//����ͨ��message��setFrom()��setReplyTo()���ַ��������ʼ��ķ����ˣ�
			//addForm()�������ӷ�����
			message.setFrom(from_mail);
			//�����ռ���
			//Ϊ�����������ˣ�����ʹ��addRecipient()�������������ˣ��˷�����Ҫʹ��
			//Message.RecipientType�ĳ��������������˵����ͣ�
            InternetAddress to_mail1=new InternetAddress(to);
            InternetAddress to_mail2=new InternetAddress(from);
            for(int i=1;i<=2;i++)
            {
            	if(i==1){ message.setRecipient(Message.RecipientType.TO ,to_mail1);
            	}
            	if(i==2){ message.setRecipient(Message.RecipientType.TO ,to_mail2);
            	}
			//��������
			message.setSubject(subject);
			//��������
			message.setText(messageText);
			//���÷���ʱ��
			message.setSentDate(new Date());
			//�����ʼ�
			message.saveChanges();  //�����ʼ���Ϣ
			/*�ڷ�����Ϣʱ��Transport�ཫ���õ��������ʵ���˷�����Ϣ��Э�飨ͨ��ΪSMTP����
			  ������һ�������࣬���ǿ���ʹ�������ľ�̬����send()��������Ϣ��
			����Ҳ����Session�����ӦЭ���Ӧ��Transportʵ������ͨ�������û��������롢�ʼ�������
			�������Ȳ����������ʼ������������ӣ���ʹ��sendMessage()��������Ϣ���ͣ����ر����ӣ�*/
			   
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
        out.println("�ȴ���֤��");
	}

}
