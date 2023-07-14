package com.rssl.phizic.wsgate.types;

import com.rssl.phizgate.common.routable.AccountBase;

/**
 * @author egorova
 * @ created 01.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class AccountImpl extends AccountBase
{
	private long     branch;

	public long getBranch()
	{
		return branch;
	}

	public void setBranch(long branch)
	{
		this.branch = branch;
	}
}
