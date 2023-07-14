package com.rssl.phizic.logging.operations;

/**
 * @author krenev
 * @ created 26.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class OperationLogFactory
{
	private static volatile LogWriter logWriter;

	private OperationLogFactory()
	{

	}

	public static LogWriter getLogWriter()
	{
		if (logWriter == null)
		{
			synchronized (OperationLogFactory.class)
			{
				if (logWriter == null)
				{
					logWriter = new BackupSupportLogWriter();
				}
			}
		}
		return logWriter;
	}
}
