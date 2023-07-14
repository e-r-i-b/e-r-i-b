package com.rssl.phizic.business.monitoring.serveravailability;

import java.util.Calendar;

/**
 * Время простоя СП
 * @author gladishev
 * @ created 07.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class ServerIdleTime
{
	private Long id;          //идентификатор записи
	private String serverId;  //идентификатор сервера
	private Calendar startDate;  //дата начала простоя
	private Calendar endDate;    //дата окончания простоя
	private IdleType type;           //Тип неработоспособности: 3(не работал джоб)/1(полная)

	public ServerIdleTime(String serverId, Calendar startDate, Calendar endDate, IdleType type)
	{
		this.serverId = serverId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

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

	public IdleType getType()
	{
		return type;
	}

	public void setType(IdleType type)
	{
		this.type = type;
	}
}
