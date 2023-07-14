package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.counters.CounterType;
import com.rssl.phizic.business.counters.UserCountersHelper;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.context.PersonContext;

import java.util.Map;

/**
 * Проверка лимита запросов в ЕРМБ/МБК по номерну телефона
 * @author lukina
 * @ created 02.11.2014
 * @ $Author$
 * @ $Revision$
 */
public class CheckPhoneLimitValidator extends MultiFieldsValidatorBase
{
	private static final String FIELD_PHONE_NUMBER = "externalPhoneNumber";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Login login = PersonContext.getPersonDataProvider().getPersonData().getLogin();
		String externalPhoneNumber = (String)retrieveFieldValue(FIELD_PHONE_NUMBER, values);
		return UserCountersHelper.checkUserCounters(login, CounterType.RECEIVE_INFO_BY_PHONE, externalPhoneNumber);
	}
}
