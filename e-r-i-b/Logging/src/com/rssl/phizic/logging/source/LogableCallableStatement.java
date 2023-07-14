package com.rssl.phizic.logging.source;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.sun.rowset.CachedRowSetImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import javax.sql.rowset.CachedRowSet;

/**
 * User: Moshenko
 * Date: 01.08.2012
 * Обертка для CallableStatement
 * Основная цель, логирование вызовов хранимых процедур.
 * !!!Логируются  только методы execute, executeQuery!!!
 * Также логирование входных параметров определено для  методов:
 * setString, setShort, setByte, setShort, setInt, setLong, setDouble,setTimestamp.
 * А для выходных (registerOutParameter) только индексные его версии..
 */
@SuppressWarnings({"OverlyComplexClass"})
public class LogableCallableStatement implements CallableStatement
{
	protected static final Log log =  PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String OUT = "[OUT]";
	private static final String IN = "[IN]";

	private CallableStatement delegate;
	private String sql;
	private System system;
	private Map<Integer,String> inParam; //индексы и значения входных параметров
	private List<Integer> outParam;     //индексы  выходных параметров
	private Map<Integer,ObjectParser> objectParsers = new HashMap<Integer,ObjectParser>(); //список обработчиков возвращаемых ХП объектов

	public LogableCallableStatement(CallableStatement delegate,String sql, System system)
	{
		this.delegate = delegate;
		this.sql = sql;
		this.inParam = new HashMap<Integer,String>();
		this.outParam = new ArrayList<Integer>();
		this.system = system;
	}

	public Array getArray(int i) throws SQLException
	{
		return delegate.getArray(i);
	}

	public Array getArray(String parameterName) throws SQLException
	{
		return delegate.getArray(parameterName);
	}

	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException
	{
		return delegate.getBigDecimal(parameterIndex);
	}

	@Deprecated
	public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException
	{
		return delegate.getBigDecimal(parameterIndex, scale);
	}

	public BigDecimal getBigDecimal(String parameterName) throws SQLException
	{
		return delegate.getBigDecimal(parameterName);
	}

	public Blob getBlob(int i) throws SQLException
	{
		return delegate.getBlob(i);
	}

	public Blob getBlob(String parameterName) throws SQLException
	{
		return delegate.getBlob(parameterName);
	}

	public boolean getBoolean(int parameterIndex) throws SQLException
	{
		return delegate.getBoolean(parameterIndex);
	}

	public boolean getBoolean(String parameterName) throws SQLException
	{
		return delegate.getBoolean(parameterName);
	}

	public byte getByte(int parameterIndex) throws SQLException
	{
		return delegate.getByte(parameterIndex);
	}

	public byte getByte(String parameterName) throws SQLException
	{
		return delegate.getByte(parameterName);
	}

	public byte[] getBytes(int parameterIndex) throws SQLException
	{
		return delegate.getBytes(parameterIndex);
	}

	public byte[] getBytes(String parameterName) throws SQLException
	{
		return delegate.getBytes(parameterName);
	}

	public Clob getClob(int i) throws SQLException
	{
		return delegate.getClob(i);
	}

	public Clob getClob(String parameterName) throws SQLException
	{
		return delegate.getClob(parameterName);
	}

	public Date getDate(int parameterIndex) throws SQLException
	{
		return delegate.getDate(parameterIndex);
	}

	public Date getDate(int parameterIndex, Calendar cal) throws SQLException
	{
		return delegate.getDate(parameterIndex, cal);
	}

	public Date getDate(String parameterName) throws SQLException
	{
		return delegate.getDate(parameterName);
	}

	public Date getDate(String parameterName, Calendar cal) throws SQLException
	{
		return delegate.getDate(parameterName, cal);
	}

	public double getDouble(int parameterIndex) throws SQLException
	{
		return delegate.getDouble(parameterIndex);
	}

	public double getDouble(String parameterName) throws SQLException
	{
		return delegate.getDouble(parameterName);
	}

	public float getFloat(int parameterIndex) throws SQLException
	{
		return delegate.getFloat(parameterIndex);
	}

	public float getFloat(String parameterName) throws SQLException
	{
		return delegate.getFloat(parameterName);
	}

	public int getInt(int parameterIndex) throws SQLException
	{
		return delegate.getInt(parameterIndex);
	}

	public int getInt(String parameterName) throws SQLException
	{
		return delegate.getInt(parameterName);
	}

	public long getLong(int parameterIndex) throws SQLException
	{
		return delegate.getLong(parameterIndex);
	}

	public long getLong(String parameterName) throws SQLException
	{
		return delegate.getLong(parameterName);
	}

	public Object getObject(int i, Map<String, Class<?>> map) throws SQLException
	{
		return delegate.getObject(i, map);
	}

	public Object getObject(int parameterIndex) throws SQLException
	{
		return delegate.getObject(parameterIndex);
	}

	public Object getObject(String parameterName) throws SQLException
	{
		return delegate.getObject(parameterName);
	}

	public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException
	{
		return delegate.getObject(parameterName, map);
	}

	public Ref getRef(int i) throws SQLException
	{
		return delegate.getRef(i);
	}

	public Ref getRef(String parameterName) throws SQLException
	{
		return delegate.getRef(parameterName);
	}

	public short getShort(int parameterIndex) throws SQLException
	{
		return delegate.getShort(parameterIndex);
	}

	public short getShort(String parameterName) throws SQLException
	{
		return delegate.getShort(parameterName);
	}

	public String getString(int parameterIndex) throws SQLException
	{
		return delegate.getString(parameterIndex);
	}

	public String getString(String parameterName) throws SQLException
	{
		return delegate.getString(parameterName);
	}

	public Time getTime(int parameterIndex) throws SQLException
	{
		return delegate.getTime(parameterIndex);
	}

	public Time getTime(int parameterIndex, Calendar cal) throws SQLException
	{
		return delegate.getTime(parameterIndex, cal);
	}

	public Time getTime(String parameterName) throws SQLException
	{
		return delegate.getTime(parameterName);
	}

	public Time getTime(String parameterName, Calendar cal) throws SQLException
	{
		return delegate.getTime(parameterName, cal);
	}

	public Timestamp getTimestamp(int parameterIndex) throws SQLException
	{
		return delegate.getTimestamp(parameterIndex);
	}

	public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException
	{
		return delegate.getTimestamp(parameterIndex, cal);
	}

	public Timestamp getTimestamp(String parameterName) throws SQLException
	{
		return delegate.getTimestamp(parameterName);
	}

	public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException
	{
		return delegate.getTimestamp(parameterName, cal);
	}

	public URL getURL(int parameterIndex) throws SQLException
	{
		return delegate.getURL(parameterIndex);
	}

	public URL getURL(String parameterName) throws SQLException
	{
		return delegate.getURL(parameterName);
	}

	public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException
	{
		outParam.add(parameterIndex);
		delegate.registerOutParameter(parameterIndex, sqlType);
	}

	public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException
	{
		outParam.add(parameterIndex);
		delegate.registerOutParameter(parameterIndex, sqlType, scale);
	}

	public void registerOutParameter(String parameterName, int sqlType) throws SQLException
	{
		delegate.registerOutParameter(parameterName, sqlType);
	}

	public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException
	{
		delegate.registerOutParameter(parameterName, sqlType, scale);
	}

	public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException
	{
		delegate.registerOutParameter(parameterName, sqlType, typeName);
	}

	public void registerOutParameter(int paramIndex, int sqlType, String typeName) throws SQLException
	{
		outParam.add(paramIndex);
		delegate.registerOutParameter(paramIndex, sqlType, typeName);
	}

	public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException
	{
		delegate.setAsciiStream(parameterName, x, length);
	}

	public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException
	{
		delegate.setBigDecimal(parameterName, x);
	}

	public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException
	{
		delegate.setBinaryStream(parameterName, x, length);
	}

	public void setBoolean(String parameterName, boolean x) throws SQLException
	{
		delegate.setBoolean(parameterName, x);
	}

	public void setByte(String parameterName, byte x) throws SQLException
	{
		delegate.setByte(parameterName, x);
	}

	public void setBytes(String parameterName, byte[] x) throws SQLException
	{
		delegate.setBytes(parameterName, x);
	}

	public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException
	{
		delegate.setCharacterStream(parameterName, reader, length);
	}

	public void setDate(String parameterName, Date x) throws SQLException
	{
		delegate.setDate(parameterName, x);
	}

	public void setDate(String parameterName, Date x, Calendar cal) throws SQLException
	{
		delegate.setDate(parameterName, x, cal);
	}

	public void setDouble(String parameterName, double x) throws SQLException
	{
		delegate.setDouble(parameterName, x);
	}

	public void setFloat(String parameterName, float x) throws SQLException
	{
		delegate.setFloat(parameterName, x);
	}

	public void setInt(String parameterName, int x) throws SQLException
	{
		delegate.setInt(parameterName, x);
	}

	public void setLong(String parameterName, long x) throws SQLException
	{
		delegate.setLong(parameterName, x);
	}

	public void setNull(String parameterName, int sqlType) throws SQLException
	{
		delegate.setNull(parameterName, sqlType);
	}

	public void setNull(String parameterName, int sqlType, String typeName) throws SQLException
	{
		delegate.setNull(parameterName, sqlType, typeName);
	}

	public void setObject(String parameterName, Object x) throws SQLException
	{
		delegate.setObject(parameterName, x);
	}

	public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException
	{
		delegate.setObject(parameterName, x, targetSqlType);
	}

	public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException
	{
		delegate.setObject(parameterName, x, targetSqlType, scale);
	}

	public void setShort(String parameterName, short x) throws SQLException
	{
		delegate.setShort(parameterName, x);
	}

	public void setString(String parameterName, String x) throws SQLException
	{
		delegate.setString(parameterName, x);
	}

	public void setTime(String parameterName, Time x) throws SQLException
	{
		delegate.setTime(parameterName, x);
	}

	public void setTime(String parameterName, Time x, Calendar cal) throws SQLException
	{
		delegate.setTime(parameterName, x, cal);
	}

	public void setTimestamp(String parameterName, Timestamp x) throws SQLException
	{
		delegate.setTimestamp(parameterName, x);
	}

	public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException
	{
		delegate.setTimestamp(parameterName, x, cal);
	}

	public void setURL(String parameterName, URL val) throws SQLException
	{
		delegate.setURL(parameterName, val);
	}

	public boolean wasNull() throws SQLException
	{
		return delegate.wasNull();
	}

	public void addBatch() throws SQLException
	{
		delegate.addBatch();
	}

	public void clearParameters() throws SQLException
	{
		delegate.clearParameters();
	}

	public boolean execute() throws SQLException
	{
		long executionTime = 0;
		//изначально полагаем, что результат будет положительный.
		//Все ХП без возвращаемого значения вида "EXEC ?" считаем успешно выполнеными, если нет исключения
		String errorCode = "0";
		try
		{
			long begin = java.lang.System.currentTimeMillis();
			boolean resutl =  delegate.execute();
			executionTime = java.lang.System.currentTimeMillis() - begin;
			return resutl;
		}
		catch(SQLException e)
		{
			errorCode = "-1";
			throw e;
		}
		finally
		{
			writeToLog(sql,delegate,executionTime,inParam,outParam, errorCode);
		}
	}

	public ResultSet executeQuery() throws SQLException
	{
		long executionTime = 0;
		try
		{
			long begin = java.lang.System.currentTimeMillis();
			ResultSet resutl =  delegate.executeQuery();
			executionTime = java.lang.System.currentTimeMillis() - begin;
			CachedRowSet csr = new CachedRowSetImpl();
			csr.populate(resutl);
			return csr.getOriginal();
		}
		finally
		{
			writeToLog(sql,delegate,executionTime,inParam,outParam, null);
		}
	}

	public int executeUpdate() throws SQLException
	{
		return delegate.executeUpdate();
	}

	public ResultSetMetaData getMetaData() throws SQLException
	{
		return delegate.getMetaData();
	}

	public ParameterMetaData getParameterMetaData() throws SQLException
	{
		return delegate.getParameterMetaData();
	}

	public void setArray(int i, Array x) throws SQLException
	{
		delegate.setArray(i, x);
	}

	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException
	{
		delegate.setAsciiStream(parameterIndex, x, length);
	}

	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException
	{
		delegate.setBigDecimal(parameterIndex, x);
	}

	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException
	{
		delegate.setBinaryStream(parameterIndex, x, length);
	}

	public void setBlob(int i, Blob x) throws SQLException
	{
		delegate.setBlob(i, x);
	}

	public void setBoolean(int parameterIndex, boolean x) throws SQLException
	{
		delegate.setBoolean(parameterIndex, x);
	}

	public void setByte(int parameterIndex, byte x) throws SQLException
	{
		inParam.put(parameterIndex,String.valueOf(x));
		delegate.setByte(parameterIndex, x);
	}

	public void setBytes(int parameterIndex, byte[] x) throws SQLException
	{
		inParam.put(parameterIndex,new String(x));
		delegate.setBytes(parameterIndex, x);
	}

	public void setBytes(int parameterIndex, byte[] x,String textToLog) throws SQLException
	{
		inParam.put(parameterIndex,textToLog);
		delegate.setBytes(parameterIndex, x);
	}

	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException
	{
		delegate.setCharacterStream(parameterIndex, reader, length);
	}

	public void setClob(int i, Clob x) throws SQLException
	{
		delegate.setClob(i, x);
	}

	public void setDate(int parameterIndex, Date x) throws SQLException
	{
		delegate.setDate(parameterIndex, x);
	}

	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException
	{
		delegate.setDate(parameterIndex, x, cal);
	}

	public void setDouble(int parameterIndex, double x) throws SQLException
	{
		inParam.put(parameterIndex,String.valueOf(x));
		delegate.setDouble(parameterIndex, x);
	}

	public void setFloat(int parameterIndex, float x) throws SQLException
	{
		inParam.put(parameterIndex,String.valueOf(x));
		delegate.setFloat(parameterIndex, x);
	}

	public void setInt(int parameterIndex, int x) throws SQLException
	{
		inParam.put(parameterIndex,String.valueOf(x));
		delegate.setInt(parameterIndex, x);
	}

	public void setLong(int parameterIndex, long x) throws SQLException
	{
		inParam.put(parameterIndex,String.valueOf(x));
		delegate.setLong(parameterIndex, x);
	}

	public void setNull(int parameterIndex, int sqlType) throws SQLException
	{
		inParam.put(parameterIndex,"NULL");
		delegate.setNull(parameterIndex, sqlType);
	}

	public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException
	{
		delegate.setNull(paramIndex, sqlType, typeName);
	}

	public void setObject(int parameterIndex, Object x) throws SQLException
	{
		delegate.setObject(parameterIndex, x);
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException
	{
		delegate.setObject(parameterIndex, x, targetSqlType);
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException
	{
		delegate.setObject(parameterIndex, x, targetSqlType, scale);
	}

	public void setRef(int i, Ref x) throws SQLException
	{
		delegate.setRef(i, x);
	}

	public void setShort(int parameterIndex, short x) throws SQLException
	{
		inParam.put(parameterIndex,String.valueOf(x));
		delegate.setShort(parameterIndex, x);
	}

	public void setString(int parameterIndex, String x) throws SQLException
	{
		inParam.put(parameterIndex,x);
		delegate.setString(parameterIndex, x);
	}

	public void setTime(int parameterIndex, Time x) throws SQLException
	{
		delegate.setTime(parameterIndex, x);
	}

	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException
	{
		delegate.setTime(parameterIndex, x, cal);
	}

	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException
	{
		inParam.put(parameterIndex,x.toString());
		delegate.setTimestamp(parameterIndex, x);
	}

	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException
	{
		delegate.setTimestamp(parameterIndex, x, cal);
	}

	@Deprecated public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException
	{
		delegate.setUnicodeStream(parameterIndex, x, length);
	}

	public void setURL(int parameterIndex, URL x) throws SQLException
	{
		delegate.setURL(parameterIndex, x);
	}

	public void addBatch(String sql) throws SQLException
	{
		delegate.addBatch(sql);
	}

	public void cancel() throws SQLException
	{
		delegate.cancel();
	}

	public void clearBatch() throws SQLException
	{
		delegate.clearBatch();
	}

	public void clearWarnings() throws SQLException
	{
		delegate.clearWarnings();
	}

	public void close() throws SQLException
	{
		delegate.close();
	}

	public boolean execute(String sql) throws SQLException
	{
		return delegate.execute(sql);
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

	public int[] executeBatch() throws SQLException
	{
		return delegate.executeBatch();
	}

	public ResultSet executeQuery(String sql) throws SQLException
	{
		// noinspection JDBCResourceOpenedButNotSafelyClosed
		ResultSet result = delegate.executeQuery(sql);
		return populateResultSet(result);
	}

	public int executeUpdate(String sql) throws SQLException
	{
		return delegate.executeUpdate(sql);
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

	public Connection getConnection() throws SQLException
	{
		return delegate.getConnection();
	}

	public int getFetchDirection() throws SQLException
	{
		return delegate.getFetchDirection();
	}

	public int getFetchSize() throws SQLException
	{
		return delegate.getFetchSize();
	}

	public ResultSet getGeneratedKeys() throws SQLException
	{
		// noinspection JDBCResourceOpenedButNotSafelyClosed
		ResultSet result = delegate.getGeneratedKeys();
		return populateResultSet(result);
	}

	public int getMaxFieldSize() throws SQLException
	{
		return delegate.getMaxFieldSize();
	}

	public int getMaxRows() throws SQLException
	{
		return delegate.getMaxRows();
	}

	public boolean getMoreResults() throws SQLException
	{
		return delegate.getMoreResults();
	}

	public boolean getMoreResults(int current) throws SQLException
	{
		return delegate.getMoreResults(current);
	}

	public int getQueryTimeout() throws SQLException
	{
		return delegate.getQueryTimeout();
	}

	public ResultSet getResultSet() throws SQLException
	{
		// noinspection JDBCResourceOpenedButNotSafelyClosed
		ResultSet result = delegate.getResultSet();
		return populateResultSet(result);
	}

	public int getResultSetConcurrency() throws SQLException
	{
		return delegate.getResultSetConcurrency();
	}

	public int getResultSetHoldability() throws SQLException
	{
		return delegate.getResultSetHoldability();
	}

	public int getResultSetType() throws SQLException
	{
		return delegate.getResultSetType();
	}

	public int getUpdateCount() throws SQLException
	{
		return delegate.getUpdateCount();
	}

	public SQLWarning getWarnings() throws SQLException
	{
		return delegate.getWarnings();
	}

	public void setCursorName(String name) throws SQLException
	{
		delegate.setCursorName(name);
	}

	public void setEscapeProcessing(boolean enable) throws SQLException
	{
		delegate.setEscapeProcessing(enable);
	}

	public void setFetchDirection(int direction) throws SQLException
	{
		delegate.setFetchDirection(direction);
	}

	public void setFetchSize(int rows) throws SQLException
	{
		delegate.setFetchSize(rows);
	}

	public void setMaxFieldSize(int max) throws SQLException
	{
		delegate.setMaxFieldSize(max);
	}

	public void setMaxRows(int max) throws SQLException
	{
		delegate.setMaxRows(max);
	}

	public void setQueryTimeout(int seconds) throws SQLException
	{
		delegate.setQueryTimeout(seconds);
	}

	/**
	 * Задать строку
	 * @param parameterIndex индекс
	 * @param text строка
	 * @param textToLog строка для логирования
	 * @throws SQLException
	 */
	public void setString(int parameterIndex, String text, String textToLog) throws SQLException
	{
		inParam.put(parameterIndex,textToLog);
		delegate.setString(parameterIndex, text);
	}

	private void writeToLog(String sqlString,Statement statement,Long executionTime,Map<Integer,String> inParam,List<Integer> outParam, String errorCode)
	{
		try
		{
			if (statement instanceof CallableStatement)
			{
				String resultString = sqlString;
				String value = null;
				CallableStatement st = (CallableStatement) statement;

				for (int i=1;i<=inParam.size()+outParam.size();i++)
				{
					if (outParam.contains(i))
					{
						if (getObjectParser(i) != null)
						{
							ObjectParser parser = getObjectParser(i);
							value = parser.parse(parser.getParam())+OUT;
						}
						else
							value = st.getString(i)+OUT;
					}
					else
						value = inParam.get(Integer.valueOf(i))+IN;

					 resultString =  StringUtils.replace(resultString, "?", value, 1);
				}

				if(resultString.contains("EXEC")) //если в результирующей строке есть код возврата
					errorCode = resultString.substring(resultString.indexOf("EXEC"), resultString.indexOf("[")).replace("EXEC","").trim();
				MessageLogWriter messageLogWriter = MessageLogService.getMessageLogWriter();
				MessagingLogEntry logEntry = MessageLogService.createLogEntry();

				logEntry.setMessageRequest(resultString);
				logEntry.setMessageRequestId(system.toValue());
				logEntry.setMessageType(LogThreadContext.getProcName());

				logEntry.setExecutionTime(executionTime);
				logEntry.setSystem(system);
				logEntry.setErrorCode(errorCode);

				logEntry.setAddInfo(LogThreadContext.getAppServerInfo());
				messageLogWriter.write(logEntry);
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка записи сообщения в журнал", e);
		}
		finally
		{
			LogThreadContext.setProcName(null);
		}
	}

	/**
	 * Добавить обработчик объекта для записи в лог
	 * @param parser - преобразователь данных объекта в строку для логирования
	 * @param index - индекс параметра курсора в statement
	 */
	public void addObjectParser(ObjectParser parser, int index)
	{
		objectParsers.put(Integer.valueOf(index),parser);
	}

	/**
	 * Получения обработчика для заданного индекса возвращаемого объекта
	 * @param index - индекс в statement
	 * @return обработчик для параметра с индексом index
	 */
	public ObjectParser getObjectParser(int index)
	{
		return objectParsers.get(Integer.valueOf(index));
	}

	/**
	 * Преобразователь значений объекта, возвращаемого хранимой процедурой
	 * в строку для записи в лог.
	 * @deprecated не забывайте закрывать курсоры при использовании этого добра. А лучше вообще не используйте данную "фичу", по крайней мере, в текущей реализации.(BUG069682 Не закрываются курсоры в базе ЦСА)
	 */
	@Deprecated
	public interface ObjectParser
	{
		/**
		 * @param object - возвращеное ХП значение для разбора.
		 * @return преобразованное значение для записи в лог
		 * @throws SQLException
		 */
		public String parse(Object object) throws SQLException;

		/**
		 * @return возвращаемый параметр хранимой процедуры для разбора.
		 * @throws SQLException
		 */
		public Object getParam() throws SQLException;
	}

	private ResultSet populateResultSet(ResultSet result) throws SQLException
	{
		if (result != null)
		{
			// JTDS-драйвер требует, чтобы перед получением output-параметров прочитали весь результ-сет.
			// Иначе получим "java.sql.SQLException: Output parameters have not yet been processed. Call getMoreResults()."
			CachedRowSet csr = new CachedRowSetImpl();
			csr.populate(result);
			return csr.getOriginal();
		}
		return result;
	}
}
