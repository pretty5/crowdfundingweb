package com.zhiyou100.service;

import com.sun.mail.util.MailSSLSocketFactory;
import com.zhiyou100.exception.CrowdFundingException;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/*
*@ClassName:MailService
 @Description:TODO
 @Author:
 @Date:2018/9/17 13:56 
 @Version:v1.0
*/
@Service
public class MailService {
    public static final String HOST="smtp.qq.com";
    //public static final String NAME="";
    public static final String PASSWORD="wuyfuvcnwuvgbbia";
    public static final String SENDER="691734536@qq.com";
    private static final String TITLE = "众筹激活邮件";

    public static void main(String[] args) throws Exception {
        new MailService().sendMail("691734536@qq.com","helloworld");
    }


    public void sendMail(String addr,String content) throws CrowdFundingException {
      try {
        Properties prop = new Properties();
        prop.setProperty("mail.host", HOST);
        prop.setProperty("mail.debug", "true");
        prop.setProperty("mail.smtp.auth", "true");
        //prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = null;
        sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(HOST, SENDER, PASSWORD);
        //4、创建邮件
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress(SENDER));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(addr));
        //邮件的标题
        message.setSubject(TITLE);
        //邮件的文本内容
        message.setContent(content, "text/html;charset=UTF-8");
        
        //5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
      }catch (Exception e){
          throw new CrowdFundingException(2,"发送失败");
      }
    }
   

}
