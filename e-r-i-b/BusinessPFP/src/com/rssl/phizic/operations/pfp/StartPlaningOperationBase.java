package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;

/**
 * @author akrenev
 * @ created 30.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * Базовая операция начала планирования
 */

public class StartPlaningOperationBase extends EditPfpOperationBase
{
	protected void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException, BusinessLogicException
	{
		if (isEmployee())
			return;

		if (!isAvailableStartPlaning())
			throw new AccessException("Вы не можете пройти планирование.");
	}
}
