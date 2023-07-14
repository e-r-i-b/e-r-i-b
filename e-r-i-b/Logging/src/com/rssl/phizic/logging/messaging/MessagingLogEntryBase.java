package com.rssl.phizic.logging.messaging;

import com.rssl.phizic.common.types.Application;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * ������� ����� ��� ��������� ������ ����������� ���������
 * User: miklyaev
 * Date: 18.09.14
 * Time: 13:50
 */
public abstract class MessagingLogEntryBase implements Serializable
{
	protected Long     id;
	protected Calendar date;
	protected Long     executionTime;
	protected Application application;
	protected String   messageType;
	protected String   messageRequestId;
	protected String   messageRequest;
	protected String   messageResponseId;
	protected String   messageResponse;
	protected Long     loginId;
	protected System   system;
	protected String   sessionId;
	//������� ������� ��� ���������� ��� ��� ���������� ���������
	protected String surName;
	//�������� ������� ��� ���������� ��� ��� ���������� ���������
	protected String patrName;
	//��� ������� ��� ���������� ��� ��� ���������� ���������
	protected String firstName;
	//�������� ������������ ������� ��� ���������� ��� ��� ���������� ���������
	protected String departmentName;
	//����� ���������� ������� ����� ������� ����� �����������
	protected String personSeries;
	//������ ���������� ������� ����� ������� ����� �����������
	protected String personNumbers;
	//���� �������� �������
	protected Calendar birthDay;
	//����� �����
	protected Long nodeId;
	//���������� � ������� ����.
	protected Long     threadInfo;
	//��
	protected String tb;
	//�� ������������
	protected String departmentCode;
	// ��� �������� � ������ ������� ���� �������
	protected String operationUID;
	//����� ������������
	protected String login;
	//IP �����
	protected String ipAddress;
	//erorCode
	protected String errorCode;
	//���, ���� ������� � IP DataPower � �������: AAA�AAA;BBBB;CCC�CCC; - ���
	//AAA�AAA � BBBB � �������������� ��� � ���� �������, ����������� ��������� � ���;
	//��х��� � IP DataPower, ����� ������� �������� ������.
	protected String addInfo;
	// ���������� ������������� �������� ��� �����������.
	protected String logUID;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public Long getExecutionTime ()
	{
		return executionTime;
	}

	public void setExecutionTime ( Long executionTime )
	{
		this.executionTime = executionTime;
	}

	public Application getApplication()
	{
		return application;
	}

	public void setApplication(Application application)
	{
		this.application = application;
	}

	public String getMessageRequestId()
	{
		return messageRequestId;
	}

	public void setMessageRequestId(String messageRequestId)
	{
		this.messageRequestId = messageRequestId;
	}

	public String getMessageRequest()
	{
		return messageRequest;
	}

	public void setMessageRequest(String messageRequest)
	{
		this.messageRequest = messageRequest;
	}

	public String getMessageResponseId()
	{
		return messageResponseId;
	}

	public void setMessageResponseId(String messageResponseId)
	{
		this.messageResponseId = messageResponseId;
	}

	public String getMessageResponse()
	{
		return messageResponse;
	}

	public void setMessageResponse(String messageResponse)
	{
		this.messageResponse = messageResponse;
	}

	public String getMessageType()
	{
		return messageType;
	}

	public void setMessageType(String messageType)
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

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}


	public String getDate2String()
	{
		return String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS", date);
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
	 * @return ����� �����, � ������� ������������ ������
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * ���������� ����� �����, � ������� ������������ ������
	 * @param nodeId ����� �����, � ������� ������������ ������
	 */
	public void setNodeId(Long  nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * @return ����� ��������
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @param tb ����� ��������
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public Long getThreadInfo()
	{
		return threadInfo;
	}

	public void setThreadInfo(Long threadInfo)
	{
		this.threadInfo = threadInfo;
	}

	public String getDepartmentCode()
	{
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode)
	{
		this.departmentCode = departmentCode;
	}

	public String getOperationUID()
	{
		return operationUID;
	}

	public void setOperationUID(String operationUID)
	{
		this.operationUID = operationUID;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getIpAddress()
	{
		return ipAddress;
	}

	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	/**
	 * @return ���, ���� ������� � IP DataPower, ����� ������� �������� ������
	 */
	public String getAddInfo()
	{
		return addInfo;
	}

	/**
	 * @param addInfo ���, ���� ������� � IP DataPower, ����� ������� �������� ������
	 */
	public void setAddInfo(String addInfo)
	{
		this.addInfo = addInfo;
	}

	public String getLogUID()
	{
		return logUID;
	}

	public void setLogUID(String logUID)
	{
		this.logUID = logUID;
	}

	@Override
	public String toString()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("operationUID", operationUID)
				.append("login", login)
				.append("departmentCode", departmentCode)
				.append("errorCode", errorCode)
				.append("logUID", logUID)
				.append("ipAddress", ipAddress)
				.append("addInfo", addInfo)
				.append("messageType", messageType)
				.append("messageRequestId", messageRequestId)
				.append("messageRequest", messageRequest)
				.append("messageResponseId", messageResponseId)
				.append("messageResponse", messageResponse)
				.append("executionTime", executionTime)
				.append("date", (date != null) ? df.format(date.getTime()) : null)
				.append("application", application)
				.append("loginId", loginId)
				.append("system", system)
				.append("sessionId", sessionId)
				.append("surName", surName)
				.append("patrName", patrName)
				.append("firstName", firstName)
				.append("departmentName", departmentName)
				.append("personSeries", personSeries)
				.append("personNumbers", personNumbers)
				.append("birthDay", (birthDay != null) ? df.format(date.getTime()) : null)
				.append("nodeId", nodeId)
				.append("threadInfo", threadInfo)
				.append("tb", tb)
				.toString();
	}
}
