package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.clients.RemoteConnectionUDBOHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.remoteConnectionUDBO.RemoteConnectionUDBOConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * проверка на ограничения операций холодного периода для перевода между своими счетами, картами и мет счетами
 * @author basharin
 * @ created 25.02.15
 * @ $Author$
 * @ $Revision$
 */

public class ColdPeriodAvailableOperationValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_FROM_RESOURCE_TYPE = "fromResourceType";
	public static final String FIELD_TO_RESOURCE_TYPE = "toResourceType";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String fromResourceType = (String) retrieveFieldValue(FIELD_FROM_RESOURCE_TYPE, values);
		String toResourceType = (String) retrieveFieldValue(FIELD_TO_RESOURCE_TYPE, values);
		// если не задан счет зачисления(в случае шаблона), проверять нечего
		if (StringHelper.isEmpty(fromResourceType) || StringHelper.isEmpty(toResourceType))
			return true;

		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		ActivePerson curPerson = provider.getPersonData().getPerson();
		if (!RemoteConnectionUDBOHelper.isColdPeriod(curPerson.getLogin()))
			return true;

		RemoteConnectionUDBOConfig config = ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class);

		if (fromResourceType.equals(CardLink.class.getName()))
		{
			if (toResourceType.equals(CardLink.class.getName()))
				return config.isColdPeriodAvailableCardToCard();
			else if (toResourceType.equals(AccountLink.class.getName()))
				return config.isColdPeriodAvailableCardToAccount();
			else if (toResourceType.equals(IMAccountLink.class.getName()))
				return config.isColdPeriodAvailableCardToIMAccount();
		}
		else if (fromResourceType.equals(AccountLink.class.getName()) && toResourceType.equals(CardLink.class.getName()))
		{
			return config.isColdPeriodAvailableAccountToCard();
		}
		else if (fromResourceType.equals(IMAccountLink.class.getName()) && toResourceType.equals(CardLink.class.getName()))
		{
			return config.isColdPeriodAvailableIMAccountToCard();
		}
		return true;
	}
}
