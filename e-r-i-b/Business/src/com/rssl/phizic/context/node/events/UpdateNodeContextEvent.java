package com.rssl.phizic.context.node.events;

import com.rssl.phizic.events.Event;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 19.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������� ���������� ��������� �����
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
	 * �������� ������� ��������� ������ ��������
	 * @param time ��������� ����� ��������� ������ ��������
	 * @return �������
	 */
	public static UpdateNodeContextEvent getStopClientSessionEvent(Calendar time)
	{
		return new UpdateNodeContextEvent(time, Type.STOP_CLIENT);
	}

	/**
	 * �������� ������� ���������� �������
	 * @return �������
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
