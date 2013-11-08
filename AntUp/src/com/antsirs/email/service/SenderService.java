package com.antsirs.email.service;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

public interface SenderService {

	/**
	 * Sends e-mail using Velocity template for the body
	 */
	public void sendMail(Map<String, String> hTemplateVariables);
	public void sendXmpp(final SimpleMailMessage msg, Map<String, Object> hTemplateVariables);
}
