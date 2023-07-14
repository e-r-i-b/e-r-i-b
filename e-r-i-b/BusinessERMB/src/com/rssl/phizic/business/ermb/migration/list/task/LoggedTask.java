package com.rssl.phizic.business.ermb.migration.list.task;

import java.util.concurrent.CancellationException;

/**
 * ��������������� ������
 * @author Puzikov
 * @ created 15.01.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class LoggedTask extends TaskBase
{
	private static final String CANCELED = "������ ���� ��������";

	private TaskLog protocol;

	protected LoggedTask()
	{
		try
		{
			protocol = new TaskLog(getType());
		}
		catch (Exception e)
		{
			throw new RuntimeException("���������� ������������������� ���-����", e);
		}
	}

	@Override
	protected void handleException(Exception e)
	{
		if (e instanceof CancellationException)
			protocol.add(CANCELED);
		else
			protocol.add(e);

		super.handleException(e);
	}

	protected void toLog(String line)
	{
		protocol.add(line);
	}

	protected void toLog(Exception e)
	{
		protocol.add(e);
	}

	protected void flushLog()
	{
		protocol.flush();
	}

	@Override
	protected void setStatus(String status)
	{
		super.setStatus(status + " ���� ������: " + protocol.getLogFileName());
	}

	@Override
	protected void stop()
	{
		flushLog();
	}
}
