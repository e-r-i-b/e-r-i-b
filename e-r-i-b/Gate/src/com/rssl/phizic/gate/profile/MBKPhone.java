package com.rssl.phizic.gate.profile;

import java.util.Calendar;

/**
 * @author bogdanov
 * @ created 23.01.15
 * @ $Author$
 * @ $Revision$
 */

public class MBKPhone
{
	private long id;
	private String phone;
	private Calendar lastUpdateTime;
	private boolean added;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public boolean isAdded()
	{
		return added;
	}

	public void setAdded(boolean added)
	{
		this.added = added;
	}

	public Calendar getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Calendar lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}
}
