package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.context.PersonContext;

/**
 * @author Rydvanskiy
 * @ created 13.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ListServicesPaymentSearchOperation extends ListServicesPaymentOperation
{
	private ActivePerson person;

	/**
	 * Инициализация операции
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void initialize() throws BusinessException
	{
		if (!PersonContext.isAvailable())
			throw new BusinessException("PersonContext не проинициализирован персоной.");

		ActivePerson temp = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		if (temp == null)
			throw new BusinessException("PersonContext не проинициализирован персоной.");

		person = temp;
	}

	/**
	 * @return персона
	 */
	public ActivePerson getPerson()
	{
		return person;
	}
}
