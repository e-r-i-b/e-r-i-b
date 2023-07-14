package com.rssl.phizic.web.gate.services.types;

import com.rssl.phizgate.common.routable.AccountBase;

/**
 * @author: Pakhomova
 * @created: 28.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class AccountImpl extends AccountBase
{
	private long     branch;

	/**
	 * FNCash из Retail, номер филиала котрому пренадлежит счет
	 * @return
	 */
	public long getBranch()
	{
		return branch;
	}

	public void setBranch(long branch)
	{
		this.branch = branch;
	}
}
