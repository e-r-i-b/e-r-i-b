package com.rssl.phizic.operations.access.conditions;

import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * Условие подтверждения для операции открытия вклада. BUG036231.
 * @author Pankin
 * @ created 17.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class AccountOpeningStrategyCondition implements StrategyCondition
{
	public String getWarning()
	{
		return null;
	}

	public boolean checkCondition(ConfirmableObject object)
	{
		if (object instanceof AccountOpeningClaim)
		{
			// Нужно подтвержать СМС-паролем открытие вклада без начального взноса (Сберегательный счет)
			if (((AccountOpeningClaim)object).isNeedInitialFee())
			{
				return false;
			}
		}
		return true;
	}
}
