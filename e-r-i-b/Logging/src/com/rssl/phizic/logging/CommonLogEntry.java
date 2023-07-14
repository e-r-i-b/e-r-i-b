package com.rssl.phizic.logging;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.LogModule;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Erkin
 * @ created 04.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class CommonLogEntry implements Serializable
{
	private CommonLogEntryId id;

	private LogType log;

	/**
	 * Дата и время (записи)
	 */
	private Calendar date;

	/**
	 * Приложение
	 */
	private Application application;

	/**
	 * Идентификатор пользователя (LOGIN_ID)
	 */
	private Long loginId;

	/**
	 * Наименование операции
	 */
	private String operation;

	private String ipAddress;

	private String sessionId;

	private String messageType;

	/**
	 * Модуль логирования системы
	 */
	private LogModule module;

	private String requestType;


	private String requestTypeTranslate;   // перевод типа запроса


	/**
	 * Система (город, CSA, iPas, WAY4, КСШ)
	 */
	private com.rssl.phizic.logging.messaging.System system;

	//пустые ли парамерты в журнале  пользовательских действий
	private boolean isParamsEmpty;

	private String firstName;
	private String patrName;
	private String surName;
	private String departmentName;
	private Long nodeId;

	public CommonLogEntryId getId()
	{
		return id;
	}

	public void setId(CommonLogEntryId id)
	{
		this.id = id;
	}

	public LogType getLog()
	{
		return log;
	}

	public void setLog(LogType log)
	{
		this.log = log;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public Application getApplication()
	{
		return application;
	}

	public void setApplication(Application application)
	{
		this.application = application;
	}

	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	public String getIpAddress()
	{
		return ipAddress;
	}

	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public String getMessageType()
	{
		return messageType;
	}

	public void setMessageType(String messageType)
	{
		this.messageType = messageType;
	}

	public LogModule getModule()
	{
		return module;
	}

	public void setModule(LogModule module)
	{
		this.module = module;
	}

	public String getRequestType()
	{
		return requestType;
	}

	public void setRequestType(String requestType)
	{
		this.requestType = requestType;
	}

	public com.rssl.phizic.logging.messaging.System getSystem()
	{
		return system;
	}

	public void setSystem(System system)
	{
		this.system = system;
	}

	public String getRequestTypeTranslate()
	{
		return requestTypeTranslate;
	}

	public void setRequestTypeTranslate(String requestTypeTranslate)
	{
		this.requestTypeTranslate = requestTypeTranslate;
	}

	public boolean isParamsEmpty()
	{
		return isParamsEmpty;
	}

	public void setParamsEmpty(boolean paramsEmpty)
	{
		isParamsEmpty = paramsEmpty;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getDepartmentName()
	{
		return departmentName;
	}

	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}

	/**
	 * @return идентификатор блока
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * задать идентификатор блока
	 * @param nodeId идентификатор
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}
}
