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
 * Отложенная команда
 */
@PlainOldJavaObject
public class DelayedCommand extends AbstractEntity
{
	/**
	 * Идентификатор пользователя (из таблицы USERS).
	 * Нужен для получения пользователя в джобе обработки отложенных команд
	 */
	private Long userId;

	/**
	 * Класс команды
	 */
	private Class<? extends Command> commandClass;

	/**
	 * Тело команды
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
