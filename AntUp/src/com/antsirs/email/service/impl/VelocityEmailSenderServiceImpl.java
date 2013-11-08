package com.antsirs.email.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.velocity.VelocityEngineUtils;
import com.antsirs.core.util.exception.ExceptionConvert;
import com.antsirs.email.service.SenderService;
import com.google.appengine.api.search.DateUtil;
import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

public class VelocityEmailSenderServiceImpl implements SenderService{

	private static final Logger logger = Logger
			.getLogger(VelocityEmailSenderServiceImpl.class.getName());
	@Autowired
	private VelocityEngine velocityEngine;

	/**
	 * Sends e-mail using Velocity template for the body
	 */
	public void sendMail(Map<String, String> hTemplateVariables) {
		Properties props = new Properties();
		Session session = Session.getInstance(props);
		session.setDebug(true);
		MimeMessage msg = new MimeMessage(session);
		try {
			//subject
			msg.setSubject("Gae DataStore Daily " + DateUtil.formatDateTime(new Date()));

			// 设置发送人
			msg.setFrom(new InternetAddress("niyong@jeidea.com"));
			// 设置收件人
			msg.setRecipient(RecipientType.TO, new InternetAddress(
					"niyong2008@gmail.com", "Mr. niyong"));
			// 设置抄送人
			msg.setRecipient(RecipientType.CC, new InternetAddress(
					"ni_yong@hotmail.com", "Mr. niyong"));
			String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/emailbody.vm", hTemplateVariables);
			msg.setText(body);
			Transport.send(msg);			
		} catch (Exception e) {			
			logger.severe("sentSimpleMail exception: " + ExceptionConvert.getErrorInfoFromException(e));
		}
	}

	/**
	 * Sends xmpp using Velocity template for the body
	 */
	public void sendXmpp(final SimpleMailMessage msg,
			Map<String, Object> hTemplateVariables) {
		JID jid = new JID("niyong2008@gmail.com");
		String body = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, "/xmppBody.vm", hTemplateVariables);
		Message message = new MessageBuilder().withRecipientJids(jid)
				.withBody(body).build();
		boolean messageSent = false;
		XMPPService xmpp = XMPPServiceFactory.getXMPPService();
		SendResponse status = xmpp.sendMessage(message);
		messageSent = (status.getStatusMap().get(jid) == SendResponse.Status.SUCCESS);
		logger.info("xmpp sendResponse: " + status.getStatusMap().get(jid)
				+ ", messageSent: " + messageSent);
	}

}