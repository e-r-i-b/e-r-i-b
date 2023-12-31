package com.rssl.phizic.logging.source;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.logging.messaging.System;
import com.sun.rowset.CachedRowSetImpl;
import org.apache.commons.logging.Log;

import java.sql.*;
import javax.sql.rowset.CachedRowSet;

/**
 * @author Gulov
 * @ created 11.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ��� Statement
 * �������� ����, ����������� ���������� sql ���������� ��� ����������.
 * !!!���������� ������ execute(String sql), executeQuery(String sql), executeUpdate(String sql)!!!
 */
public class LogableStatement implements Statement
{
	protected static final Log log =  PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private final Statement delegate;
	private final System system;

	private String sql;

	public LogableStatement(Statement delegate, System system)
	{
		this.delegate = delegate;
		this.system = system;
	}

	public boolean execute(String sql) throws SQLException
	{
		this.sql = sql;
		long executionTime = 0;
		try
		{
			long begin = java.lang.System.currentTimeMillis();
			boolean result = delegate.execute(sql);
			executionTime = java.lang.System.currentTimeMillis() - begin;
			return result;
		}
		finally
		{
			writeToLog(delegate, executionTime);
		}
	}

	public ResultSet executeQuery(String sql) throws SQLException
	{
		this.sql = sql;
		long executionTime = 0;
		try
		{
			long begin = java.lang.System.currentTimeMillis();
			ResultSet result = delegate.executeQuery(sql);
			executionTime = java.lang.System.currentTimeMillis() - begin;
			CachedRowSet csr = new CachedRowSetImpl();
			csr.populate(result);
			return csr.getOriginal();
		}
		finally
		{
			writeToLog(delegate, executionTime);
		}
	}

	public int executeUpdate(String sql) throws SQLException
	{
		this.sql = sql;
		long executionTime = 0;
		try
		{
			long begin = java.lang.System.currentTimeMillis();
			int result = delegate.executeUpdate(sql);
			executionTime = java.lang.System.currentTimeMillis() - begin;
			return result;
		}
		finally
		{
			writeToLog(delegate, executionTime);
		}
	}

	public void close() throws SQLException
	{
		delegate.close();
	}

	public int getMaxFieldSize() throws SQLException
	{
		return delegate.getMaxFieldSize();
	}

	public void setMaxFieldSize(int max) throws SQLException
	{
		delegate.setMaxFieldSize(max);
	}

	public int getMaxRows() throws SQLException
	{
		return delegate.getMaxRows();
	}

	public void setMaxRows(int max) throws SQLException
	{
		delegate.setMaxRows(max);
	}

	public void setEscapeProcessing(boolean enable) throws SQLException
	{
		delegate.setEscapeProcessing(enable);
	}

	public int getQueryTimeout() throws SQLException
	{
		return delegate.getQueryTimeout();
	}

	public void setQueryTimeout(int seconds) throws SQLException
	{
		delegate.setQueryTimeout(seconds);
	}

	public void cancel() throws SQLException
	{
		delegate.cancel();
	}

	public SQLWarning getWarnings() throws SQLException
	{
		return delegate.getWarnings();
	}

	public void clearWarnings() throws SQLException
	{
		delegate.clearWarnings();
	}

	public void setCursorName(String name) throws SQLException
	{
		delegate.setCursorName(name);
	}

	public ResultSet getResultSet() throws SQLException
	{
		return delegate.getResultSet();
	}

	public int getUpdateCount() throws SQLException
	{
		return delegate.getUpdateCount();
	}

	public boolean getMoreResults() throws SQLException
	{
		return delegate.getMoreResults();
	}

	public void setFetchDirection(int direction) throws SQLException
	{
		delegate.setFetchDirection(direction);
	}

	public int getFetchDirection() throws SQLException
	{
		return delegate.getFetchDirection();
	}

	public void setFetchSize(int rows) throws SQLException
	{
		delegate.setFetchSize(rows);
	}

	public int getFetchSize() throws SQLException
	{
		return delegate.getFetchSize();
	}

	public int getResultSetConcurrency() throws SQLException
	{
		return delegate.getResultSetConcurrency();
	}

	public int getResultSetType() throws SQLException
	{
		return delegate.getResultSetType();
	}

	public void addBatch(String sql) throws SQLException
	{
		delegate.addBatch(sql);
	}

	public void clearBatch() throws SQLException
	{
		delegate.clearBatch();
	}

	public int[] executeBatch() throws SQLException
	{
		return delegate.executeBatch();
	}

	public Connection getConnection() throws SQLException
	{
		return delegate.getConnection();
	}

	public boolean getMoreResults(int current) throws SQLException
	{
		return delegate.getMoreResults(current);
	}

	public ResultSet getGeneratedKeys() throws SQLException
	{
		return delegate.getGeneratedKeys();
	}

	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException
	{
		return delegate.executeUpdate(sql, autoGeneratedKeys);
	}

	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException
	{
		return delegate.executeUpdate(sql, columnIndexes);
	}

	public int executeUpdate(String sql, String[] columnNames) throws SQLException
	{
		return delegate.executeUpdate(sql, columnNames);
	}

	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException
	{
		return delegate.execute(sql, autoGeneratedKeys);
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException
	{
		return delegate.execute(sql, columnIndexes);
	}

	public boolean execute(String sql, String[] columnNames) throws SQLException
	{
		return delegate.execute(sql, columnNames);
	}

	public int getResultSetHoldability() throws SQLException
	{
		return delegate.getResultSetHoldability();
	}

	private void writeToLog(Statement statement, Long executionTime)
	{
		if (!(statement instanceof Statement))
			return;
		MessageLogWriter messageLogWriter = MessageLogService.getMessageLogWriter();
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();

		logEntry.setMessageRequest(sql);
		logEntry.setMessageRequestId(system.toValue());
		logEntry.setMessageType(sql.substring(0, sql.indexOf(' ')));

		logEntry.setMessageResponseId(system.toValue());
		logEntry.setMessageResponse(sql);

		logEntry.setExecutionTime(executionTime);
		logEntry.setSystem(system);

		try
		{
			messageLogWriter.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("������ ������ ��������� � ������", e);
		}
	}
}
