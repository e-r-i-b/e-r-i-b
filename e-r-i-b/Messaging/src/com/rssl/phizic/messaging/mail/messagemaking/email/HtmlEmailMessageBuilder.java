package com.rssl.phizic.messaging.mail.messagemaking.email;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.notification.BusinessNotificationConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.MailFormat;
import com.rssl.phizic.messaging.mail.messagemaking.AddressBuilder;
import com.rssl.phizic.messaging.mail.messagemaking.ContactInfo;
import com.rssl.phizic.messaging.mail.messagemaking.MessageBuilderBase;
import com.rssl.phizic.utils.StringHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import static com.rssl.phizic.messaging.mail.messagemaking.TemplateLoaderBase.SEPARATOR;
/**
 * @author Roshka
 * @ created 08.06.2006
 * @ $Author: basharin $
 * @ $Revision: 83994 $
 */

public class HtmlEmailMessageBuilder extends MessageBuilderBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	public static final String TITLE_TEMPLATE_KEY = "theme";

	private Session        session;
	private AddressBuilder addressBuilder;
	private Configuration templateConfiguration;
	private String key;

	public HtmlEmailMessageBuilder(Configuration templateConfiguration, String key, Session session, AddressBuilder addressBuilder)
	{
		this.key       = key;
		this.templateConfiguration       = templateConfiguration;
		this.addressBuilder = addressBuilder;
		this.session        = session;
	}

	public MimeMessage create(ContactInfo contactInfo, Object bean) throws MessagingException
	{
		return create(contactInfo, bean, false);
	}

	public MimeMessage create(ContactInfo contactInfo, Object bean, boolean needLogForLogin) throws MessagingException
	{
		writeToLog(needLogForLogin, "Получаем и записываем адрес для email сообщения о входе");
		Address address = addressBuilder.build(contactInfo);

		MimeMessage message = new MimeMessage(session);
		message.setRecipient(Message.RecipientType.TO, address);
		message.setSubject(composeSubject(bean), "utf-8");

		MailFormat mailFormat = contactInfo.getMailFormat();
		BodyPart bodyPart = format(bean, mailFormat != null ? mailFormat : MailFormat.PLAIN_TEXT, needLogForLogin);
		writeToLog(needLogForLogin, "Записываем тело сообщения email о входе");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(bodyPart);

		message.setContent(multipart);
		return message;
	}

	/**
	 * Создает сообщение со вложением.
	 *
	 * @param contactInfo contactInfo
	 * @param bean bean
	 * @param attachments вложение (имя файла|content type - файл).
	 * @return сообщение.
	 * @throws MessagingException
	 */
	public MimeMessage create(ContactInfo contactInfo, Object bean, Map<String, byte[]> attachments) throws MessagingException
	{
		Address address = addressBuilder.build(contactInfo);

		MimeMessage message = new MimeMessage(session);
		message.setRecipient(Message.RecipientType.TO, address);
		message.setSubject(composeSubject(bean), "utf-8");

		MailFormat mailFormat = contactInfo.getMailFormat();
		BodyPart bodyPart = format(bean, mailFormat != null ? mailFormat : MailFormat.PLAIN_TEXT, false);
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(bodyPart);

		for (Map.Entry<String, byte[]> file : attachments.entrySet())
		{
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			ByteArrayDataSource source = new ByteArrayDataSource(file.getValue(), file.getKey().split("\\|")[1]);
			source.setName(file.getKey().split("\\|")[0]);
	        attachmentBodyPart.setDataHandler(new DataHandler(source));
			try
			{
	            attachmentBodyPart.setFileName(MimeUtility.encodeText(file.getKey().split("\\|")[0]));
			}
			catch (UnsupportedEncodingException e)
			{
				throw new MessagingException("Ошибка определения кодировки", e);
			}
	        multipart.addBodyPart(attachmentBodyPart);
		}

		message.setContent(multipart);
		return message;
	}

	protected String composeSubject(Object bean) throws MessagingException
	{
		return composeInnerTemplate(bean, getTemplate(TITLE_TEMPLATE_KEY));
	}

	protected Template getTextTemplate(String formatType) throws MessagingException
	{
		return getTemplate(formatType);
	}

	private BodyPart format(Object msg, MailFormat mailFormat, boolean needLogForLogin) throws MessagingException
	{
		if (msg == null)
			throw new NullPointerException("ERROR: The parameter [msg]  is null");

		BodyPart bodyPart = new MimeBodyPart();
		writeToLog(needLogForLogin, "Получаем шаблон для email сообщения о входе");
		String finalText = composeInnerTemplate(msg, getTextTemplate(mailFormat.getKey()));
		if (mailFormat == MailFormat.HTML)
		{
			writeToLog(needLogForLogin, "Получаем конфиг с внешним шаблоном для email сообщения о входе");
			BusinessNotificationConfig config = ConfigFactory.getConfig(BusinessNotificationConfig.class);
			String outerText = config.getOuterTemplate();
			writeToLog(needLogForLogin, "Вставляем текст во внешний шаблон для email сообщения о входе");
			if (StringHelper.isEmpty(outerText))
				log.error("Не указан внешний html-шаблон");
			else
				finalText = outerText.replace("${innerTemplate}", finalText);
		}
		writeToLog(needLogForLogin, "Записываем получившийся текст в email сообщения о входе");
		bodyPart.setContent(finalText, "text/html;\r\n\tcharset=\"utf-8\"");

		return bodyPart;
	}

	private void writeToLog(boolean needLogForLogin, String message)
	{
		if (needLogForLogin)
			log.info(message);
	}

	public String composeMessageText(Object msg) throws MessagingException
	{
		throw new UnsupportedOperationException();
	}

	private String composeInnerTemplate(Object msg, Template template) throws MessagingException
	{
		StringWriter writer = new StringWriter();

		try
		{
			Map messageBeanMap = prepareMessageBean(msg);
			template.process(messageBeanMap, writer);
		}
		catch (TemplateException ex)
		{
			throw new MessagingException(ex.getMessage(), ex);
		}
		catch (IOException ex)
		{
			throw new MessagingException(ex.getMessage(), ex);
		}

		return writer.toString();
	}

	private Template getTemplate(String templateType) throws MessagingException
	{
		try
		{
			return templateConfiguration.getTemplate(key + SEPARATOR + templateType);
		}
		catch (IOException ex)
		{
			throw new MessagingException(ex.getMessage(), ex);
		}
	}
}