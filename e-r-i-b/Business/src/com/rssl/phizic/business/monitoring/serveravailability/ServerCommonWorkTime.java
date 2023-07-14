package com.rssl.phizic.business.monitoring.serveravailability;

import java.util.Calendar;

/**
 * Общее время работы СП
 * @author gladishev
 * @ created 07.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class ServerCommonWorkTime
{
	private String serverId;     //идентификатор сервера
	private Calendar startDate;   //дата начала работы СП
	private Calendar endDate;    //дата окончания работы СП
	private Calendar endPingDate;    //дата последнего удачного пинга

	public String getServerId()
	{
		return serverId;
	}

	public void setServerId(String serverId)
	{
		this.serverId = serverId;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public Calendar getEndPingDate()
	{
		return endPingDate;
	}

	public void setEndPingDate(Calendar endPingDate)
	{
		this.endPingDate = endPingDate;
	}
}
