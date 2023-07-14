package com.rssl.phizicgate.rsretailV6r4.bankroll;

import com.rssl.phizgate.common.routable.AccountBase;

/**
 * @author Danilov
 * @ created 03.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class AccountImpl extends AccountBase
{
    private long     longId;
	private long     branch;

    public String getId()
    {
	    if (super.getId() != null)
            return super.getId();

	    return String.valueOf(longId);
    }

    long getLongId()
    {
        return longId;
    }

    public void setLongId(long longId)
    {
        this.longId = longId;
    }

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

	    if (longId != account.longId) return false;

	    return true;
	}

    public int hashCode()
    {
        return (int) (longId ^ (longId >>> 32));
    }

	public String toString()
	{
		return getNumber();
	}
}
