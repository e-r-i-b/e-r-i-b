package com.rssl.phizicgate.wsgate.services.types;

import com.rssl.phizgate.common.routable.AccountBase;

/**
 * @author: Pakhomova
 * @created: 09.06.2009
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

	public boolean equals(Object o)
	{
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;

	    final AccountImpl account = (AccountImpl) o;

	    if (id != account.id) return false;

	    return true;
	}

    public int hashCode()
    {
        return id.hashCode();
    }

	public String toString()
	{
		return getNumber();
	}
}
