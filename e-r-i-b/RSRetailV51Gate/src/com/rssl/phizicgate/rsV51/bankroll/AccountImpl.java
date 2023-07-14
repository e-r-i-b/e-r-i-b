package com.rssl.phizicgate.rsV51.bankroll;

import com.rssl.phizgate.common.routable.AccountBase;
import com.rssl.phizic.common.types.Money;

/**
 * @author Kidyaev
 * @ created 30.09.2005
 * @ $Author: filimonova $
 * @ $Revision:3030 $
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

    private void setLongId(long longId)
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
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		AccountImpl account = (AccountImpl) o;

		if (branch != account.branch)
			return false;
		if (longId != account.longId)
			return false;
		if (currency != null ? !currency.equals(account.currency) : account.currency != null)
			return false;
		if (description != null ? !description.equals(account.description) : account.description != null)
			return false;
		if (number != null ? !number.equals(account.number) : account.number != null)
			return false;
		if (openDate != null ? !openDate.equals(account.openDate) : account.openDate != null)
			return false;

		return true;
	}

	public int hashCode()
	{
		int result;
		result = (int) (longId ^ (longId >>> 32));
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (number != null ? number.hashCode() : 0);
		result = 31 * result + (currency != null ? currency.hashCode() : 0);
		result = 31 * result + (openDate != null ? openDate.hashCode() : 0);
		result = 31 * result + (int) (branch ^ (branch >>> 32));
		return result;
	}

	public String toString()
	{
		return getNumber();
	}

	public Money getMaxSumWrite()
    {
       return balance;
    }
}
