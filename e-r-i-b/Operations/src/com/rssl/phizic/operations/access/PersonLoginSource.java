package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;

/**
 * @author Evgrafov
 * @ created 17.01.2006
 * @ $Author: Kidyaev $
 * @ $Revision: 1743 $
 */

public class PersonLoginSource implements LoginSource
{
	private static final PersonService personService = new PersonService();

	private ActivePerson person;

	public PersonLoginSource(Long personId) throws BusinessException
	{
		person = (ActivePerson) personService.findById(personId);

        if (person == null)
        {
            throw new BusinessException("Пользователь с ID:" + personId + " не найден");
        }
	}

	public CommonLogin getLogin()
	{
		return person.getLogin();
	}

    public ActivePerson getPerson()
    {
        return person;
    }
}
