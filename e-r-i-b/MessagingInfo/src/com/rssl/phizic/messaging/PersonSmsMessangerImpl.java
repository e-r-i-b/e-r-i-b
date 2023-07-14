package com.rssl.phizic.messaging;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.messaging.mail.SmsTransportService;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonManagerBase;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;

/**
 * @author Erkin
 * @ created 14.04.2013
 * @ $Author$
 * @ $Revision$
 */

class PersonSmsMessangerImpl extends PersonManagerBase implements PersonSmsMessanger
{
	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	private final String toPhone;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param module - модуль, в котором работает мессенжер (never null)
	 * @param person - клиент, с которым работает мессенжер (never null)
	 */
	PersonSmsMessangerImpl(Module module, Person person)
	{
		super(module, person);

		try
		{
			// todo: уйти от лишнего запроса профиля. Исполнитель: Еркин С.
			ErmbProfileImpl profile = profileService.findByUser(person);

			toPhone = profile.getMainPhoneNumber();
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	public void sendSms(TextMessage message)
	{
		sendSms(message, toPhone);
	}

	public String getDefaultPhone()
	{
		return toPhone;
	}

	public void sendSms(TextMessage message, String phoneNumber)
	{
		try
		{
			Person person = getPerson();
			SmsTransportService smsTransportService = MessagingSingleton.getInstance().getSmsTransportService(profileService.isErmbProfileExistsByPersonId(person.getId()), false);
			PhoneNumber phoneNumber1 = PhoneNumber.fromString(phoneNumber);
			phoneNumber = PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phoneNumber1);
			smsTransportService.sendSms(phoneNumber, message.getText(), message.getTextToLog(), message.getPriority());
		}
		catch (IKFLMessagingException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}
}
