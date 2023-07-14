package com.rssl.phizic.business.migration;

import java.util.List;

/**
 * @author akrenev
 * @ created 03.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * Задача на миграцию клиентов
 */

public class MigrationTask
{
	private Long id;
	private Long totalCount;
	private boolean needStop;
	private Long batchSize;
	private List<MigrationThreadTask> threadTasks;

	/**
	 * @return идентификатор записи
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор записи
	 * @param id идентификатор записи
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return общее количество мигрируемых клиентов
	 */
	public Long getTotalCount()
	{
		return totalCount;
	}

	/**
	 * задать общее количество мигрируемых клиентов
	 * @param totalCount количество
	 */
	public void setTotalCount(Long totalCount)
	{
		this.totalCount = totalCount;
	}

	/**
	 * @return флаг остановки
	 */
	public boolean isNeedStop()
	{
		return needStop;
	}

	/**
	 * задать флаг остановки
	 * @param needStop флаг
	 */
	public void setNeedStop(boolean needStop)
	{
		this.needStop = needStop;
	}

	/**
	 * @return размер пачки миграции
	 */
	public Long getBatchSize()
	{
		return batchSize;
	}

	/**
	 * задать размер пачки миграции
	 * @param batchSize размер
	 */
	public void setBatchSize(Long batchSize)
	{
		this.batchSize = batchSize;
	}

	/**
	 * @return список задач на потоки миграции клиентов
	 */
	public List<MigrationThreadTask> getThreadTasks()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return threadTasks;
	}

	/**
	 * задать список задач на потоки миграции клиентов
	 * @param threadTasks задачи
	 */
	public void setThreadTasks(List<MigrationThreadTask> threadTasks)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.threadTasks = threadTasks;
	}
}
