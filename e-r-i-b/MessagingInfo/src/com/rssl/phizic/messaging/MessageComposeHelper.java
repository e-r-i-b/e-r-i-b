package com.rssl.phizic.messaging;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.messaging.info.PersonalSubscriptionData;
import com.rssl.phizic.business.sms.SMSResource;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.ext.sbrf.push.PushMessageBuilder;
import com.rssl.phizic.messaging.loaders.push.*;
import com.rssl.phizic.messaging.loaders.sms.InformingMessageLoader;
import com.rssl.phizic.messaging.loaders.sms.MessageLoader;
import com.rssl.phizic.messaging.loaders.sms.PasswordMessageLoader;
import com.rssl.phizic.messaging.loaders.sms.RefusingMessageLoader;
import com.rssl.phizic.messaging.mail.messagemaking.sms.TemplateMessageBuilder;
import com.rssl.phizic.messaging.push.PushMessage;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.Session;

/**
 * ����� ��� �������� ������� ���-��������� �� ��������
 * @author Rtischeva
 * @ created 20.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class MessageComposeHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final PropertyReader messagingProperties = ConfigFactory.getReaderByFileName("messaging.properties");
	private static final Map<MessageTemplateType, Configuration> PUSH_CONFIGURATIONS;
	private static final int DEFAULT_CACHE_DELAY = 300;  //����� ����� ���� ���������� � ��������

	static
	{
		PUSH_CONFIGURATIONS = new HashMap<MessageTemplateType, Configuration>();
		PUSH_CONFIGURATIONS.put(MessageTemplateType.CONFIRM, createTemplatePushConfiguration(MessageTemplateType.CONFIRM));
		PUSH_CONFIGURATIONS.put(MessageTemplateType.INFORM, createTemplatePushConfiguration(MessageTemplateType.INFORM));
	}

	/**
	 * �������� ������ ���-��������� ��� ����
	 * @param messageKey - ���� ������� ���-���������
	 * @param messageProperties - ���� ����������, ����� ��� ������ ��������� � ������ �����������
	 * @param maskLoggingProperties - ���� ����������, ������� ������ ������������� � ������ �����������
	 * @return
	 */
	public static TextMessage buildErmbMessage(String messageKey, Map<String, ?> messageProperties,  Map<String, ?> maskLoggingProperties)
	{
		try
		{
			return buildTextMessage(MessageTemplateType.INFORM, messageKey, messageProperties, maskLoggingProperties);
		}
		catch (IKFLMessagingException e)
		{
			log.error("������ ��� ��������� ������� ���������", e);
			throw new UserErrorException(new TextMessage("�������� ����������"));
		}
	}

	/**
	 * �������� ������ ���-��������� ��� ���� ��� ����������
	 * @param messageKey - ���� ������� ���-���������
	 * @return
	 */
	public static TextMessage buildErmbMessageWithoutParameters(String messageKey)
	{
		return buildErmbMessage(messageKey, Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	/**
	 * �������� ������ ���-���������
	 * @param templateType - ��� ������� ���-��������� (��������������, �������������, �������������� � ������������� ��������� ��������)
	 * @param messageKey - ���� ������� ���-���������
	 * @param messageProperties  - ���� ���������� ��� ������ ���������
	 * @param loggingProperties - ���� ������������� ���������� ��� ������ ����������� (�� ���, � ������ ��, ������� ������ �������������)
	 * @return
	 * @throws IKFLMessagingException
	 */
	public static TextMessage buildTextMessage(MessageTemplateType templateType, String messageKey, Map<String, ?> messageProperties, Map<String, ?> loggingProperties) throws IKFLMessagingException
	{
		Configuration templateConfiguration = createTemplateSmsConfiguration(templateType);
		String messageText="";
		String loggingText ="";
		try
		{
			TextMessage textMessage = TemplateMessageBuilder.build(templateConfiguration.getTemplateLoader(), messageKey, messageProperties, loggingProperties);
			messageText = textMessage.getText();
			loggingText = textMessage.getTextToLog();
		}
		catch (IKFLMessagingException ex)
		{
			log.error("�� ������� �������� ������ ��� ����� " + messageKey, ex);
			throw ex;
		}
		MessageLoader messageLoader = (MessageLoader) templateConfiguration.getTemplateLoader();
		SMSResource smsResource = messageLoader.getSmsResource();
		TextMessage textMessage = new TextMessage(messageText, loggingText, smsResource.getPriority());
		return textMessage;
	}

	/**
	 * �������� ���������� ��� �������� push-���������
	 *
	 * @param login - ����� ������
	 * @param templateType - ��� ������� push-��������� (��������������, �������������)
	 * @param messageKey - ���� ������� ���-���������
	 * @param messageProperties  - ���� ���������� ��� ������ ���������
	 * @param loggingProperties - ���� ������������� ���������� ��� ������ ����������� (�� ���, � ������ ��, ������� ������ �������������)
	 * @param needAdditionalCheck - ������������� �������� IMSI ��� �������� sms ��������� � ������ ������� �������� push - ���������
	 * @return ��� ���������
	 * @throws IKFLMessagingException
	 */
	public static PushMessage buildPushMessage(CommonLogin login, MessageTemplateType templateType, String messageKey, Map<String, Object> messageProperties, Map<String, Object> loggingProperties, boolean needAdditionalCheck) throws IKFLMessagingException
	{
		return buildPushMessageWithAttributes(login, templateType, messageKey, messageProperties, loggingProperties, needAdditionalCheck, false);
	}

	/**
	 * �������� ���������� ��� �������� push-��������� � �������������� ���������
	 *
	 * @param login - ����� ������
	 * @param templateType - ��� ������� push-��������� (��������������, �������������)
	 * @param messageKey - ���� ������� ���-���������
	 * @param messageProperties  - ���� ���������� ��� ������ ���������
	 * @param loggingProperties - ���� ������������� ���������� ��� ������ ����������� (�� ���, � ������ ��, ������� ������ �������������)
	 * @param needAdditionalCheck - ������������� �������� IMSI ��� �������� sms ��������� � ������ ������� �������� push - ���������
	 * @param composeAttributes - ������������� ���������� ���������
	 * @return ��� ���������
	 * @throws IKFLMessagingException
	 */
	public static PushMessage buildPushMessageWithAttributes(CommonLogin login, MessageTemplateType templateType, String messageKey, Map<String, Object> messageProperties, Map<String, Object> loggingProperties, boolean needAdditionalCheck, boolean composeAttributes) throws IKFLMessagingException
	{
		return  buildPushMessageWithAttributes(login, templateType, messageKey, messageProperties, loggingProperties, needAdditionalCheck, composeAttributes, true);
	}

	/**
	 * �������� ���������� ��� �������� push-��������� � �������������� ���������
	 *
	 * @param login - ����� ������
	 * @param templateType - ��� ������� push-��������� (��������������, �������������)
	 * @param messageKey - ���� ������� ���-���������
	 * @param messageProperties  - ���� ���������� ��� ������ ���������
	 * @param loggingProperties - ���� ������������� ���������� ��� ������ ����������� (�� ���, � ������ ��, ������� ������ �������������)
	 * @param needAdditionalCheck - ������������� �������� IMSI ��� �������� sms ��������� � ������ ������� �������� push - ���������
	 * @param composeAttributes - ������������� ���������� ���������
	 * @param useStopTime - ������������� ��������� ���  <stopTime> ���������� ������� ��������
	 * @return ��� ���������
	 * @throws IKFLMessagingException
	 */
	public static PushMessage buildPushMessageWithAttributes(CommonLogin login, MessageTemplateType templateType, String messageKey, Map<String, Object> messageProperties, Map<String, Object> loggingProperties, boolean needAdditionalCheck, boolean composeAttributes, boolean useStopTime) throws IKFLMessagingException
	{
		try
		{
			Session session = Session.getInstance(messagingProperties.getAllProperties());
			PushMessageBuilder messageBuilder = new PushMessageBuilder(PUSH_CONFIGURATIONS.get(templateType), messageKey, session, null);

			PersonalSubscriptionData contactData = new PersonalSubscriptionData();
			contactData.setLogin(login);
			PushMessage pushMessage = messageBuilder.create(contactData, messageProperties, loggingProperties, composeAttributes, useStopTime);
			pushMessage.setAdditionalCheck(needAdditionalCheck);
			return pushMessage;
		}
		catch (MessagingException ex)
		{
			log.error("������ ��� ��������� ������� ���������", ex);
			throw new IKFLMessagingException(ex);
		}
	}

	private static Configuration createTemplateSmsConfiguration(MessageTemplateType templateType)
	{
		Configuration templateConfiguration = new Configuration();

		if (templateType == MessageTemplateType.INFORM)
			templateConfiguration.setTemplateLoader(new InformingMessageLoader());

		else if (templateType == MessageTemplateType.CONFIRM)
			templateConfiguration.setTemplateLoader(new PasswordMessageLoader());

		else if (templateType == MessageTemplateType.REFUSE)
			templateConfiguration.setTemplateLoader(new RefusingMessageLoader());

		return templateConfiguration;
	}

	private static Configuration createTemplatePushConfiguration(MessageTemplateType templateType)
	{
		Configuration templateConfiguration = new Configuration();
		templateConfiguration.setTemplateUpdateDelay(DEFAULT_CACHE_DELAY);

		if (templateType == MessageTemplateType.INFORM)
		{
			TemplateLoader[] loaders = {new ShortMessageInformingPushLoader(), new FullMessageInformingPushLoader(), new InformingSmsForPushLoader(), new AttributesInformingPushLoader()};
			MultiTemplateLoader loader = new MultiTemplateLoader(loaders);
			templateConfiguration.setTemplateLoader(loader);
		}

		else if (templateType == MessageTemplateType.CONFIRM)
		{
			TemplateLoader[] loaders = {new ShortMessagePasswordPushLoader(), new FullMessagePasswordPushLoader(), new PasswordSmsForPushLoader()};
			MultiTemplateLoader loader = new MultiTemplateLoader(loaders);
			templateConfiguration.setTemplateLoader(loader);
		}

		return templateConfiguration;
	}

	private static Template getTemplate(Configuration templateConfiguration, String key) throws IKFLMessagingException
	{
		try
		{
			return templateConfiguration.getTemplate(key);
		}
		catch (IOException e)
		{
			log.error("�� ������� �������� ������ ��� ����� " + key, e);
			throw new IKFLMessagingException(e);
		}
	}
}
