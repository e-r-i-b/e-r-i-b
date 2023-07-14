package com.rssl.phizic.operations.autosubscription;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

/**
 * Ѕазовый класс операций дл€ работы с подписками автоплатежей.
 * (поскольку в ј–ћ сотрудника используетс€ контекст клиента,
 * то мы должны при открытии какого-либо фун-ла выполн€ть проверку на его заполненность)
 *
 * @author khudyakov
 * @ created 14.02.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class AutoSubscriptionOperationBase extends OperationBase
{
	private PersonData personData;

	public void initialize() throws BusinessException, BusinessLogicException
	{
		if (!PersonContext.isAvailable())
			throw new BusinessException("PersonContext не проинициализирован персоной.");

		PersonData temp = PersonContext.getPersonDataProvider().getPersonData();
		if (temp.getPerson() == null)
			throw new BusinessException("PersonContext не проинициализирован персоной.");

		personData = temp;
	}

	public PersonData getPersonData()
	{
		return personData;
	}
	/**
	 * @return персона
	 */
	public ActivePerson getPerson()
	{
		return personData.getPerson();
	}
}
