package com.rssl.phizic.messaging;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.push.PushMessage;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.util.ApplicationUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 * @author Erkin
 * @ created 11.11.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Композитор сообщений
 */
public class MessageComposer
{
	private static final String PASSWORD_MASK = "***";

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final MobilePlatformService mobilePlatformService = new MobilePlatformService();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Составляет сообщение с паролем.
	 * Тект сообщения содержит только пароль
	 * @param login - логин получателя сообщения
	 * @param password - пароль
	 * @return сообщение
	 */
	public IKFLMessage buildSimplePasswordMessage(Login login, String password)
	{
		IKFLMessage message = new IKFLMessage(login.getId(), password, false);
		message.setTextToLog(PASSWORD_MASK);
		return message;
	}

	/**
	 * Составляет сообщение об успешной смене постоянного пароля
	 * @param login - логин получателя сообщения
	 * @param password - пароль
	 * @return сообщение
	 */
	public IKFLMessage buildUserPasswordChangedMessage(Login login, String password) throws IKFLMessagingException, BusinessException
	{
		IKFLMessage ikflMessage = buildInformingSmsMessage(login.getId(), "com.rssl.iccs.user.sms.password");
		String smsText = ikflMessage.getText().replaceAll("\\{0\\}", password);
		String logText = ikflMessage.getText().replaceAll("\\{0\\}", PASSWORD_MASK);

		ikflMessage.setText(smsText);
		ikflMessage.setTextToLog(logText);
		return ikflMessage;
	}

	/**
	 * Составляет сообщение с подтверждением
	 * некоторого подтверждаемого объекта
	 * @param login - логин получателя сообщения
	 * @param confirmableObject - объект, который нужно подтвердить паролем
	 * @param password - пароль
	 *  или null, если для подтверждения пароль не требуется
	 * @param needAdditionalCheck - необходимость проверки IMSI при отправке sms сообщения в случае неудачи отправки push - сообщения
	 * @return сообщение
	 * @throws IKFLMessagingException
	 */
	public PushMessage buildConfirmationPushPasswordMessage(Login login, ConfirmableObject confirmableObject, String password, boolean needAdditionalCheck) throws IKFLMessagingException
	{
		return MessageComposeHelper.buildPushMessage(login, MessageTemplateType.CONFIRM, confirmableObject.getClass().getName(),buildMessageProperties(confirmableObject, password), buildLogProperties(), needAdditionalCheck);
	}

	/**
	 * Составляет сообщение с подтверждением
	 * некоторого подтверждаемого объекта
	 * @param login - логин получателя сообщения
	 * @param confirmableObject - объект, который нужно подтвердить паролем
	 * @param password - пароль
	 *  или null, если для подтверждения пароль не требуется
	 * @param needAdditionalCheck - необходимость проверки IMSI при отправке смс
	 * @return сообщение
	 * @throws IKFLMessagingException
	 */
	public IKFLMessage buildConfirmationSmsPasswordMessage(Login login, ConfirmableObject confirmableObject, String password, boolean needAdditionalCheck)
			throws IKFLMessagingException, BusinessException
	{
		TextMessage textMessage;
		try
		{
			Map<String, Object> maskLoggingProperties = buildLogProperties();
			Map<String, Object> messageProperties = buildMessageProperties(confirmableObject, password);
			if (ApplicationUtil.isMobileApi() || ApplicationUtil.isSocialApi())
				messageProperties.put("applicationName", mobilePlatformService.findByPlatformId(AuthenticationContext.getContext().getDeviceInfo()).getPlatformName());
			textMessage = MessageComposeHelper.buildTextMessage(MessageTemplateType.CONFIRM, confirmableObject.getClass().getName(), messageProperties, maskLoggingProperties);
		}
		catch (IKFLMessagingException e)
        {
            log.error("Ошибка при обработке шаблона передачи пароля", e);
	        textMessage = new TextMessage(password, PASSWORD_MASK);
        }


		IKFLMessage message = null;
		if (!(login instanceof GuestLogin))
		{
			Long loginId = login.getId();
			message = new IKFLMessage(loginId, textMessage.getText(), textMessage.getTextToLog(), needAdditionalCheck, textMessage.getPriority(),
							loginId != null && ErmbHelper.hasErmbProfileByLogin(loginId));
		}
		//У гостя нет ИД логина
		else
		{
			message = new IKFLMessage(null, textMessage.getText(), textMessage.getTextToLog(), needAdditionalCheck, textMessage.getPriority(), false);
			message.setRecipientLogin(login);
		}

		return message;
	}

	private Map<String, Object> buildLogProperties()
	{
		return Collections.<String, Object>singletonMap("password", PASSWORD_MASK);
	}

	private Map<String, Object> buildMessageProperties(ConfirmableObject confirmableObject, String password)
	{
		Map<String, Object> messageProperties = new HashMap<String, Object>();
		messageProperties.put("document", confirmableObject);
		messageProperties.put("password", password);
		return messageProperties;
	}

	/**
	 * Составляет СМС с информацией о невозможности совершить операцию.
	 * @param login - получатель сообщения
	 * @param confirmableObject - объект операция по которому не была совершена
	 * @return сообщение
	 * @throws IKFLMessagingException
	 */
	public IKFLMessage buildRefusePaymentMessage(Login login, ConfirmableObject confirmableObject)
			throws IKFLMessagingException, BusinessException
	{
		Map<String, Object> messageProperties = new HashMap<String, Object>();
		messageProperties.put("document", confirmableObject);
		TextMessage textMessage = MessageComposeHelper.buildTextMessage(MessageTemplateType.REFUSE, confirmableObject.getClass().getName(), messageProperties, Collections.<String, Object>emptyMap());
		return new IKFLMessage(login.getId(), textMessage.getText(), textMessage.getTextToLog(), false, textMessage.getPriority(), ErmbHelper.hasErmbProfileByLogin(login.getId()));
	}

	/**
	 * Формирует сообщение используя messageKey в качестве ключа при поиске подходящего ресурса содержащего
	 * текст смс
	 *
	 * @param loginId идентификатор логина
	 * @param messageKey ключ искомого ресурса
	 *
	 * @return IKFLMessage
	 * @throws IKFLMessagingException
	 */
	public static IKFLMessage buildInformingSmsMessage(Long loginId, String messageKey) throws IKFLMessagingException, BusinessException
	{
		return buildInformingSmsMessage(loginId, messageKey, Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	public static IKFLMessage buildInformingSmsMessage(Long loginId, String messageKey, Map<String, Object> messageProperties, Map<String, Object> loggingProperties) throws IKFLMessagingException, BusinessException
	{
		TextMessage textMessage = MessageComposeHelper.buildTextMessage(MessageTemplateType.INFORM, messageKey, messageProperties, loggingProperties);
		return new IKFLMessage(loginId, textMessage.getText(), textMessage.getTextToLog(), false, textMessage.getPriority(), ErmbHelper.hasErmbProfileByLogin(loginId));
	}

	/**
	 * Формирует сообщение используя messageKey в качестве ключа при поиске подходящего ресурса содержащего
	 * текст push-сообщения
	 *
	 *
	 * @param login идентификатор логина
	 * @param messageKey ключ искомого ресурса
	 *
	 * @return IKFLMessage
	 * @throws IKFLMessagingException
	 */
	public static PushMessage buildInformingPushMessage(CommonLogin login, String messageKey) throws IKFLMessagingException
	{
		return MessageComposeHelper.buildPushMessage(login, MessageTemplateType.INFORM, messageKey, Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap(), false);
	}

	public static PushMessage buildInformingPushMessage(CommonLogin login, String messageKey, Map<String, Object> messageProperties, Map<String, Object> loggingProperties) throws IKFLMessagingException
	{
		return MessageComposeHelper.buildPushMessage(login, MessageTemplateType.INFORM, messageKey, messageProperties, loggingProperties, false);
	}

	/**
	 * Формирует сообщение используя messageKey в качестве ключа при поиске подходящего ресурса содержащего текст push-сообщения
	 * @param messageKey ключ искомого ресурса
	 * @param messageProperties мапа параметров для текста сообщения
	 * @return IKFLMessage
	 * @throws IKFLMessagingException
	 */
	public static PushMessage buildInformingPushMessage(String messageKey, Map<String, Object> messageProperties) throws IKFLMessagingException
	{
		return buildInformingPushMessage(messageKey, messageProperties, true);
	}

	/**
	 * Формирует сообщение используя messageKey в качестве ключа при поиске подходящего ресурса содержащего текст push-сообщения
	 * @param messageKey ключ искомого ресурса
	 * @param messageProperties мапа параметров для текста сообщения
	 * @param useStopTime необходимость добавлять тег  <stopTime> «окончание периода отправки»
	 * @return IKFLMessage
	 * @throws IKFLMessagingException
	 */
	public static PushMessage buildInformingPushMessage(String messageKey, Map<String, Object> messageProperties, boolean useStopTime) throws IKFLMessagingException
	{
		return MessageComposeHelper.buildPushMessageWithAttributes(null, MessageTemplateType.INFORM, messageKey, messageProperties, Collections.<String, Object>emptyMap(), false, true, useStopTime);
	}

	/**
	 * @param login получатель
	 * @return сообщение с предупреждением о безопасности
	 */
	public IKFLMessage buildWarningMessage(Login login) throws IKFLMessagingException, BusinessException
	{
		return buildInformingSmsMessage(login.getId(), "com.rssl.iccs.user.sms.confirm.warning.second");
	}

	/**
	 *
	 * @param password пароль
	 * @return текст сообщения при смене держателя номера ЕРМБ или МБК
	 * @throws IKFLMessagingException
	 */
	public IKFLMessage buildASFilialConfirmHolderMessage(String password) throws IKFLMessagingException
	{
		TextMessage message = MessageComposeHelper.buildTextMessage(
				MessageTemplateType.INFORM,
				"com.rssl.iccs.asfilial.confirm.holder.sms",
				Collections.singletonMap("password", password),
				Collections.singletonMap("password", PASSWORD_MASK)
		);
		return new IKFLMessage(null, message.getText(), message.getTextToLog(), false, message.getPriority(), true);
	}
}