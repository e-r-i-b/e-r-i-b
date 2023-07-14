package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.clients.RemoteConnectionUDBOHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.remoteConnectionUDBO.RemoteConnectionUDBOConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;

import java.util.Map;

/**
 * проверка на ограничения операций холодного периода для перевода частному лицу
 * @author basharin
 * @ created 20.03.15
 * @ $Author$
 * @ $Revision$
 */

public class ColdPeriodAvailableOperationForRurPaymentValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_IS_OUR_BANK = "isOurBank";
	public static final String FIELD_EXTERNAL_CARD = "externalCard";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Boolean isOurBank = (Boolean) retrieveFieldValue(FIELD_IS_OUR_BANK, values);
		String externalCard = (String) retrieveFieldValue(FIELD_EXTERNAL_CARD, values);

		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		ActivePerson curPerson = provider.getPersonData().getPerson();
		if (!RemoteConnectionUDBOHelper.isColdPeriod(curPerson.getLogin()))
			return true;

		RemoteConnectionUDBOConfig config = ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class);

		if(isOurBank != null && isOurBank)
			return config.isColdPeriodAvailableToOurBankCardPhoneNumber();
		else if (externalCard != null && externalCard.equals("true"))
			return config.isColdPeriodAvailableToOtherBankCard();
		else
			return config.isColdPeriodAvailableToOtherBankAccount();
	}
}
