package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.ima.IMAccountState;

/**
 * @author basharin
 * @ created 11.09.14
 * @ $Author$
 * @ $Revision$
 */

public class NotArrestedIMAccountFilter implements IMAccountFilter
{
	public boolean accept(IMAccountLink imAccountLink)
	{
		return IMAccountState.arrested != imAccountLink.getImAccount().getState();
	}
}
