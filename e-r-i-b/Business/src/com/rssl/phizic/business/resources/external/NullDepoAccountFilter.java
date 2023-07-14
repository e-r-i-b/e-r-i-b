package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.depo.DepoAccount;

/**
 * @author mihaylov
 * @ created 03.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class NullDepoAccountFilter extends DepoAccountFilterBase
{
	public boolean accept(DepoAccount depoAccount)
	{
		return true;
	}
}
