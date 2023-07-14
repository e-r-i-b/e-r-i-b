package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.documents.ChangeCreditLimitClaim;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * Заявка на изменение кредитного лимита
 * @author lukina
 * @ created 01.07.2015
 * @ $Author$
 * @ $Revision$
 */
public class ChangeCreditLimitStrategyCondition implements StrategyCondition
{
	public String getWarning()
	{
		return null;
	}

	public boolean checkCondition(ConfirmableObject object)
	{
		/**
		 *  изменение кредитного лимита
		 */
		if (object instanceof ChangeCreditLimitClaim)
			return false;

		return true;
	}
}

