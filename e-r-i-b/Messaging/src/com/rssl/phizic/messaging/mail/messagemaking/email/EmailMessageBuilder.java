package com.rssl.phizic.messaging.mail.messagemaking.email;

import freemarker.template.Template;

import javax.mail.MessagingException;
import javax.mail.Session;

/**
 * @author gladishev
 * @ created 19.11.13
 * @ $Author$
 * @ $Revision$
 */
public class EmailMessageBuilder extends HtmlEmailMessageBuilder
{
	private String subject;
	private Template template;

	/**
	 * ctor
	 * @param subject - тема
	 * @param template - шаблон сообщения
	 * @param mailSession сессия
	 * @param internetAddressBuilder - адрес билдер
	 */
	public EmailMessageBuilder(String subject, Template template, Session mailSession, InternetAddressBuilder internetAddressBuilder)
	{
		super(null, null, mailSession, internetAddressBuilder);
		this.subject = subject;
		this.template = template;
	}

	@Override
	protected String composeSubject(Object bean) throws MessagingException
	{
		return subject;
	}

	@Override
	protected Template getTextTemplate(String formatType) throws MessagingException
	{
		return template;
	}
}
