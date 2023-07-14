package com.rssl.phizic.operations.sberbankForEveryDay;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * просмор списка заявок СБНКД
 * @author basharin
 * @ created 16.02.15
 * @ $Author$
 * @ $Revision$
 */

public class ListSBNKDClaimOperation extends OperationBase implements ListEntitiesOperation
{
	private static final PersonService personService = new PersonService();
	private ActivePerson person;
	private long loginId;

	public void initialize(Long personId) throws BusinessException
	{
		person =(ActivePerson) personService.findById(personId);
		this.loginId = person.getLogin().getId();
	}

	@Override
	public Query createQuery(String name)
    {
	    Query query = super.createQuery(name);
	    query.setParameter("loginId", loginId);
	    return query;
    }

	public ActivePerson getActivePerson()
	{
		return person;
	}
}
