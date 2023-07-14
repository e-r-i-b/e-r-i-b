package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.common.types.MockObject;

/**
 * @author egorova
 * @ created 16.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class MockAccountLink extends AccountLink implements MockObject
{
	private Account account;
	private Office office;

	public void setValue(Account account)
	{
		this.account = account;
	}

	public void setOffice(Office office)
	{
		this.office = office;
	}


    public Account getAccount()
    {
        return account;
    }

    public int hashCode()
    {
	    return account.hashCode();
    }

	public Office getOffice()
	{
		return office;
	}
}
