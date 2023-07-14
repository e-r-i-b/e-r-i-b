package com.rssl.phizic.logging.messaging;

import com.rssl.phizic.common.types.Application;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author vagin
 * @ created 19.02.15
 * @ $Author$
 * @ $Revision$
 * Сущность для журнала сообщений в АРМ сотрудника.
 */
public class CommonMessageLogEntry implements Serializable
{
	private Long id;
	private Calendar startDate;
	private System system;
	private String messageType;
	private Application application;
	private String departmentName;
	private String sessionId;
	private String messageTranslate;
	private Long nodeId;
	private String type;
	private String phone;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Long getNodeId()
	{
		return nodeId;
	}

	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	public String getMessageTranslate()
	{
		return messageTranslate;
	}

	public void setMessageTranslate(String messageTranslate)
	{
		this.messageTranslate = messageTranslate;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public String getDepartmentName()
	{
		return departmentName;
	}

	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}

	public Application getApplication()
	{
		return application;
	}

	public void setApplication(Application application)
	{
		this.application = application;
	}

	public String getMessageType()
	{
		return messageType;
	}

	void setMessageType(String messageType)
	{
		this.messageType = messageType;
	}

	public System getSystem()
	{
		return system;
	}

	public void setSystem(System system)
	{
		this.system = system;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}
}
