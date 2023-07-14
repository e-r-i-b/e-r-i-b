package com.rssl.phizic.operations.certification;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.auth.CommonLogin;

/**
 * @author Omeliyanchuk
 * @ created 06.04.2007
 * @ $Author$
 * @ $Revision$
 */

public class GetPersonCertificateOperation extends OperationBase implements ListEntitiesOperation
{
	private static PersonService personService = new PersonService();

	private ActivePerson activePerson;

	public void setPersonId(Long personId) throws BusinessException
	{
		activePerson = (ActivePerson) personService.findById(personId);
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public CommonLogin getLogin() throws BusinessException
	{
		if(activePerson == null)
			throw new BusinessException("Не установлен пользователь");

		return activePerson.getLogin();
	}	
}

