package com.rssl.phizic.messaging.ext.sbrf.sms;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.messaging.ext.sbrf.MobileBankNotAvailabeException;
import com.rssl.phizic.utils.PhoneNumberUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;

/**
 * @author gladishev
 * @ created 02.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class SendSmsByClientInfoCardsMethod extends SendSmsByPhoneMethodBase
{
	protected Boolean alternative; //использовать альтернативный способ получения регистраций

	protected SendSmsByClientInfoCardsMethod(Login login, TranslitMode translit, IKFLMessage message, Boolean alternative)
	{
		super(translit, login, message);
		this.alternative = alternative;
	}

	public void send(String text,String textToLog, Long priority) throws IKFLMessagingException
	{
		Collection<String> phones = null;
		try
		{
			phones = getPhones(login, alternative);
		}
		catch (BusinessException e)
		{
			throw new IKFLMessagingException(e);
		}

		if (CollectionUtils.isEmpty(phones))
			throw new MobileBankNotAvailabeException("Ни по одной из карт клиента невозможно отправить СМС.");

		if (needAdditionalCheck)
		{
			sendSmsWithAdditionalCheck(text, textToLog, priority, phones.toArray(new String[phones.size()]));
		}
		else
		{
			boolean sended = false;
			for (String phone : phones)
			{
				try
				{
					smsTransportService.sendSms(phone, text, textToLog, priority);
					addRecipientInfo(phone);
					sended = true;
				}
				catch (IKFLMessagingException e)
				{
					log.error(e.getMessage(), e);
					addErrorInfo(PhoneNumberUtil.getCutPhoneNumber(phone), SendMessageError.error);
				}
			}

			if (!sended)
				throw new IKFLMessagingException("Ни по одной из карт клиента не удалось отправить СМС.");
		}
	}
}
