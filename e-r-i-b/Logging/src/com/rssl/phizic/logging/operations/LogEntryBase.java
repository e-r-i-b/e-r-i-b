package com.rssl.phizic.logging.operations;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.LoggingHelper;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vagin
 * @ created 17.02.15
 * @ $Author$
 * @ $Revision$
 * Базовый класс записи журнала действий пользователя
 */
public abstract class LogEntryBase implements Serializable
{
	private Long id;
	private Application application;
	private String operationKey;
	private Calendar date;
	private Long executionTime;
	private String description;
	private String key;
	private String parameters;
	private String sessionId;
	private String type;
	private String ipAddress;
	//Фамилия клиента или сотрудника под кем вызывалось сообщение
	private String surName;
	//Отчество клиента или сотрудника под кем вызывалось сообщение
	private String patrName;
	//Имя клиента или сотрудника под кем вызывалось сообщение
	private String firstName;
	//Название департамента клиента или сотрудника под кем вызывалось сообщение
	private String departmentName;
	//серии документов клиента одной строкой через разделитель
	private String personSeries;
	//номера документов клиента одной строкой через разделитель
	private String personNumbers;
	//дата рождения клиента
	private Calendar birthDay;
	//номер блока
	private Long nodeId;
	//Информация о текущей нити.
	private Long     threadInfo;
	//номер ТБ
	private String tb;

	private static final Map<LogDataReader.ResultType, String> typeCodes = new HashMap<LogDataReader.ResultType, String>();
	private static final String SUCCESS = "S";
	private static final String SYSTEM_ERROR = "E";
	private static final String CLIENT_ERROR = "U";

	static
	{
		typeCodes.put(LogDataReader.ResultType.SUCCESS, SUCCESS);
		typeCodes.put(LogDataReader.ResultType.CLIENT_ERROR, CLIENT_ERROR);
		typeCodes.put(LogDataReader.ResultType.SYSTEM_ERROR, SYSTEM_ERROR);
	}

	//заполненнеи общей части сущностей
	protected void fillBasePart(LogDataReader reader, Calendar start, Calendar end)
	{
		setType(typeCodes.get(reader.getResultType()));
		setDate(start);
		Long execTime = end.getTimeInMillis() - start.getTimeInMillis();
		setExecutionTime(execTime);
		setOperationKey(reader.getOperationKey());
		setDescription(reader.getDescription());
		setKey(reader.getKey());
		setIpAddress(LogThreadContext.getIPAddress());
		setSessionId(LogThreadContext.getSessionId());
		setSurName(LogThreadContext.getSurName());
		setPatrName(LogThreadContext.getPatrName());
		setFirstName(LogThreadContext.getFirstName());
		setDepartmentName(LogThreadContext.getDepartmentName());
		setPersonSeries(LogThreadContext.getSeries());
		setPersonNumbers(LogThreadContext.getNumber());
		setTb(LogThreadContext.getDepartmentRegion());
		setBirthDay(LogThreadContext.getBirthday());
		setApplication(LogThreadContext.getApplication());
		setNodeId(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
		setThreadInfo(Thread.currentThread().getId());
		//Если это не действия пользователя, то сохраняется адрес сервера
		if (StringHelper.isEmpty(LogThreadContext.getIPAddress()))
			LoggingHelper.setAppServerInfoToLogThreadContext(null);
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public Long getExecutionTime()
	{
		return executionTime;
	}

	public void setExecutionTime(Long executionTime)
	{
		this.executionTime = executionTime;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public Application getApplication()
	{
		return application;
	}

	public void setApplication(Application application)
	{
		this.application = application;
	}

	public String getOperationKey()
	{
		return operationKey;
	}

	public void setOperationKey(String operationKey)
	{
		this.operationKey = operationKey;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getParameters()
	{
		return parameters;
	}

	public void setParameters(String parameters)
	{
		this.parameters = parameters;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getDepartmentName()
	{
		return departmentName;
	}

	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}

	public String getPersonSeries()
	{
		return personSeries;
	}

	public void setPersonSeries(String personSeries)
	{
		this.personSeries = personSeries;
	}

	public String getPersonNumbers()
	{
		return personNumbers;
	}

	public void setPersonNumbers(String personNumbers)
	{
		this.personNumbers = personNumbers;
	}


	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	/**
	 * @return номер блока, в котором инициирована запись
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * Установить номер блока, в котором инициирована запись
	 * @param nodeId номер блока, в котором инициирована запись
	 */
	public void setNodeId(Long  nodeId)
	{
		this.nodeId = nodeId;
	}

	//Описание операции для ЦСА
	public String getOperationKeyDescription()
	{
		try
		{
			return key != null ? CSAOperations.valueOf(key).getDescription() : key;
		}
		catch (IllegalArgumentException e)
		{
			return key;
		}
	}
	//Описание состояния операции ЦСА
	public String getOperationStateDescription()
	{
		try
		{
			return type != null ? CSAOperationsState.valueOf(type).getDescription() : type;
		}
		catch (IllegalArgumentException e)
		{
			return type;
		}
	}

	/**
	 * @return номер тербанка
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @param tb номер тербанка
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public String getIpAddress()
	{
		return ipAddress;
	}

	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Long getThreadInfo()
	{
		return threadInfo;
	}

	public void setThreadInfo(Long threadInfo)
	{
		this.threadInfo = threadInfo;
	}

	/**
	 * @return полное имя того, под кем вызывалось сообщение
	 */
	public String getFullName()
	{
		StringBuffer buf = new StringBuffer();

		if (getSurName() != null)
		{
			buf.append(getSurName());
		}

		if (getFirstName() != null)
		{
			if(buf.length() > 0) buf.append(" ");
			buf.append(getFirstName());
		}

		if (getPatrName() != null)
		{
			if (buf.length() > 0) buf.append(" ");
			buf.append(getPatrName());
		}

		return buf.toString();
	}
}
