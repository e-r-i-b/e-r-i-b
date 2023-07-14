package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.depo.DepoAccountState;

/**
 * Пропустит счет если он открыт и по нему не разрешенны операции
 * @author gladishev
 * @ created 08.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class OpenNotAllowedDepoAccountFilter extends DepoAccountFilterBase
{
	public boolean accept(DepoAccount depoAccount)
	{
		return depoAccount.getState() == DepoAccountState.open && !depoAccount.getIsOperationAllowed();
	}
}
