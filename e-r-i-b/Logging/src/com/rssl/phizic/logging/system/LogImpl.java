package com.rssl.phizic.logging.system;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LoggingHelper;
import com.rssl.phizic.logging.LoggingMode;
import com.rssl.phizic.logging.writers.SystemLogWriter;

/**
 * @author eMakarov
 * @ created 16.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class LogImpl implements Log
{
	private org.apache.commons.logging.Log apacheLogger;// = org.apache.commons.logging.LogFactory.getLog("innerLogger");

	/** "Trace" level logging. */
    public static final int LOG_LEVEL_TRACE = 0;
    /** "Debug" level logging. */
    public static final int LOG_LEVEL_DEBUG = 1;
    /** "Info"  level logging. */
    public static final int LOG_LEVEL_INFO  = 2;
    /** "Warn"  level logging. */
    public static final int LOG_LEVEL_WARN  = 3;
    /** "Error" levellogging. */
    public static final int LOG_LEVEL_ERROR = 4;
    /** "Fatal" level logging. */
    public static final int LOG_LEVEL_FATAL = 5;

	public static final LogLevel[] LOG_LEVELS =
			new LogLevel[]{LogLevel.T, LogLevel.D, LogLevel.I, LogLevel.W, LogLevel.E, LogLevel.F};

    /** Enable no logging levels */
    public static final int LOG_LEVEL_OFF   = 6;// LOG_LEVEL_FATAL + 1

	/** Модуль, для которого создан логгер */
    protected LogModule logName = null;
	private static volatile SystemLogWriter logWriter;

	private SystemLoggingAccessor accessor = new SystemLoggingAccessor();

	public LogImpl(LogModule module)
	{
		logName = module;
		apacheLogger = org.apache.commons.logging.LogFactory.getLog(logName.toValue());
	}

	/**
	 * <p> Log a message with debug log level. </p>
	 *
	 * @param message log this message
	 */
	public void debug(Object message)
	{
		debug(message, null);
	}

	/**
	 * <p> Log an error with debug log level. </p>
	 *
	 * @param message log this message
	 * @param t log this cause
	 */
	public void debug(Object message, Throwable t)
	{
		if (isLevelEnabled(LOG_LEVEL_DEBUG))
			log(LOG_LEVEL_DEBUG, message, t);
		apacheLogger.debug(message, t);
	}

	/**
	 * <p> Log a message with error log level. </p>
	 *
	 * @param message log this message
	 */
	public void error(Object message)
	{
		if (message instanceof Throwable)
		{
			error(((Throwable)message).getMessage(), (Throwable)message);
		}
		else
			error(message, null);
	}

	/**
	 * <p> Log an error with error log level. </p>
	 *
	 * @param message log this message
	 * @param t log this cause                                                                                      
	 */
	public void error(Object message, Throwable t)
	{
		if (isLevelEnabled(LOG_LEVEL_ERROR))
			log(LOG_LEVEL_ERROR, message, t);
		apacheLogger.error(message, t);
	}

	/**
	 * <p> Log a message with fatal log level. </p>
	 *
	 * @param message log this message
	 */
	public void fatal(Object message)
	{
		fatal(message, null);
	}

	/**
	 * <p> Log an error with fatal log level. </p>
	 *
	 * @param message log this message
	 * @param t log this cause
	 */
	public void fatal(Object message, Throwable t)
	{
		if (isLevelEnabled(LOG_LEVEL_FATAL))
			log(LOG_LEVEL_FATAL, message, t);
		apacheLogger.fatal(message, t);
	}

	/**
	 * <p> Log a message with info log level. </p>
	 *
	 * @param message log this message
	 */
	public void info(Object message)
	{
		info(message, null);
	}

	/**
	 * <p> Log an error with info log level. </p>
	 *
	 * @param message log this message
	 * @param t log this cause
	 */
	public void info(Object message, Throwable t)
	{
		if (isLevelEnabled(LOG_LEVEL_INFO))
			log(LOG_LEVEL_INFO, message, t);
		apacheLogger.info(message, t);
	}

	/**
	 * <p> Is debug logging currently enabled? </p>
	 *
	 * <p> Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than debug. </p>
	 *
	 * @return true if debug is enabled in the underlying logger.
	 */
	public boolean isDebugEnabled()
	{
		return isLevelEnabled(LOG_LEVEL_DEBUG);
	}

	/**
	 * <p> Is error logging currently enabled? </p>
	 *
	 * <p> Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than error. </p>
	 *
	 * @return true if error is enabled in the underlying logger.
	 */
	public boolean isErrorEnabled()
	{
		return isLevelEnabled(LOG_LEVEL_ERROR);
	}

	/**
	 * <p> Is fatal logging currently enabled? </p>
	 *
	 * <p> Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than fatal. </p>
	 *
	 * @return true if fatal is enabled in the underlying logger.
	 */
	public boolean isFatalEnabled()
	{
		return isLevelEnabled(LOG_LEVEL_FATAL);
	}

	/**
	 * <p> Is info logging currently enabled? </p>
	 *
	 * <p> Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than info. </p>
	 *
	 * @return true if info is enabled in the underlying logger.
	 */
	public boolean isInfoEnabled()
	{
		return isLevelEnabled(LOG_LEVEL_INFO);
	}

	/**
	 * <p> Is trace logging currently enabled? </p>
	 *
	 * <p> Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than trace. </p>
	 *
	 * @return true if trace is enabled in the underlying logger.
	 */
	public boolean isTraceEnabled()
	{
		return isLevelEnabled(LOG_LEVEL_TRACE);
	}

	/**
	 * <p> Is warn logging currently enabled? </p>
	 *
	 * <p> Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than warn. </p>
	 *
	 * @return true if warn is enabled in the underlying logger.
	 */
	public boolean isWarnEnabled()
	{
		return isLevelEnabled(LOG_LEVEL_WARN);
	}

	/**
	 * <p> Log a message with trace log level. </p>
	 *
	 * @param message log this message
	 */
	public void trace(Object message)
	{
		trace(message, null);
	}

	/**
	 * <p> Log an error with trace log level. </p>
	 *
	 * @param message log this message
	 * @param t log this cause
	 */
	public void trace(Object message, Throwable t)
	{
		if (isLevelEnabled(LOG_LEVEL_TRACE))
			log(LOG_LEVEL_TRACE, message, t);
		apacheLogger.trace(message, t);
	}

	/**
	 * <p> Log a message with warn log level. </p>
	 *
	 * @param message log this message
	 */
	public void warn(Object message)
	{
		warn(message, null);
	}

	/**
	 * <p> Log an error with warn log level. </p>
	 *
	 * @param message log this message
	 * @param t log this cause
	 */
	public void warn(Object message, Throwable t)
	{
		if (isLevelEnabled(LOG_LEVEL_WARN))
			log(LOG_LEVEL_WARN, message, t);
		apacheLogger.warn(message, t);
	}

	/**
     * @param logLevel - проверяемое значение
	 * @return true, если уровень включён
     */
    protected boolean isLevelEnabled(int logLevel)
	{
		try
		{
			return LoggingMode.OFF != ConfigFactory.getConfig(LoggingHelper.class).getLoggingMode(accessor, logLevel, logName);
		}
		catch (RuntimeException e)
		{
			apacheLogger.fatal("Ошибка записи в журнал", e);
			return false;
		}
    }

	/**
     * <p> Do the actual logging.
     * This method assembles the message
     * and then calls <code>write()</code> to cause it to be written.</p>
     *
     * @param type One of the LOG_LEVEL_levelName constants defining the log level
     * @param message The message itself (typically a String)
     * @param t The exception whose stack trace should be logged
     */
    protected void log(int type, Object message, Throwable t)
	{
		LogLevel logLevel = LOG_LEVELS[type];
		StringBuffer buf = getLogBuffer(logLevel, message, t);

		try
		{
			getWriter().write(logName, buf.toString(), logLevel);
		}
		catch (Exception e)
		{
			apacheLogger.error("Ошибка записи в журнал", e);
		}
	}

	private Object createLogEntryBase(int type, Object message, Throwable t)
	{
		LogLevel logLevel = LOG_LEVELS[type];
		StringBuffer buf = getLogBuffer(logLevel, message, t);

		try
		{
			return getWriter().createEntry(logName, buf.toString(), logLevel);
		}
		catch (Exception e)
		{
			apacheLogger.error("Ошибка записи в журнал", e);
			return null;
		}
	}

	private StringBuffer getLogBuffer(LogLevel logLevel, Object message, Throwable t)
	{
        // Use a string buffer for better performance
        StringBuffer buf = new StringBuffer();

        // Append a readable representation of the log level
		buf.append("["+ logLevel.toValue() +"] " + logName + " - ");
        buf.append(String.valueOf(message));

        if (t != null)
        {
            buf.append(" <");
            buf.append(t.toString());
            buf.append(">");

            java.io.StringWriter sw = new java.io.StringWriter(1024);
            java.io.PrintWriter pw = new java.io.PrintWriter(sw);
	        try
	        {
		        pw = new java.io.PrintWriter(sw);
		        t.printStackTrace(pw);
	        }
            finally
	        {
                pw.close();
	        }
            buf.append(sw.toString());
        }

		return buf;
	}

	private static SystemLogWriter getWriter()
	{
		if (logWriter == null)
		{
			synchronized (LogImpl.class)
			{
				if (logWriter == null)
				{
					logWriter = new BackupSupportSystemLogWriter();
				}
			}
		}
		return logWriter;
	}

	public LogLevel resolveLogLevelName(String firstchar)
	{
		return LogLevel.fromValue(firstchar);
	}

	public Object createEntry(Object message)
	{
		if (message instanceof Throwable)
		{
			return createEntry(((Throwable) message).getMessage(), (Throwable) message);
		}

		return createEntry(message, null);
	}

	public Object createEntry(Object message, Throwable t)
	{
		if (isLevelEnabled(LOG_LEVEL_ERROR))
		{
			return createLogEntryBase(LOG_LEVEL_ERROR, message, t);
		}

		return null;
	}
}
