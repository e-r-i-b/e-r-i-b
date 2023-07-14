package com.rssl.phizic.business.ermb.migration.list.task;

import com.rssl.phizic.common.types.annotation.Singleton;

import java.util.EnumMap;

/**
 * Менеджер задач-одиночек
 * @author Puzikov
 * @ created 16.12.13
 * @ $Author$
 * @ $Revision$
 */

@Singleton
public class TaskManager
{
	private static final String TASK_ALREADY_RUNNING_MESSAGE = "Задача данного типа уже выполняется. Ее статус: '%s'";
	private static final String ILLEGAL_TASK_RUNNING_MESSAGE = "Выполнение задачи невозможно, выполняется конкурирующая задача %s. Ее статус '%s'";
	private static final String ILLEGAL_CANCELLATION_MESSAGE = "Отмена невозможна, задача не запущена";
	private static final String TIMEOUT_CANCEL_MESSAGE       = "Ошибка отмены задачи: таймаут";

	private static final long CANCELLATION_TIMEOUT = 2 * 60 * 1000;
	private static final int CANCELLATION_PERIOD = 100;

	private EnumMap<TaskType, Task> tasks = new EnumMap<TaskType, Task>(TaskType.class);

	private static TaskManager instance = new TaskManager();

	private TaskManager()
	{
	}

	/**
	 * @return инстанс менеджера задач
	 */
	public static TaskManager getInstance()
	{
		return instance;
	}

	/**
	 * Запустить задачу на исполнение.
	 * Если задача уже запущена, или запущена конкурирующая задача - IllegalStateException
	 * @param task задача-одиночка
	 */
	public synchronized void start(Task task)
	{
		if (tasks.containsKey(task.getType()))
		{
			Task previousTask = tasks.get(task.getType());
			if (previousTask.isDone())
				tasks.remove(task.getType());
			else
				throw new IllegalStateException(String.format(TASK_ALREADY_RUNNING_MESSAGE, previousTask.getStatus()));
		}
		for (TaskType illegalTaskType : task.getIllegalTasks())
			if (tasks.containsKey(illegalTaskType))
			{
				Task illegalTask = tasks.get(illegalTaskType);
				if (illegalTask.isDone())
					continue;
				throw new IllegalStateException(String.format(ILLEGAL_TASK_RUNNING_MESSAGE,
						illegalTask.getType().getDescription(), illegalTask.getStatus()));
			}

		tasks.put(task.getType(), task);
		new Thread(task).start();
	}

	/**
	 * Отменить задачу данного типа. Блокируется пока задача не закончит выполнение.
	 * Если задача не запущена - IllegalStateException
	 * @param taskType тип задачи
	 */
	public synchronized void cancel(TaskType taskType)
	{
		if (!tasks.containsKey(taskType))
			throw new IllegalStateException(ILLEGAL_CANCELLATION_MESSAGE);

		try
		{
			Task task = tasks.get(taskType);
			task.interrupt();
			long tryNumber = CANCELLATION_TIMEOUT / CANCELLATION_PERIOD;
			for (int i = 0; i < tryNumber; i++)
			{
				if (task.isDone())
				{
					tasks.remove(taskType);
					return;
				}
				Thread.sleep(CANCELLATION_PERIOD);
			}

			throw new IllegalStateException(TIMEOUT_CANCEL_MESSAGE);
		}
		catch (InterruptedException e)
		{
			throw new IllegalArgumentException(e);
		}
	}
}
