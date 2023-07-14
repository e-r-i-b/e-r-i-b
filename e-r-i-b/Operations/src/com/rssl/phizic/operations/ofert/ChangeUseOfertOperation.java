package com.rssl.phizic.operations.ofert;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.userSettings.SettingsProcessor;
import com.rssl.phizic.userSettings.UserPropertiesConfig;

/**
 * Операция изменения признака оферты
 * @author osminin
 * @ created 22.10.2009
 * @ $Author$
 * @ $Revision$
 */

public class ChangeUseOfertOperation extends OperationBase
{
	private static final PersonService service = new PersonService();
	private ActivePerson person;

	public void initialize(Login login) throws BusinessException
	{
		person = service.findByLogin(login);
	}

	public ActivePerson getPerson() throws BusinessException
	{
		return person;
	}

	public void setUseOfert(final Boolean useOfert) throws BusinessException
	{
		UserPropertiesConfig.processUserSettingsWithoutPersonContext(person.getLogin(), new SettingsProcessor<Void>()
		{
			public Void onExecute(UserPropertiesConfig userProperties)
			{
				userProperties.setUseOfert(useOfert);
				return null;
			}
		});
	}
}
