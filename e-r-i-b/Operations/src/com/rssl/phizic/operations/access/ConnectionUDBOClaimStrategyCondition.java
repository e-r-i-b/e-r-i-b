package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.documents.RemoteConnectionUDBOClaim;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * Условие не включать карточную стратегию для подключения УДБО
 * @author basharin
 * @ created 19.03.15
 * @ $Author$
 * @ $Revision$
 */

public class ConnectionUDBOClaimStrategyCondition implements StrategyCondition
{
	public String getWarning()
	{
		return null;
	}

	public boolean checkCondition(ConfirmableObject object)
	{
		/**
		 *  подключение УДБО
		 */
		if (object instanceof RemoteConnectionUDBOClaim)
			return false;

		return true;
	}
}
