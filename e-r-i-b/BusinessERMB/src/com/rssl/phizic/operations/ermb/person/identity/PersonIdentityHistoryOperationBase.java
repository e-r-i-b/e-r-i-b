package com.rssl.phizic.operations.ermb.person.identity;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.operations.OperationBase;

/**
 * User: moshenko
 * Date: 24.03.2013
 * Time: 13:35:52
 */
public abstract class PersonIdentityHistoryOperationBase extends OperationBase
{
	private static final PersonService personService = new PersonService();
	private ActivePerson person;

	public ActivePerson getPerson()
	{
		return person;
	}

	public void initialize(Long value) throws BusinessException, BusinessLogicException
	{
		this.person = (ActivePerson) personService.findById(value, null);
	}
}
