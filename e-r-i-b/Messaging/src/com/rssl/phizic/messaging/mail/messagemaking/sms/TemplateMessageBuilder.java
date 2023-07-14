package com.rssl.phizic.messaging.mail.messagemaking.sms;

import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.Session;

/**
 * �������� �������� �� �����
 * @author Puzikov
 * @ created 14.03.14
 * @ $Author$
 * @ $Revision$
 */

public class TemplateMessageBuilder
{
	private static final PropertyReader messagingProperties = ConfigFactory.getReaderByFileName("messaging.properties");

	/**
	 * @param loader ��������� ��������
	 * @param key ���� �������
	 * @param messageProperties  - ���� ���������� ��� ������ ���������
	 * @param loggingProperties - ���� ������������� ���������� ��� ������ ����������� (�� ���, � ������ ��, ������� ������ �������������)
	 * @return ��� ���������
	 * @throws IKFLMessagingException
	 */
	public static TextMessage build(TemplateLoader loader, String key, Map<String, ?> messageProperties, Map<String, ?> loggingProperties) throws IKFLMessagingException
	{
		try
		{
			Configuration templateConfiguration = new Configuration();
			templateConfiguration.setTemplateLoader(loader);
			Session session = Session.getInstance(messagingProperties.getAllProperties());
			PlainTextMessageBuilder messageBuilder = new PlainTextMessageBuilder(templateConfiguration, key, session, new PhoneAddressBuilder());

			Map<String, Object> messageObject = new HashMap<String, Object>(messageProperties);

			Map<String, Object> loggingObject = new HashMap<String, Object>(messageProperties);
			loggingObject.putAll(loggingProperties);

			String messageText = messageBuilder.composeMessageText(messageObject);
			String loggingText = messageBuilder.composeMessageText(loggingObject);

			//������� ��� ������� ����� �������� � �������
			messageText = messageText.replaceAll("\\s+,",",").replaceAll("\\s+\\.",".");
			loggingText = loggingText.replaceAll("\\s+,",",").replaceAll("\\s+\\.",".");

			return new TextMessage(messageText, loggingText);
		}
		catch (IOException e)
		{
			throw new IKFLMessagingException(e);
		}
		catch (MessagingException e)
		{
			throw new IKFLMessagingException(e);
		}
	}
}
