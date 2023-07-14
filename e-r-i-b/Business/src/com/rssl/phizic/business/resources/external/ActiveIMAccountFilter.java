package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.ima.IMAccountState;

/**
 * фильтр для открытых ОМС
 * @author Pankin
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class ActiveIMAccountFilter implements IMAccountFilter
{
	public boolean accept(IMAccountLink imAccountLink)
	{
		return IMAccountState.opened == imAccountLink.getImAccount().getState() ||
				IMAccountState.lost_passbook == imAccountLink.getImAccount().getState();
	}
}
