package com.rssl.phizic.task;

/**
 * @author Erkin
 * @ created 13.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Исключение "Задача не готова (к выполнению)"
 * Выбрасывается, если указаны не все необходимые параметры задачи
 */
public class TaskNotReadyException extends IllegalStateException
{
	/**
	 * ctor
	 */
	public TaskNotReadyException()
	{
	}

	/**
	 * ctor
	 * @param s
	 */
	public TaskNotReadyException(String s)
	{
		super(s);
	}
}
