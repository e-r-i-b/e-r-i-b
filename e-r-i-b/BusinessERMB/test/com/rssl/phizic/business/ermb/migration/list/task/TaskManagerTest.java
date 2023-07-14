package com.rssl.phizic.business.ermb.migration.list.task;

import junit.framework.TestCase;

import java.util.EnumSet;

/**
 * @author Puzikov
 * @ created 18.12.13
 * @ $Author$
 * @ $Revision$
 */

public class TaskManagerTest extends TestCase
{
	private static final String doneStatus = "Задача выполнена";
	private static final String notInitStatus = "Задача не инициализирована";

	public void testIfEnded() throws Exception
	{
		Task task = new MockLoadClientsTask();
		TaskManager.getInstance().start(task);

		Thread.sleep(500);
		String status = task.getStatus();
		assertEquals(status, doneStatus);
		assertEquals(task.isDone(), true);
	}

	public void testIfLocked() throws Exception
	{
		Task sleepingTask = new MockLoadClientsTask()
		{
			@Override protected void start()
			{
				try
				{
					Thread.sleep(10000);
				}
				catch (InterruptedException e)
				{
					fail();
				}
			}
		};
		TaskManager.getInstance().start(sleepingTask);
		assertEquals(sleepingTask.isDone(), false);

		Task sameTask = new MockLoadClientsTask();
		try
		{
			TaskManager.getInstance().start(sameTask);
			fail();
		}
		catch (IllegalStateException e)
		{
			assertEquals(e.getMessage(), String.format("Задача данного типа уже выполняется. Ее статус: '%s'", notInitStatus));
		}

		Task otherTask = new MockLoadClientsTask()
		{
			@Override public TaskType getType()
			{
				return TaskType.SMS_BROADCAST;
			}
		};
		TaskManager.getInstance().start(otherTask);
	}

	public void testIfCanceled() throws Exception
	{
		Task nonExistingTask = new MockLoadClientsTask()
		{
			@Override public TaskType getType()
			{
				return TaskType.ROLLBACK_MIGRATION;
			}

			@Override protected void start()
			{
				try
				{
					assertEquals(isInterrupted(), false);
					Thread.sleep(1000);
					assertEquals(isInterrupted(), true);
				}
				catch (InterruptedException e)
				{
					fail();
				}
			}
		};

		try
		{
			TaskManager.getInstance().cancel(nonExistingTask.getType());
			fail();
		}
		catch (IllegalStateException e)
		{
			assertEquals(e.getMessage(), "Отмена невозможна, задача не запущена");
		}

		TaskManager.getInstance().start(nonExistingTask);
		Thread.sleep(500);
		TaskManager.getInstance().cancel(nonExistingTask.getType());
	}

	public void testIfWaitingForInit() throws Exception
	{
		Task longInitTask = new MockLoadClientsTask()
		{
			@Override public TaskType getType()
			{
				return TaskType.START_MIGRATION;
			}

			public void init()
			{
				try
				{
					setStatus("notInit");
					Thread.sleep(500);
					setStatus("init");
				}
				catch (InterruptedException e)
				{
					fail();
				}
			}
		};

		TaskManager.getInstance().start(longInitTask);
		assertEquals(longInitTask.getStatus(), notInitStatus);
	}

	public void testIfWaitingForStop()
	{
		Task longStoppingTask = new MockLoadClientsTask()
		{
			@Override public TaskType getType()
			{
				return TaskType.SMS_BROADCAST;
			}

			@Override protected void start()
			{
				try
				{
					while (!isInterrupted())
						Thread.sleep(100);

					Thread.sleep(500);
					setStatus("stopped");
				}
				catch (InterruptedException e)
				{
					fail();
				}
			}
		};
		TaskManager.getInstance().start(longStoppingTask);
		TaskManager.getInstance().cancel(longStoppingTask.getType());
		assertEquals(longStoppingTask.getStatus(), doneStatus);
	}

	private class MockLoadClientsTask extends TaskBase
	{

		@Override protected void start()
		{
		}

		@Override protected void stop()
		{
		}

		public TaskType getType()
		{
			return TaskType.LOAD_CLIENTS;
		}

		public EnumSet<TaskType> getIllegalTasks()
		{
			return EnumSet.noneOf(TaskType.class);
		}
	}
}
