package com.rssl.phizic.business.ermb.migration.list.task;

import java.util.Set;

/**
 * Задача-одиночка, исполняемая в отдельном потоке
 * @author Puzikov
 * @ created 17.12.13
 * @ $Author$
 * @ $Revision$
 */

public interface Task extends Runnable
{
	/**
	 * @return тип задачи
	 */
	public TaskType getType();

	/**
	 * @return текущее состояние задачи
	 */
	public String getStatus();

	/**
	 * @return завершена ли задача
	 */
	public boolean isDone();

	/**
	 * прервать выполнение задачи
	 */
	public void interrupt();

	/**
	 * @return задачи, запрещенные к одновременному выполнению с данной
	 */
	public Set<TaskType> getIllegalTasks();
}
