package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.ima.IMAccountState;

/**
 * ‘ильтр, провер€ющий что мет счет арестован или активен
 * @author basharin
 * @ created 05.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ActiveOrArrestedIMAccountFilter implements IMAccountFilter
{
	public boolean accept(IMAccountLink imAccountLink)
	{
		return IMAccountState.opened == imAccountLink.getImAccount().getState() ||
				IMAccountState.lost_passbook == imAccountLink.getImAccount().getState() ||
				IMAccountState.arrested == imAccountLink.getImAccount().getState();
	}
}
