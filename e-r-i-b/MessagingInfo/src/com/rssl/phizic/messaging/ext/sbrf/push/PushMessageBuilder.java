package com.rssl.phizic.messaging.ext.sbrf.push;

import com.rssl.phizic.auth.modes.PushPasswordConfirmStrategy;
import com.rssl.phizic.business.push.PushParams;
import com.rssl.phizic.business.push.PushResourcesService;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.push.MessageBuilderType;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.push.PushEventType;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.mail.messagemaking.AddressBuilder;
import com.rssl.phizic.messaging.mail.messagemaking.ContactInfo;
import com.rssl.phizic.messaging.mail.messagemaking.MessageBuilderBase;
import com.rssl.phizic.messaging.push.PushMessage;
import com.rssl.phizic.messaging.push.PushXmlCreator;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.Session;

import static com.rssl.phizic.messaging.mail.messagemaking.TemplateLoaderBase.SEPARATOR;

/**
 * Билдер для создания push-сообщений
 * @ author gorshkov
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 */
public class PushMessageBuilder extends MessageBuilderBase
{
	private static PushResourcesService pushResourcesService = new PushResourcesService();
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private Configuration templateConfiguration;
	private String key;

	/**
	 * ctor
	 * @param templateConfiguration конфигурация
	 * @param key ключ шаблона
	 * @param session сессия
	 * @param addressBuilder строитель адреса
	 * @throws IKFLMessagingException
	 */
	public PushMessageBuilder(Configuration templateConfiguration, String key, Session session, AddressBuilder addressBuilder) throws IKFLMessagingException
	{
		this.key            = key;
		this.priority       = TextMessage.DEFAULT_PRIORITY;
		this.templateConfiguration = templateConfiguration;
	}

	/**
	 * создать пуш сообщение
	 * @param contactInfo контактная информация
	 * @param bean бин
	 * @return сообщение
	 * @throws MessagingException
	 */
	public PushMessage create(ContactInfo contactInfo, Object bean) throws MessagingException
	{
		Map<String, Object> messageProperties = (Map<String, Object>) bean;
		return create(contactInfo, messageProperties, Collections.<String, Object>emptyMap(), false);
	}

	/**
	 * создать пуш сообщения с учетом использования атрибутов
	 * @param contactInfo контактная информация
	 * @param messageProperties параметры сообщения
	 * @param loggingProperties параметры логирования
	 * @param composeAttributes соствлять ли сообщение с атрибутами
	 * @return сообщение
	 * @throws MessagingException
	 */
	public PushMessage create(ContactInfo contactInfo, Map<String, Object> messageProperties, Map<String, Object> loggingProperties, boolean composeAttributes) throws MessagingException
	{
		return create(contactInfo, messageProperties, loggingProperties, composeAttributes, true);
	}

	/**
	 * создать пуш сообщения с учетом использования атрибутов
	 * @param contactInfo контактная информация
	 * @param messageProperties параметры сообщения
	 * @param loggingProperties параметры логирования
	 * @param composeAttributes соствлять ли сообщение с атрибутами
	 * @param useStopTime необходимость добавлять тег  <stopTime> «окончание периода отправки»
	 * @return сообщение
	 * @throws MessagingException
	 */
	public PushMessage create(ContactInfo contactInfo, Map<String, Object> messageProperties, Map<String, Object> loggingProperties, boolean composeAttributes, boolean useStopTime) throws MessagingException
	{
		try
		{
			PushParams pushParams = pushResourcesService.getPushParams(this.key);
			Map<String, Object> messageObject = new HashMap<String, Object>(messageProperties);
			Map<String, Object> loggingObject = new HashMap<String, Object>(messageProperties);

			loggingObject.putAll(loggingProperties);

			String shortText = composePushText(MessageBuilderType.SHORT_MESSAGE, messageObject);
			String fullText = composePushText(MessageBuilderType.FULL_MESSAGE, messageObject);
			String logText = composePushText(MessageBuilderType.FULL_MESSAGE, loggingObject);
			String smsText = composePushText(MessageBuilderType.SMS_MESSAGE, messageObject);

			//Атрибуты пока используются только для сообщений краудгифтинга, поэтому для остальных не делаем лишних запросов к БД
			String attributes = composeAttributes ? composePushText(MessageBuilderType.ATTRIBUTES, messageObject) : StringUtils.EMPTY;

			//убираем все пробелы перед запятыми и точками
			shortText = shortText.replaceAll("\\s+,", ",").replaceAll("\\s+\\.", ".");
			fullText = fullText.replaceAll("\\s+,", ",").replaceAll("\\s+\\.", ".");
			logText = logText.replaceAll("\\s+,", ",").replaceAll("\\s+\\.", ".");
			smsText = smsText.replaceAll("\\s+,", ",").replaceAll("\\s+\\.", ".");

			PushMessage message = new PushMessage();
			message.setRecipientLoginId(contactInfo.getLoginId());
			message.setShortMessage(shortText);
			message.setFullMessage(PushXmlCreator.createXml(fullText, attributes, (int)PushPasswordConfirmStrategy.getLifeTimePassword(), pushParams.isSmsBackup(),
					pushParams.getPrivacyType(), pushParams.getPublicityType(), pushParams.getTypeCode(), useStopTime));
			message.setSmsMessage(smsText);
			message.setTextToLog(PushXmlCreator.createXml(logText, attributes, (int)PushPasswordConfirmStrategy.getLifeTimePassword(), pushParams.isSmsBackup(),
					pushParams.getPrivacyType(), pushParams.getPublicityType(), pushParams.getTypeCode(), useStopTime));
			message.setSmsBackup(pushParams.isSmsBackup());
			message.setPrivacyType(pushParams.getPrivacyType());
			message.setPublicityType(pushParams.getPublicityType());
			message.setTypeCode(PushEventType.fromValue(String.valueOf(pushParams.getTypeCode())));
			return message;
		}
		catch (Exception e)
		{
			throw new MessagingException(e.getMessage(), e);
		}
	}

	public String composeMessageText(Object bean) throws MessagingException
	{
		throw new UnsupportedOperationException("не поддерживаемая операция");
	}

	private String composePushText(MessageBuilderType builderType, Object bean) throws MessagingException
	{
		StringWriter writer = new StringWriter();
		try
		{
			Template template = templateConfiguration.getTemplate(key + SEPARATOR + builderType.getDescription());
			Map messageBeanMap = prepareMessageBean(bean);
			template.process(messageBeanMap, writer);
		}
		catch (FileNotFoundException e)
		{
			if (builderType.isRequired())
			{
				log.error(e.getMessage(), e);
				throw new MessagingException("Ошибка", e);
			}
			return StringUtils.EMPTY;
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