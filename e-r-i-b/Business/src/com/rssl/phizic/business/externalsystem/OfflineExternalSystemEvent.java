package com.rssl.phizic.business.externalsystem;

import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;

import java.util.Calendar;

/**
 * Записи об ошибках недоступности внешних систем
 * @author Pankin
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class OfflineExternalSystemEvent
{
	private Long id;
	private Adapter adapter;
	private AutoStopSystemType autoStopSystemType;
	private Calendar eventTime;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Adapter getAdapter()
	{
		return adapter;
	}

	public void setAdapter(Adapter adapter)
	{
		this.adapter = adapter;
	}

	public AutoStopSystemType getAutoStopSystemType()
	{
		return autoStopSystemType;
	}

	public void setAutoStopSystemType(AutoStopSystemType autoStopSystemType)
	{
		this.autoStopSystemType = autoStopSystemType;
	}

	public Calendar getEventTime()
	{
		return eventTime;
	}

	public void setEventTime(Calendar eventTime)
	{
		this.eventTime = eventTime;
	}
}
