package com.rssl.phizic.business.ermb.migration.mbk.registrator;

import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.disconnector.DisconnectorPhone;
import com.rssl.phizic.business.ermb.disconnector.ErmbPhoneDisconnector;
import com.rssl.phizic.gate.mobilebank.PhoneDisconnectionReason;
import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.business.ermb.migration.mbk.registrator.sender.ProcessResult;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * Обработчик сообщений на отключение услуги МБ в ЕРИБ
 * @author Puzikov
 * @ created 09.07.14
 * @ $Author$
 * @ $Revision$
 */

public class RemoveRegistrationMbkMessageProcessor extends MbkMessageProcessorBase
{

	public ProcessResult process(MBKRegistration mbkMessage) throws IKFLException
	{
		PhoneNumber phoneNumber = mbkMessage.getPhoneNumber();
		ErmbProfileImpl profile = findByPhone(phoneNumber);
		if (profile == null)
		{
			return createErrorResult(mbkMessage, false, "Не найден профиль клиента по данному телефону");
		}

		if (!checkFioDulDr(profile.getPerson(), mbkMessage))
		{
			return createErrorResult(mbkMessage, false, "Телефон не отключен от Мобильного банка. Указанный телефон подключен другому клиенту");
		}

		ErmbPhoneDisconnector disconnector = new DisconnectorPhone();
		disconnector.disconnect(PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phoneNumber), PhoneDisconnectionReason.OTHER);

		return createSuccessResult(mbkMessage);
	}

	private boolean checkFioDulDr(Person person, MBKRegistration mbkMessage)
	{
		UserInfo owner = mbkMessage.getOwner();
		String passport = StringUtils.deleteWhitespace(owner.getPassport()).toUpperCase();
		return StringHelper.equalsAsPersonName(person.getSurName(), owner.getSurname())
				&& StringHelper.equalsAsPersonName(person.getFirstName(), owner.getFirstname())
				&& StringHelper.equalsAsPersonName(person.getPatrName(), owner.getPatrname())
				&& PersonHelper.getPersonSeriesAndNumbers((ActivePerson) person).contains(passport)
				&& DateUtils.isSameDay(person.getBirthDay(), owner.getBirthdate());
	}
}
