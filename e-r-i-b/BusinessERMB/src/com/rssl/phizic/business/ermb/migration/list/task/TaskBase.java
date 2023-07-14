package com.rssl.phizic.business.ermb.migration.list.task;

import com.rssl.phizic.business.ermb.migration.list.ASMigrator;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.context.ModuleContext;
import com.rssl.phizic.module.ModuleStaticManager;

import java.util.concurrent.CancellationException;

/**
 * ������� ����� ��� ������-��������
 * @author Puzikov
 * @ created 16.12.13
 * @ $Author$
 * @ $Revision$
 */

public abstract class TaskBase implements Task
{
	private static final String EXCEPTION_STATUS_MESSAGE = "�� ����� ���������� '%s' ��������� ������ '%s'";
	private static final String CANCELLATION_STATUS_MESSAGE = "������ ���� �������� �� ����� ���������� '%s'";

	private volatile boolean interrupt;
	private volatile boolean done;

	private String status = "������ �� ����������������";

	protected final boolean isInterrupted()
	{
		return interrupt;
	}

	public final void interrupt()
	{
		interrupt = true;
	}

	public final boolean isDone()
	{
		return done;
	}

	public final void run()
	{
		try
		{
			ASMigrator module = ModuleStaticManager.getInstance().getModule(ASMigrator.class);
			ModuleContext.setModule(module);
			ApplicationInfo.setCurrentApplication(module.getApplication());

			start();
			setStatus("������ ���������");
		}
		catch (Exception e)
		{
			handleException(e);
		}
		finally
		{
			done = true;
			stop();
		}
	}

	protected void handleException(Exception e)
	{
		if (e instanceof CancellationException)
			setStatus(String.format(CANCELLATION_STATUS_MESSAGE,
					getStatus()));
		else
			setStatus(String.format(EXCEPTION_STATUS_MESSAGE,
					getStatus(), e.getMessage()));
	}
	/**
	 * ������ ������ �� ����������.
	 * ����������� ����������.
	 * ������ ��������� ������������ ���� interrupted, ��������� CancellationException
	 */
	protected abstract void start() throws Exception;

	/**
	 * ������� ������. ���������� ������������� ��� ��������, � �.�. ��� ������.
	 */
	protected abstract void stop();

	public final String getStatus()
	{
		return status;
	}

	protected void setStatus(String status)
	{
		this.status = status;
	}
}
