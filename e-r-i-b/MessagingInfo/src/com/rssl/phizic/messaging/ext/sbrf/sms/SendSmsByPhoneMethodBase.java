package com.rssl.phizic.messaging.ext.sbrf.sms;

import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.mobilebank.MessageInfo;
import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.messaging.ext.sbrf.MessageInfoImpl;
import com.rssl.phizic.messaging.ext.sbrf.SendMessageMethodBase;
import com.rssl.phizic.messaging.mail.SmsTransportService;
import com.rssl.phizic.utils.PhoneNumberUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author gladishev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */
abstract class SendSmsByPhoneMethodBase extends SendMessageMethodBase
{
	private static final String STUB_TEXT_KEY = "com.rssl.phizic.messaging.ext.sbrf.SendSmsByPhoneMethodBase.stubText";
	private static final String STUB_TEXT_TEMPLATEMESSAGE_KEY = "com.rssl.phizic.messaging.ext.sbrf.SendSmsByPhoneMethodBase.stubTextTemplateMessage";
	private static final String STUB_TEXT_USERPROFILEMESSAGE_KEY = "com.rssl.phizic.messaging.ext.sbrf.SendSmsByPhoneMethodBase.stubTextUserProfileMessage";
	private static final String STUB_TEXT_REGISTRATION_MESSAGE_KEY = "com.rssl.phizic.messaging.ext.sbrf.SendSmsByPhoneMethodBase.stubTextRegistrationMessage";
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected  SmsTransportService smsTransportService;


	/**
	 * Требуется ли дополнительная проверка.
	 */
	protected boolean needAdditionalCheck = false;

	/**
	 * Логин клиента, для которого отправляется смс.
	 */
	protected Login login;
	protected OperationType operationType;

	private List<String> recipientInfo = new ArrayList<String>();

	/**
	 * Результат проверки IMSI.
	 */
	private boolean imsiResult = true;

	protected SendSmsByPhoneMethodBase(TranslitMode translit, Login login, IKFLMessage message)
	{
		super(translit);
		this.login = login;
		this.needAdditionalCheck = message.isAdditionalCheck();
		this.operationType = message.getOperationType();
		smsTransportService = MessagingSingleton.getInstance().getSmsTransportService(message.isErmbConnectedPerson(), needAdditionalCheck);
	}

	public String getRecipient()
	{
		return StringUtils.join(recipientInfo, "; ");
	}

	protected void addRecipientInfo(String info)
	{
		recipientInfo.add(info);
	}

	protected void sendSmsWithAdditionalCheck(String text, String textToLog,Long priority,  String... phones) throws IKFLMessagingException
	{
		//1. отправляем на все телефоны смс
		Map<String, SendMessageError> result = null;
		try
		{
			result = smsTransportService.sendSmsWithIMSICheck(createMessageInfo(text, textToLog), priority, phones);
		}
		catch (IKFLMessagingException me)
		{
			log.error(me.getMessage(), me);
		}

		//2. обрабатываем результат
		boolean sended = result.size() == 0;
		for (String phone : phones)
		{
			if(result.containsKey(phone))
			{
				addErrorInfo(PhoneNumberUtil.getCutPhoneNumber(phone), result.get(phone));
				if (result.get(phone) == SendMessageError.imsi_error)
				{
					imsiResult = false;
					if (OperationType.PAYMENT_OPERATION == operationType)
					{
						try
						{
							smsTransportService.sendSms(phone, text, textToLog, priority);
							addRecipientInfo(phone);//Отправили смс, значит получатель есть.
							sended = true;
						}
						catch (IKFLMessagingException e)
						{
							log.error(e.getMessage(), e);
						}
					}
				}
			}
			else
			{
				addRecipientInfo(phone);
				sended = true;
			}
		}

		if (OperationType.PAYMENT_OPERATION == operationType && !sended)
			throw new IKFLMessagingException("Не удалось отправить сообщение ни на один из телефонных номеров.");

		if (OperationType.PAYMENT_OPERATION != operationType && result.size() == phones.length)
			throw new IKFLMessagingException("Не удалось отправить сообщение ни на один из телефонных номеров.");
	}

	@Override
	public boolean imsiResult()
	{
		return imsiResult;
	}

	private MessageInfo createMessageInfo(String text, String textToLog)
	{
		//login может быть null при отправки сообщений из админки, или по команде из интегрированных шлюзов
		if (login != null && !(login instanceof GuestLogin))
		{
			try
			{
				if (operationType == OperationType.CREATE_TEMPLATE_OPERATION)
					return new MessageInfoImpl(text, textToLog, MessageComposer.buildInformingSmsMessage(login.getId(), STUB_TEXT_TEMPLATEMESSAGE_KEY).getText());

				if (operationType == OperationType.EDIT_USER_SETTINGS)
					return new MessageInfoImpl(text, textToLog, MessageComposer.buildInformingSmsMessage(login.getId(), STUB_TEXT_USERPROFILEMESSAGE_KEY).getText());

				if (operationType == OperationType.REGISTRATION_OPERATION)
					return new MessageInfoImpl(text, textToLog, MessageComposer.buildInformingSmsMessage(login.getId(), STUB_TEXT_REGISTRATION_MESSAGE_KEY).getText());

				return new MessageInfoImpl(text, textToLog, MessageComposer.buildInformingSmsMessage(login.getId(), STUB_TEXT_KEY).getText());
			}
			catch (IKFLMessagingException e)
			{
				log.error(e.getMessage(), e);
				return new MessageInfoImpl(text, textToLog, null);
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
				return new MessageInfoImpl(text, textToLog, null);
			}
		}
		else
			return new MessageInfoImpl(text,textToLog);
	}

	protected Collection<String> getPhones(Login login, Boolean alternative) throws BusinessException
	{
		try
		{
			if (login instanceof GuestLogin)
				return PersonContext.getPersonDataProvider().getPersonData().getPhones(true);
			else
				return StaticPersonData.getPhones(login, alternative);
		}
		catch(BusinessLogicException e)
		{
			throw new BusinessException(e);
		}
	}

}
