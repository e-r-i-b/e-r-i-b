package com.rssl.phizic.business.ermb;

import java.util.Calendar;

/**
 * @author Nady
 * @ created 02.13.2015
 * @ $Author$
 * @ $Revision$
 */

/**
 * Статистика по ЕРМБ профилю
 */
public class ErmbProfileStatistic
{
	private Long id;
	private ErmbProfileImpl profile;
	private Calendar lastRequestTime;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public ErmbProfileImpl getProfile()
	{
		return profile;
	}

	public void setProfile(ErmbProfileImpl profile)
	{
		this.profile = profile;
	}

	public Calendar getLastRequestTime()
	{
		return lastRequestTime;
	}

	public void setLastRequestTime(Calendar lastRequestTime)
	{
		this.lastRequestTime = lastRequestTime;
	}
}
