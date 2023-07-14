package com.rssl.phizic.messaging.ext.sbrf.sms;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.logging.confirm.ConfirmType;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.messaging.ext.sbrf.SendMessageFactory;
import com.rssl.phizic.messaging.ext.sbrf.SendMessageMethod;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.util.ApplicationUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * фабрика для создания методов отправки СМС
 * @author basharin
 * @ created 02.10.13
 * @ $Author$
 * @ $Revision$
 */

public class SendSmsFactory implements SendMessageFactory
{
	private static final String NOT_SEND_PHONES = "Не удалось отправить СМС сообщение на следующие номера: %s";
	private static final String IMSI_CHECK_ERROR_MESSAGE = "В целях безопасности Сбербанк приостановил отправку SMS-паролей на номер %s в связи с заменой SIM-карты до момента Вашего обращения в Контактный Центр Сбербанка по телефону 8-800-555-5550.";

	public SendMessageMethod createSendByClientAllInfoCardMethod(Person person, String phone, TranslitMode translit, boolean onlyMobileBank, IKFLMessage message)
	{
		return new SendSmsByClientAllInfoCardMethod(person.getLogin(), phone, translit, onlyMobileBank, message);
	}

	public SendMessageMethod createSendByPhoneMethod(Person person, String phone, TranslitMode translit, IKFLMessage message)
	{
		Login login = (person != null ? person.getLogin() : message.getRecipientLogin());
		return new SendSmsByPhoneMethod(login, phone, translit, message);
	}

	public SendMessageMethod createSendByClientInfoCardsMethod(Person person, TranslitMode translit, IKFLMessage message, Boolean useAlternativeRegistrations)
	{
		Login login = (person != null ? person.getLogin() : message.getRecipientLogin());
		return new SendSmsByClientInfoCardsMethod(login, translit, message, useAlternativeRegistrations);
	}

	public SendMessageMethod createSendGeneralMethod(Person person, String phone, TranslitMode translit, IKFLMessage message)
	{
		Login login = (person != null ? person.getLogin() : message.getRecipientLogin());
		TranslitMode translitMode = (translit != null ? translit : TranslitMode.DEFAULT);

		return new SendSmsGeneralMethod(login, phone, translitMode, message);
	}

	public boolean methodsCanDoAdditionalCheck()
	{
		return true;
	}

	public String getErrorInfoMessage(Map<String, SendMessageError> errorInfo, OperationType operationType)
	{
		if (errorInfo.isEmpty())
			return null;

		List<String> imsiErrorPhones = new ArrayList<String>();
		List<String> otherErrorPhones = new ArrayList<String>();
		for (String phone : errorInfo.keySet())
		{
			if (errorInfo.get(phone) == SendMessageError.imsi_error)
				imsiErrorPhones.add(phone);
			else
				otherErrorPhones.add(phone);
		}

		StringBuilder resultMessage = new StringBuilder();
		if (!imsiErrorPhones.isEmpty() && OperationType.PAYMENT_OPERATION != operationType)
			resultMessage.append(String.format(IMSI_CHECK_ERROR_MESSAGE, StringUtils.join(imsiErrorPhones, ", ")));
		if (!otherErrorPhones.isEmpty())
		{
			if (!imsiErrorPhones.isEmpty())
			{
				if (ApplicationUtil.isMobileApi())
					resultMessage.append(" ");
				else
					resultMessage.append("<br>");
			}

			resultMessage.append(String.format(NOT_SEND_PHONES, StringUtils.join(otherErrorPhones,", ")));
		}

		return resultMessage.toString();
	}

	public ConfirmType getConfirmType()
	{
		return ConfirmType.SMS;
	}
}
