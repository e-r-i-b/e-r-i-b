package com.rssl.phizic.messaging.ext.sbrf.sms;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.messaging.TranslitMode;

/**
 * общий метод отправки СМС сообщений
 * @author basharin
 * @ created 02.10.13
 * @ $Author$
 * @ $Revision$
 */

public class SendSmsGeneralMethod extends SendSmsByPhoneMethodBase
{
	protected final String phone;

	protected SendSmsGeneralMethod(Login login, String phone, TranslitMode translit, IKFLMessage message)
	{
		super(translit, login, message);
		this.phone = phone;
	}

	public void send(String text, String textToLog, Long priority) throws IKFLMessagingException
	{
		smsTransportService.sendSms(phone, getTranslit(), text, textToLog, priority);
		addRecipientInfo(phone);
	}
}
