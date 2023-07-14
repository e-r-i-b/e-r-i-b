package com.rssl.phizic.messaging;

import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.confirm.ConfirmType;
import com.rssl.phizic.logging.confirm.NullOperationConfirmLogWriter;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.logging.confirm.OperationConfirmLogWriter;
import com.rssl.phizic.messaging.ext.sbrf.SendMessageFactory;
import com.rssl.phizic.messaging.ext.sbrf.SendMessageMethod;
import com.rssl.phizic.messaging.ext.sbrf.push.SendPushFactory;
import com.rssl.phizic.messaging.ext.sbrf.sms.SendSmsFactory;
import com.rssl.phizic.messaging.push.PushMessage;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;

/**
 * @author Erkin
 * @ created 11.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class GeneralMessagingService implements MessagingService
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final PersonService personService = new PersonService();
	private static final String SEND_MESSAGE_TO_GUEST = "Отправка сообщения гостю guest_code=%s с текстом '%s' ";

	///////////////////////////////////////////////////////////////////////////

	public String sendSms(IKFLMessage message) throws IKFLMessagingException, IKFLMessagingLogicException
	{
		return sendMessage(message, new SendSmsFactory(), NullOperationConfirmLogWriter.INSTANCE);
	}

	public String sendPush(PushMessage message) throws IKFLMessagingException, IKFLMessagingLogicException
	{
		return sendMessage(message, new SendPushFactory(), NullOperationConfirmLogWriter.INSTANCE);
	}

	public String sendOTPSms(IKFLMessage message) throws IKFLMessagingException, IKFLMessagingLogicException
	{
		return sendMessage(message, new SendSmsFactory(), ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter());
	}

	public String sendOTPPush(PushMessage message) throws IKFLMessagingException, IKFLMessagingLogicException
	{
		return sendMessage(message, new SendPushFactory(), ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter());
	}

	protected String sendMessage(IKFLMessage message, SendMessageFactory sendMessageFactory, OperationConfirmLogWriter confirmLogWriter) throws IKFLMessagingException, IKFLMessagingLogicException
	{
		String messageText = message.getText();
		String textToLog = message.getTextToLog();
		if (StringHelper.isEmpty(textToLog))
			textToLog = messageText;
		try
		{
			IKFLMessageContext messageContext = buildMessageContext(message);

			String phone = messageContext.getMobilePhone();
			TranslitMode translitMode = messageContext.getTranslitMode();
			ActivePerson person = messageContext.getRecipient();

			String logMessage = "";
			if (person != null && person instanceof GuestPerson)
			{
				GuestLogin login = (GuestLogin) person.getLogin();
				logMessage = String.format(SEND_MESSAGE_TO_GUEST, login.getGuestCode(), textToLog) ;
			}
			else if (message.getRecipientLogin() != null && message.getRecipientLogin() instanceof GuestLogin)
			{
				GuestLogin login = (GuestLogin) message.getRecipientLogin();
				logMessage = String.format(SEND_MESSAGE_TO_GUEST, login.getGuestCode(), textToLog) ;
			}
			else
				logMessage = String.format("Отправка сообщения клиенту LOGIN_ID=%s с текстом '%s' ",  message.getRecipientLoginId(), textToLog);
			log.info(logMessage);

			SendMessageMethod sendMessageMethod = sendMessageFactory.createSendGeneralMethod(person, phone, translitMode, message);
			sendMessageMethod.send(messageText, textToLog, message.getPriority());
			logSuccess(confirmLogWriter, sendMessageFactory.getConfirmType(), textToLog, sendMessageMethod.getRecipient(), message.isAdditionalCheck());
			return null;
		}
		catch (IKFLMessagingException e)
		{
			logFailed(confirmLogWriter, sendMessageFactory.getConfirmType(), textToLog, null, message.isAdditionalCheck());
			throw e;
		}
	}

	protected void logSuccess(OperationConfirmLogWriter confirmLogWriter, ConfirmType confirmType, String textToLog, String recipient, boolean additionalCheck)
	{
		switch (confirmType)
		{
			case SMS:
				confirmLogWriter.initializeSMSSuccess(recipient, textToLog, additionalCheck);
				return;
			case PUSH:
				confirmLogWriter.initializePUSHSuccess(recipient, textToLog);
				return;
			default:
				log.error("Недопустимый способ подтверждения " + confirmType);
		}

	}

	protected void logFailed(OperationConfirmLogWriter confirmLogWriter, ConfirmType confirmType, String textToLog, String recipient, boolean additionalCheck)
	{
		switch (confirmType)
		{
			case SMS:
				confirmLogWriter.initializeSMSFailed(recipient, textToLog, additionalCheck);
				return;
			case PUSH:
				confirmLogWriter.initializePUSHFailed(recipient, textToLog);
				return;
			default:
				log.error("Недопустимый способ подтверждения " + confirmType);
		}

	}

	protected IKFLMessageContext buildMessageContext(IKFLMessage message) throws IKFLMessagingException
	{
		if (message == null)
		    throw new NullPointerException("Аргумент 'message' не может быть null");

		ActivePerson recipient = null;

		if (message.getRecipientLogin() == null)
		{
			Long loginId = message.getRecipientLoginId();
			if (loginId == null)
				throw new IKFLMessagingException("Не указан получатель сообщения");

			try
			{
				recipient = personService.findByLoginId(loginId);
				if (recipient == null)
					throw new IKFLMessagingException(
							"Не найден активный клиент с LOGIN_ID=" + loginId);

			}
			catch (BusinessException ex)
			{
				throw new IKFLMessagingException(ex);
			}
		}
		if (StringHelper.isEmpty(message.getText()))
			throw new IKFLMessagingException("Текст сообщения не может быть пустым");
		// Если в сообщении номер телефона указан отдельно,
		// то следует использовать этот номер
		String mobilePhone;
		if (message.getRecipientMobilePhone() != null)
			mobilePhone = message.getRecipientMobilePhone();
		else mobilePhone = recipient.getMobilePhone();

		IKFLMessageContext ctx = new IKFLMessageContext();
		ctx.setMessage(message);
		ctx.setRecipient(recipient);
		ctx.setMobilePhone(mobilePhone);
		ctx.setTranslitMode(recipient == null ? TranslitMode.DEFAULT : getPersonTranslitMode(recipient));
		return ctx;
	}

	private static TranslitMode getPersonTranslitMode(Person person)
	{
		return person.getSMSFormat();
	}
}
