package com.rssl.phizic.messaging.mail.messagemaking.sms;

import com.rssl.phizic.messaging.mail.rsalarm.PhoneAddress;
import com.rssl.phizic.messaging.mail.messagemaking.AddressBuilder;
import com.rssl.phizic.messaging.mail.messagemaking.ContactInfo;
import com.rssl.phizic.messaging.TranslitMode;

import javax.mail.internet.AddressException;

/**
 * @author Evgrafov
 * @ created 03.07.2006
 * @ $Author: erkin $
 * @ $Revision: 20152 $
 */

public class PhoneAddressBuilder implements AddressBuilder
{
	public PhoneAddress build(ContactInfo contactInfo) throws AddressException
	{
		String mobilePhone = contactInfo.getMobilePhone();
		TranslitMode translit = contactInfo.getSmsTranslitMode();

		if (mobilePhone == null || mobilePhone.equals(""))
			throw new AddressException("Не задан номер мобильного телефона");

		return new PhoneAddress(mobilePhone, translit);
	}
}