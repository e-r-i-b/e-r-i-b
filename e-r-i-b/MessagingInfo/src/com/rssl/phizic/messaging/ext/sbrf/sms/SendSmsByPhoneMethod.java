package com.rssl.phizic.messaging.ext.sbrf.sms;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 09.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class SendSmsByPhoneMethod extends SendSmsByPhoneMethodBase
{
	protected final String phone;

	///////////////////////////////////////////////////////////////////////////

	public SendSmsByPhoneMethod(Login login, String phone, TranslitMode translit, IKFLMessage message)
	{
		super(translit, login, message);
		this.phone = phone;
	}

	public void send(String text, String textToLog, Long priority) throws IKFLMessagingException
	{
		if (StringHelper.isEmpty(phone))
			throw new IKFLMessagingException(
					"В анкете клиента не указан номер телефона");

		try {
			PhoneNumber.fromString(phone);
		} catch (NumberFormatException ex) {
			throw new IKFLMessagingException(
					"В анкете клиента указан некорректный номер телефона: " + phone, ex);
		}

		if (needAdditionalCheck)
		{
			sendSmsWithAdditionalCheck(text, textToLog, priority,  phone);
		}
		else
		{
			try
			{
				addRecipientInfo(phone);
				smsTransportService.sendSms(phone, getTranslit(), text, textToLog, priority);
			}
			catch (IKFLMessagingException e)
			{
				addErrorInfo(PhoneNumberUtil.getCutPhoneNumber(phone), SendMessageError.error);
				throw e;
			}
		}
	}

	public String toString()
	{
		return "SendSmsByPhoneMethod{" +
				"phone='" + PhoneNumberUtil.getCutPhoneNumber(phone) + '\'' +
				'}';
	}
}
