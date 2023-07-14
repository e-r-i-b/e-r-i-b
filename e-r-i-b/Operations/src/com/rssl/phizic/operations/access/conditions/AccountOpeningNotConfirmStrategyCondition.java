package com.rssl.phizic.operations.access.conditions;

import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * Не подтверждать открытие вклада
 * @author Pankin
 * @ created 18.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class AccountOpeningNotConfirmStrategyCondition implements StrategyCondition
{
	public String getWarning()
	{
		return null;
	}

	public boolean checkCondition(ConfirmableObject object)
	{
		if (object instanceof AccountOpeningClaim)
			return false;

		return true;
	}
}
