package com.rssl.phizic.business.security.auth;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.context.PersonContext;

/**
 * @author Evgrafov
 * @ created 18.12.2006
 * @ $Author: krenev $
 * @ $Revision: 82782 $
 */

/**
 * В КЛАССЕ НЕ ДОЛЖНО БЫТЬ СОСТОЯНИЯ!!!!!!
 */
public class ClientAthenticationCompleteAction implements AthenticationCompleteAction
{
	private static final PersonService personService = new PersonService();

	public void execute(AuthenticationContext context) throws SecurityException
	{
		try
		{
			Login login = (Login) context.getLogin();
			ActivePerson person = personService.findByLogin(login);

			//Если не можем получить клиента по логину то, надо падать.
			if (person == null)
				throw new SecurityException("Can not find person by login = " + login.getUserId());

			updateContext(person);
		}
		catch (BusinessException e)
		{
			//Если не можем получить клиента по логину то, надо падать.
			throw new SecurityException(e);
		}
	}

	protected void updateContext(ActivePerson person)
	{
		PersonContext.getPersonDataProvider().setPersonData(new StaticPersonData(person));
	}
}