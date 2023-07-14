package com.rssl.phizic.logging.monitoring;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Запись о входе клиента.
 *
 * @author bogdanov
 * @ created 03.03.15
 * @ $Author$
 * @ $Revision$
 */

public class MonitoringUserLoginEntry implements MonitoringEntry, Serializable
{
	private Calendar startDate;
	private String application;
	private String tb;
	private String platform;
	private long nodeId;

	public String getApplication()
	{
		return application;
	}

	public void setApplication(String application)
	{
		this.application = application;
	}

	public long getNodeId()
	{
		return nodeId;
	}

	public void setNodeId(long nodeId)
	{
		this.nodeId = nodeId;
	}

	public String getPlatform()
	{
		return platform;
	}

	public void setPlatform(String platform)
	{
		this.platform = platform;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}
}
