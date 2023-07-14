package com.rssl.phizic.messaging.mail.messagemaking.sms;

import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.messaging.mail.messagemaking.AddressBuilder;
import com.rssl.phizic.messaging.mail.messagemaking.ContactInfo;
import com.rssl.phizic.messaging.mail.messagemaking.MessageBuilderBase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.Session;

/**
 * @author Evgrafov
 * @ created 05.07.2006
 * @ $Author: koptyaev $
 * @ $Revision: 81667 $
 */

public class PlainTextMessageBuilder extends MessageBuilderBase
{
	private static final String DELIMETER = "^";
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private Template template;

	public PlainTextMessageBuilder(Template template, Session session, AddressBuilder addressBuilder)
	{
		this.template       = template;
		template.setNumberFormat("###0.00");
		this.priority = TextMessage.DEFAULT_PRIORITY;
	}

	public PlainTextMessageBuilder(Configuration templateConfiguration, String key, Session session, AddressBuilder addressBuilder) throws IKFLMessagingException, IOException
	{
		this(templateConfiguration.getTemplate(getTemplateName(key)), session, addressBuilder);
	}

	private static String getTemplateName(String key)
	{
		return MultiLocaleContext.isDefaultLocale() ? key : MultiLocaleContext.getLocaleId() + DELIMETER + key;
	}

	public IKFLMessage create(ContactInfo contactInfo, Object bean) throws MessagingException
	{
		return new IKFLMessage(contactInfo.getLoginId(), composeMessageText(bean), getPriority(), false);
	}

	public String composeMessageText(Object bean) throws MessagingException
	{
		StringWriter writer = new StringWriter();

		try
		{
			Map messageBeanMap = prepareMessageBean(bean);
			template.process(messageBeanMap, writer);
		}
		catch (TemplateException e)
		{
			log.error(e.getMessage(), e);
			throw new MessagingException("Ошибка", e);
		}
		catch (IOException e)
		{
			log.error(e.getMessage(), e);
			throw new MessagingException("Ошибка", e);
		}

		return writer.toString();
	}

}