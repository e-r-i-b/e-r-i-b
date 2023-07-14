package com.rssl.phizic.messaging.ext.sbrf.sms;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;

/**
 * @author lukina
 * @ created 03.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class SendSmsByClientAllInfoCardMethod extends SendSmsByPhoneMethodBase
{
	protected String phone;
	protected boolean onlyMobileBank;


	protected SendSmsByClientAllInfoCardMethod(Login login, String phone, TranslitMode translit, boolean onlyMobileBank, IKFLMessage message)
	{
		super(translit, login, message);
		this.phone = phone;
		this.onlyMobileBank = onlyMobileBank;
	}

	public void send(String text,String textToLog, Long priority) throws IKFLMessagingException
	{
		Collection<String> phones = null;
		try
		{
			phones = getPhones(login, null);
		}
		catch (BusinessException e)
		{
			throw new IKFLMessagingException(e);
		}

		if (CollectionUtils.isEmpty(phones))
			throw new IKFLMessagingException("Hе удалось отправить СМС.");

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
			throw new IKFLMessagingException("Hе удалось отправить СМС.");
	}

	@Override
	protected Collection<String> getPhones(Login login, Boolean alternative) throws BusinessException
	{
		Collection<String> phones = super.getPhones(login, alternative);
		//добавляем номер телефона из анкеты, если его еще нет в списке и способ отправки не "Только МБ"
		if (!onlyMobileBank && StringHelper.isNotEmpty(phone))
		{
			String ikflPhone = PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phone);
			if (!phones.contains(ikflPhone))
				phones.add(phone);
		}

		return phones;
	}
}
