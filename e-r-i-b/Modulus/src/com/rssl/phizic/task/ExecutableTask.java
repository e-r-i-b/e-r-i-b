package com.rssl.phizic.task;

/**
 * @author Erkin
 * @ created 12.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Выполнимая задача.
 * Знает как себя выполнить.
 */
public interface ExecutableTask extends Task
{
	/**
	 * Выполнить задачу
	 */
	void execute();
}
