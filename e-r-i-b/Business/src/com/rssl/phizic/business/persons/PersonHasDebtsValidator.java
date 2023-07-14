package com.rssl.phizic.business.persons;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.chargeoff.ChargeOffLogService;
import com.rssl.phizic.business.chargeoff.ChargeOffLog;
import com.rssl.phizic.business.BusinessException;

import java.util.Map;
import java.util.List;

/**
 * Проверяем есть ли у клиента долги
 * @author egorova
 * @ created 11.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class PersonHasDebtsValidator extends MultiFieldsValidatorBase
{
	protected static final PersonService personService = new PersonService();
	private static final ChargeOffLogService chargeoffService = new ChargeOffLogService();

	public static final String CLIENT_ID = "clientId";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String clientId = (String) retrieveFieldValue(CLIENT_ID, values);
		if(clientId == null) return false;
		ActivePerson person = null;
		try
		{
			person = personService.findByClientId(clientId, null);

			if( person == null )
				return false;

			List<ChargeOffLog> personDebts = chargeoffService.getPersonsDebts(person.getLogin());

			return !personDebts.isEmpty();
		}
		catch(BusinessException ex)
		{
			return false;
		}
	}
}
