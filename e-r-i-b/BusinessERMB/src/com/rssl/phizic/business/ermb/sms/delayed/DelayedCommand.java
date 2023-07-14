package com.rssl.phizic.business.ermb.sms.delayed;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.common.types.AbstractEntity;
import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

/**
 * @author Gulov
 * @ created 14.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� �������
 */
@PlainOldJavaObject
public class DelayedCommand extends AbstractEntity
{
	/**
	 * ������������� ������������ (�� ������� USERS).
	 * ����� ��� ��������� ������������ � ����� ��������� ���������� ������
	 */
	private Long userId;

	/**
	 * ����� �������
	 */
	private Class<? extends Command> commandClass;

	/**
	 * ���� �������
	 */
	private String commandBody;

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public Class<? extends Command> getCommandClass()
	{
		return commandClass;
	}

	public void setCommandClass(Class<? extends Command> commandClass)
	{
		this.commandClass = commandClass;
	}

	public String getCommandBody()
	{
		return commandBody;
	}

	public void setCommandBody(String commandBody)
	{
		this.commandBody = commandBody;
	}

	@Override
	public String toString()
	{
		return "commandClass=" + commandClass + ", commandBody=" + commandBody;
	}
}
