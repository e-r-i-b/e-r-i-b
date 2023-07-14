package com.rssl.phizic.auth;

import java.util.Calendar;

/**
 * @author Roshka
 * @ created 27.12.2005
 * @ $Author$
 * @ $Revision$
 */
class BankLoginImpl extends LoginBase implements BankLogin
{
	private Calendar lastSynchronizationDate;

    BankLoginImpl()
    {
    }

	public Calendar getLastSynchronizationDate()
	{
		return lastSynchronizationDate;
	}

	public void setLastSynchronizationDate(Calendar lastSynchronizationDate)
	{
		this.lastSynchronizationDate = lastSynchronizationDate;
	}
}
