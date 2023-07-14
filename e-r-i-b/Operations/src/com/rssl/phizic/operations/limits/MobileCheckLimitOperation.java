package com.rssl.phizic.operations.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;

/**
 * Операция получения размера мобильного кошелька
 * @author Pankin
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class MobileCheckLimitOperation extends OperationBase implements ViewEntityOperation
{
	private Profile entry;

	/**
	 * инициализация операции (получение лимита)
	 */
	public void initialize() throws BusinessException
	{
		if (PersonContext.isAvailable())
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			entry = personData.getProfile();
		}
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return entry;
	}
}
