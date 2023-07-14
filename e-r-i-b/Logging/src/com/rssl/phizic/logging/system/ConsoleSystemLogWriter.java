package com.rssl.phizic.logging.system;

/**
 * @author eMakarov
 * @ created 18.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class ConsoleSystemLogWriter extends SystemLogWriterBase
{
	protected void doWrite(SystemLogEntry logEntry)
	{
		StringBuilder output = new StringBuilder(80);

		output.append("\nvvvvv vvvvv vvvvv vvvvv vvvvv\n");
		output.append("SystemLogEntry:\n");
		createRecord(logEntry, output);
		output.append("^^^^^ ^^^^^ ^^^^^ ^^^^^ ^^^^^\n");
		System.out.println(output);
	}

	private void createRecord(SystemLogEntry logEntry, StringBuilder output)
	{
		output.append("Application: ");
		output.append(logEntry.getApplication());
		output.append("\n");

		output.append("ThreadId: ");
		output.append(logEntry.getThreadInfo());
		output.append("\n");

		output.append("Date: ");
		output.append(String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS", logEntry.getDate()));
		output.append("\n");

		output.append("ipAddress: ");
		output.append(logEntry.getIpAddress());
		output.append("\n");

		output.append("Level: ");
		output.append(logEntry.getLevel().toValue());
		output.append("\n");

		output.append("loginId: ");
		output.append(logEntry.getLoginId());
		output.append("\n");

		output.append("Message: ");
		output.append(logEntry.getMessage());
		output.append("\n");
	}

	public SystemLogEntry createEntry(LogModule source, String message, LogLevel level)
	{
		return null;
	}
}
