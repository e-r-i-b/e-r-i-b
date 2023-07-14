package com.rssl.phizic.messaging.ext.sbrf.push;

import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.logging.confirm.ConfirmType;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.messaging.ext.sbrf.SendMessageFactory;
import com.rssl.phizic.messaging.ext.sbrf.SendMessageMethod;
import com.rssl.phizic.messaging.push.PushMessage;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.util.ApplicationUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * фабрика дл€ создани€ методов отправки push-уведомлений
 * @author basharin
 * @ created 02.10.13
 * @ $Author$
 * @ $Revision$
 */

public class SendPushFactory implements SendMessageFactory
{
	private static final String NOT_SEND_PHONES = "Ќе удалось отправить push-уведомлени€ на следующие устройства: %s";

	public SendMessageMethod createSendByClientAllInfoCardMethod(Person person, String phone, TranslitMode translit, boolean onlyMobileBank, IKFLMessage message)
	{
		return new SendPushByClientAllInfoCardMethod(person, phone, translit, onlyMobileBank, (PushMessage)message);
	}

	public SendMessageMethod createSendByPhoneMethod(Person person, String phone, TranslitMode translit, IKFLMessage message)
	{
		return new SendPushByPhoneMethod(person, phone, translit, (PushMessage)message);
	}

	public SendMessageMethod createSendByClientInfoCardsMethod(Person person, TranslitMode translit, IKFLMessage message, Boolean useAlternativeRegistrations)
	{
		return new SendPushByClientInfoCardsMethod(person, translit, (PushMessage)message, useAlternativeRegistrations);
	}

	public SendMessageMethod createSendGeneralMethod(Person person, String phone, TranslitMode translit, IKFLMessage message)
	{
		return new SendPushGeneralMethod(person, phone, translit, (PushMessage)message);
	}

	public boolean methodsCanDoAdditionalCheck()
	{
		return false;
	}

	public String getErrorInfoMessage(Map<String, SendMessageError> errorInfo, OperationType operationType)
	{
		if (MapUtils.isEmpty(errorInfo))
			return null;

		StringBuilder resultMessage = new StringBuilder();

		if (ApplicationUtil.isMobileApi())
			resultMessage.append(" ");
		else
			resultMessage.append("<br>");

		resultMessage.append(String.format(NOT_SEND_PHONES, StringUtils.join(errorInfo.keySet(), ", ")));
		return resultMessage.toString();
	}

	public ConfirmType getConfirmType()
	{
		return ConfirmType.PUSH;
	}
}
