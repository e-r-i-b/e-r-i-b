package com.rssl.phizic.context.node.events;

import com.rssl.phizic.events.Event;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 19.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Событие обновления контекста блока
 */

public class UpdateNodeContextEvent implements Event
{
	public static final String TYPE = "UpdateNodeContextEvent";

	private final Type type;
	private Calendar kickTime;

	private UpdateNodeContextEvent(Type type)
	{
		this.type = type;
	}

	private UpdateNodeContextEvent(Calendar kickTime, Type type)
	{
		this(type);
		this.kickTime = kickTime;
	}

	/**
	 * получить событие остановки сессий клиентов
	 * @param time временная метка остановки сессий клиентов
	 * @return событие
	 */
	public static UpdateNodeContextEvent getStopClientSessionEvent(Calendar time)
	{
		return new UpdateNodeContextEvent(time, Type.STOP_CLIENT);
	}

	/**
	 * получить событие обновления конфига
	 * @return событие
	 */
	public static UpdateNodeContextEvent getRefreshEvent()
	{
		return new UpdateNodeContextEvent(Type.REFRESH_CONFIG);
	}

	Type getType()
	{
		return type;
	}

	Calendar getKickTime()
	{
		return kickTime;
	}

	public String getStringForLog()
	{
		return TYPE;
	}
}
