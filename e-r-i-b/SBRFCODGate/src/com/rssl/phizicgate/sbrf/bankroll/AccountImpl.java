package com.rssl.phizicgate.sbrf.bankroll;

import com.rssl.phizgate.common.routable.AccountBase;

/**
 * @author Omeliyanchuk
 * @ created 21.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class AccountImpl extends AccountBase
{
    public boolean equals ( Object o )
	{
		if (this==o)
		{
			return true;
		}
		if (o==null || getClass()!=o.getClass())
		{
			return false;
		}
		return id.equals(id);
	}

	public int hashCode ()
	{
		return id.hashCode();
	}
}
