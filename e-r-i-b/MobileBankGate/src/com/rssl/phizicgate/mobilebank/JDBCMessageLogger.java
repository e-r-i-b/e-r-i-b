package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.annotation.NonThreadSafe;
import com.rssl.phizic.common.types.annotation.Statefull;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLStringWriter;
import com.rssl.phizic.utils.xml.XMLWriter;
import org.dom4j.io.OutputFormat;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import javax.sql.rowset.CachedRowSet;

/**
 * @author Erkin
 * @ created 19.01.2015
 * @ $Author$
 * @ $Revision$
 */

/**
 * Логгер Журнала Сообщений для варианта, когда не подходит логирование LogableCallableStatement
 * LogableCallableStatement не подходит для:
 * + ХП, которая возвращает резальтсет
 */
@Statefull
@NonThreadSafe
class JDBCMessageLogger
{
	private final Log syslog = PhizICLogFactory.getLog(LogModule.Gate);

	private final com.rssl.phizic.logging.messaging.System system;

	private String messageType;

	private String sqlStatement;

	private final Map<String, String> inputParams = new HashMap<String, String>();

	private final Map<String, String> outputParams = new HashMap<String, String>();

	private CachedRowSet resultSet;

	private String resultCode;

	private long startTime;

	JDBCMessageLogger(System system)
	{
		this.system = system;
	}

	///////////////////////////////////////////////////////////////////////////

	void startEntry(String messageType)
	{
		this.messageType = messageType;
		this.inputParams.clear();
		this.outputParams.clear();
		this.resultSet = null;
		this.resultCode = null;
		this.startTime = java.lang.System.currentTimeMillis();
	}

	@MandatoryParameter
	void setSQLStatement(String sqlStatement)
	{
		this.sqlStatement = sqlStatement;
	}

	void addInputParam(String paramName, Object paramValue)
	{
		if (StringHelper.isEmpty(paramName))
			throw new IllegalArgumentException("Не указано имя параметра");

		inputParams.put(paramName, String.valueOf(paramValue));
	}

	void setResultCode(Object resultCode)
	{
		this.resultCode = String.valueOf(resultCode);
	}

	void addOutputParam(String paramName, Object paramValue)
	{
		if (StringHelper.isEmpty(paramName))
			throw new IllegalArgumentException("Не указано имя параметра");

		outputParams.put(paramName, String.valueOf(paramValue));
	}

	void setResultSet(CachedRowSet resultSet)
	{
		this.resultSet = resultSet;
	}

	void finishEntry()
	{
		if (StringHelper.isEmpty(sqlStatement))
			throw new IllegalStateException("Не указано SQL-выражение");

		try
		{
			String requestString = makeRequestString();
			long executionTime = java.lang.System.currentTimeMillis() - startTime;

			MessageLogWriter messageLogWriter = MessageLogService.getMessageLogWriter();
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();

			logEntry.setMessageType(messageType);
			logEntry.setMessageRequest(requestString);

			logEntry.setExecutionTime(executionTime);
			logEntry.setSystem(system);
			logEntry.setErrorCode(resultCode);

			messageLogWriter.write(logEntry);
		}
		catch (Exception e)
		{
			syslog.error("Ошибка записи в Журнал Сообщений", e);
		}
	}

	private String makeRequestString() throws Exception
	{
		XMLWriter xmlWriter = new XMLStringWriter(OutputFormat.createCompactFormat());

		xmlWriter.startElement("JDBCExecutionInfo");
		{
			xmlWriter.writeTextElement("SQL", sqlStatement);

			xmlWriter.writeTextElement("ResultCode", resultCode);

			xmlWriter.startElement("InputParams");
			for (Map.Entry<String, String> entry : inputParams.entrySet())
				xmlWriter.writeTextElement(entry.getKey(), entry.getValue());
			xmlWriter.endElement();

			xmlWriter.startElement("OutputParams");
			for (Map.Entry<String, String> entry : outputParams.entrySet())
				xmlWriter.writeTextElement(entry.getKey(), entry.getValue());
			xmlWriter.endElement();

			if (resultSet != null)
			{
				resultSet.beforeFirst();

				xmlWriter.startElement("ResultSet");
				for (Map<String, String> row : getResultSetRows(resultSet))
				{
					xmlWriter.startElement("Row");
					for (Map.Entry<String, String> col : row.entrySet())
						xmlWriter.writeTextElement(col.getKey(), col.getValue());
					xmlWriter.endElement();
				}
				xmlWriter.endElement();

				resultSet.beforeFirst();
			}
		}
		xmlWriter.endElement();

		return xmlWriter.toString();
	}

	private List<Map<String, String>> getResultSetRows(ResultSet resultSet) throws SQLException
	{
		if (resultSet == null)
			return Collections.emptyList();

		List<Map<String, String>> rows = new LinkedList<Map<String, String>>();
		while (resultSet.next())
		{
			ResultSetMetaData metaData = resultSet.getMetaData();
			Map<String, String> cols = new LinkedHashMap<String, String>(metaData.getColumnCount());
			for (int i=1; i<=metaData.getColumnCount(); i++)
			{
				String name = metaData.getColumnName(i);
				if (StringHelper.isNotEmpty(name))
					cols.put(name, resultSet.getString(i));
				else cols.put(String.valueOf(i), resultSet.getString(i));
			}
			rows.add(cols);
		}
		return rows;
	}
}
