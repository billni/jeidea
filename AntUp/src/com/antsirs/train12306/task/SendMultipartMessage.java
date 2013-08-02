package com.antsirs.train12306.task;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Message.RecipientType;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.antsirs.core.util.exception.ExceptionConvert;
import com.google.appengine.api.search.DateUtil;

public class SendMultipartMessage {
	private static final Logger logger = Logger.getLogger(SendMultipartMessage.class.getName());
	
	public static void sentMail(String msgContent) {
		Properties props = new Properties();
		Session session = Session.getInstance(props);
		session.setDebug(true);
		Message msg = new MimeMessage(session);
		try {
			msg.setSubject("Gae DataStore Daily "
					+ DateUtil.formatDateTime(new Date()));

			// 设置发送人
			msg.setFrom(new InternetAddress("niyong2008@gmail.com"));
			// 设置收件人
			msg.setRecipient(RecipientType.TO, new InternetAddress(
					"niyong2008@gmail.com", "Mr. niyong"));
			// 设置抄送人
			msg.setRecipient(RecipientType.CC, new InternetAddress(
					"ni_yong@hotmail.com", "Mr. niyong"));

			MimeMultipart msgMultipart = new MimeMultipart("mixed");
			msg.setContent(msgMultipart);
			// 为message对象创建content组件和attch组件
			MimeBodyPart content = new MimeBodyPart();
			MimeBodyPart attch = new MimeBodyPart();
			// 将两个附件对象和正文对象添加到msgMultipart对象
			msgMultipart.addBodyPart(content);
			msgMultipart.addBodyPart(attch);

			// 为附件1关联数据源
			DataSource ds = new FileDataSource(
					"C:\\Documents and Settings\\Administrator\\桌面\\river.jpg");			
			attch.setDataHandler(new DataHandler(ds));
			attch.setFileName(new Date() + " crawled data.");

			// 因为正文content也是一个Multipart对象,其组件是related关系，所以content.setContent(contentMultipart)
			MimeMultipart contentMultipart = new MimeMultipart("related");
			content.setContent(contentMultipart);
//			// 为contentMultipart创建htmlPart组件和gifPart组件
//			MimeBodyPart htmlPart = new MimeBodyPart();
//			MimeBodyPart gifPart = new MimeBodyPart();
//			// 将htmlPart和gifPart对象添加到contentMultipart对象
//			contentMultipart.addBodyPart(htmlPart);
//			contentMultipart.addBodyPart(gifPart);
//
//			DataSource ds3 = new FileDataSource(
//					"C:\\Documents and Settings\\Administrator\\桌面\\2012_03130103.JPG");
//			DataHandler dh3 = new DataHandler(ds3);
//			gifPart.setDataHandler(dh3);
//			gifPart.setFileName("itstar.gif");
//			gifPart.setHeader("Content-Location",
//					"http://www.csdn.net/itstar.gif");
//			// 这里src="http://www.csdn.net/itstar.gif"实际上指向的是其关联的gifPart对象
//			htmlPart.setContent(
//					"这是我的第一个复合结构的JavaMail，我的照片<img height=100px,width=80px src='http://www.csdn.net/itstar.gif'/>",
//					"text/html;charset=utf-8");

			// 保存message所做的改变
			msg.saveChanges();
			Transport.send(msg);
			// OutputStream ops = new
			// FileOutputStream("C:\\Documents and Settings\\Administrator\\桌面\\SendMultipartMessageDemo3.eml");
			// 将message对象写入文件流中
			// msg.writeTo(ops);
			// ops.close();
		} catch (Exception e) {
			logger.severe("sentMail exception: " + ExceptionConvert.getErrorInfoFromException(e));			
		}
	}
	
	public static void sentSimpleMail(String msgContent) {
		Properties props = new Properties();
		Session session = Session.getInstance(props);
		session.setDebug(true);
		Message msg = new MimeMessage(session);
		try {
			msg.setSubject("Gae DataStore Daily "
					+ DateUtil.formatDateTime(new Date()));

			// 设置发送人
			msg.setFrom(new InternetAddress("niyong@jeidea.com"));
			// 设置收件人
			msg.setRecipient(RecipientType.TO, new InternetAddress(
					"niyong2008@gmail.com", "Mr. niyong"));
			// 设置抄送人
			msg.setRecipient(RecipientType.CC, new InternetAddress(
					"ni_yong@hotmail.com", "Mr. niyong"));
			msg.setText("<data>" +msgContent + "</data>");
			Transport.send(msg);			
		} catch (Exception e) {			
			logger.severe("sentSimpleMail exception: " + ExceptionConvert.getErrorInfoFromException(e));
		}
	}
}
