package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.depo.DepoAccountState;

/**
 * @author mihaylov
 * @ created 03.09.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Проверяет является ли счет депо открытым и разрешенным для операций
 */
public class OpenAllowedDepoAccountFilter extends DepoAccountFilterBase
{
	public boolean accept(DepoAccount depoAccount)
	{
		return depoAccount.getState() == DepoAccountState.open && depoAccount.getIsOperationAllowed();
	}
}
