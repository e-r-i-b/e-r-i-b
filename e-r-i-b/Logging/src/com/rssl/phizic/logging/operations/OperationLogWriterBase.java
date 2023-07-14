package com.rssl.phizic.logging.operations;

import com.rssl.auth.csa.back.servises.UserLogonType;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.LoggingAccessor;
import com.rssl.phizic.logging.LoggingHelper;
import com.rssl.phizic.logging.LoggingMode;
import com.rssl.phizic.logging.operations.config.OperationsLogConfig;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 15.03.2006
 * @ $Author: vagin $
 * @ $Revision: 74474 $
 */

public abstract class OperationLogWriterBase implements LogWriter
{
	public static final String SUCCESS = "S";
	public static final String SYSTEM_ERROR = "E";
	public static final String SECURITY = "B";

	private ActiveOperationsLoggingAccessor activeOperationsAccessor = new ActiveOperationsLoggingAccessor();
	private PassiveOperationsLoggingAccessor passiveOperationsAccessor = new PassiveOperationsLoggingAccessor();

	public void writeActiveOperation(LogDataReader reader, Calendar start, Calendar end) throws Exception
	{
		writeOperation(activeOperationsAccessor, reader, start, end);
	}

	public void writePassiveOperation(LogDataReader reader, Calendar start, Calendar end) throws Exception
	{
		writeOperation(passiveOperationsAccessor, reader, start, end);
	}

	private void writeOperation(LoggingAccessor accessor, LogDataReader reader, Calendar start, Calendar end) throws Exception
	{
		LoggingMode loggingMode = ConfigFactory.getConfig(LoggingHelper.class).getLoggingMode(accessor);

		if (LoggingMode.OFF == loggingMode)
			return;

		LogEntryBase logEntry = createLogEntry(reader, start, end, loggingMode);
		// записать entry в DB
		writeEntry(logEntry);
	}

	public void writeSecurityOperation(LogDataReader reader, Calendar start, Calendar end) throws Exception
	{
		//сообщения безопасности пишем всегда.
		LogEntryBase logEntry = createLogEntry(reader, start, end, LoggingMode.NORMAL);
		// Для сообщения безопасности нас не интересует результат выполнения операции
		// ставим тип SECURITY
		logEntry.setType(SECURITY);
		// записать entry в DB
		writeEntry(logEntry);
	}

	protected LogEntryBase createLogEntry(LogDataReader reader, Calendar start, Calendar end, LoggingMode loggingMode) throws Exception
	{
		LogEntryBase logEntry = LogThreadContext.isGuest() ? new GuestLogEntry(reader, start, end) : new LogEntry(reader, start, end);
		// записать параметры
		logEntry.setParameters(readParameters(reader, start, end, loggingMode));
		//logEntry.setParameters(reader.readParameters());
		return logEntry;
	}

	protected String readParameters(LogDataReader reader, Calendar start, Calendar end, LoggingMode loggingMode) throws Exception
	{
		OperationsLogConfig config = ConfigFactory.getConfig(OperationsLogConfig.class);

		OperationsLogConfig.Level level = LoggingMode.EXTENDED == loggingMode ? config.getExtendedLevel() : config.getLevel();
		if (level == OperationsLogConfig.Level.SHORT)
		{
			return "";
		}

		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

		map.put("URL операции", reader.getOperationPath());

		if (level == OperationsLogConfig.Level.DETAILED)
		{
			map.put("Время выполнения операции(мс)", end.getTimeInMillis() - start.getTimeInMillis());
			map.putAll(reader.readParameters());
		}
		StringBuilder builder = new StringBuilder();
		formatMap(builder, map);
		return builder.toString();
	}

	private void formatMap(StringBuilder buffer, Map<String, ?> map)
	{
		for (String key : map.keySet())
		{
			Object value = map.get(key);
			if ((value != null) && value instanceof Map)
			{
				appendDescriptor(buffer, key);
				formatMap(buffer, (Map<String, ?>) value);
			}
			else
			{
				appendParameter(buffer, key, value);
			}
		}
	}

	protected abstract void writeEntry(final LogEntryBase logEntry) throws Exception;

	protected void appendDescriptor(StringBuilder builder, String description)
	{
		if (!description.equals(""))
		{
			builder.append("[");
			builder.append(description);
			builder.append("]<br>");
		}
	}

	protected void appendParameter(StringBuilder builder, String name, Object value)
	{
		if (name != null)
		{
			builder.append(name);
			builder.append(" = ");
		}
		builder.append(getNullSaveStringValue(value));
		builder.append("<br>");
	}

	private String getNullSaveStringValue(Object o)
	{
		if (o == null)
		{
			return "";
		}
		return o.toString();
	}
}