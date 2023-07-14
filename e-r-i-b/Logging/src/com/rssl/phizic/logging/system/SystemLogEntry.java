package com.rssl.phizic.logging.system;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LogThreadContext;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Roshka
 * @ created 10.03.2006
 * @ $Author: osminin $
 * @ $Revision: 78140 $
 * @noinspection ReturnOfCollectionOrArrayField
 */
public class SystemLogEntry implements Serializable
{                                     
	private static final String NONE = "none";
	
	private Long     id;
	private Calendar date;
	private Long     loginId;
	private LogLevel level;
	private Application   application;
	private String   message;
	private String   ipAddress;
	private String   sessionId;
	private String   userId;
	/**
	 * ���������� � ������� ����.
	 */
	private Long     threadInfo;
	private LogModule source;
	//������� ������� ��� ���������� ��� ��� ���������� ���������
	private String surName;
	//�������� ������� ��� ���������� ��� ��� ���������� ���������
	private String patrName;
	//��� ������� ��� ���������� ��� ��� ���������� ���������
	private String firstName;
	//�������� ������������ ������� ��� ���������� ��� ��� ���������� ���������
	private String departmentName;
	//����� ���������� ������� ����� ������� ����� �����������
	private String personSeries;
	//������ ���������� ������� ����� ������� ����� �����������
	private String personNumbers;
	//���� �������� �������
	private Calendar birthDay;
	//����� ������������
	private String  login;
	//�� ������������
	private String   departmentCode;
	// ���������� ������������� �������� ��� �����������.
	private String   logUID;
	//����� �����
	private Long nodeId;

	private String tb;
	private String osb;
	private String vsp;

	//���, ���� ������� � IP DataPower � �������: AAA�AAA;BBBB;CCC�CCC; - ���
	//AAA�AAA � BBBB � �������������� ��� � ���� �������, ����������� ��������� � ���;
	//��х��� � IP DataPower, ����� ������� �������� ������.
	private String addInfo;

	/**
	 * ctor ��� hibernate
	 */
	public SystemLogEntry()
	{
	}

	/**
	 * ctor 
	 * @param message ���������
	 * @param level ������� ������
	 * @param source ������
	 */
	public SystemLogEntry(String message, LogLevel level, LogModule source)
	{                         
		GregorianCalendar today = new GregorianCalendar();
		String logIpAddress = LogThreadContext.getIPAddress();
		String logSessionId = LogThreadContext.getSessionId();
		Long logLoginId = LogThreadContext.getLoginId();
		Application logApplication = LogThreadContext.getApplication();
		
		setLevel(level);
		setDate(today);
		setMessage(message);
		setIpAddress(logIpAddress == null ? NONE : logIpAddress);
		setSessionId(logSessionId == null ? NONE : logSessionId);
		setLoginId(logLoginId == null ? -1L : logLoginId);
		setSurName(LogThreadContext.getSurName());
		setPatrName(LogThreadContext.getPatrName());
		setFirstName(LogThreadContext.getFirstName());
		setDepartmentName(LogThreadContext.getDepartmentName());
		setPersonSeries(LogThreadContext.getSeries());
		setPersonNumbers(LogThreadContext.getNumber());
		setTb(LogThreadContext.getDepartmentRegion());
		setOsb(LogThreadContext.getDepartmentOSB());
		setVsp(LogThreadContext.getDepartmentVSP());
		setBirthDay(LogThreadContext.getBirthday());
		setApplication(logApplication == null ? Application.Other : logApplication);
		setThreadInfo(Thread.currentThread().getId());
		setSource(source);
		setDepartmentCode(LogThreadContext.getDepartmentCode());
		setLogin(LogThreadContext.getLogin());
		setUserId(LogThreadContext.getUserId());
		setLogUID(LogThreadContext.getLogUID());
		setNodeId(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
		setAddInfo(LogThreadContext.getAppServerInfo());
	}

	/**
	 * ctor
	 * @param entry ������ � ������
	 */
	public SystemLogEntry(SystemLogEntry entry)
	{
		setLevel(entry.getLevel());
		setDate(entry.getDate());
		setMessage(entry.getMessage());
		setIpAddress(entry.getIpAddress());
		setSessionId(entry.getSessionId());
		setLoginId(entry.getLoginId());
		setSurName(entry.getSurName());
		setPatrName(entry.getPatrName());
		setFirstName(entry.getFirstName());
		setDepartmentName(entry.getDepartmentName());
		setPersonSeries(entry.getPersonSeries());
		setPersonNumbers(entry.getPersonNumbers());
		setTb(entry.getTb());
		setOsb(entry.getOsb());
		setVsp(entry.getVsp());
		setBirthDay(entry.getBirthDay());
		setApplication(entry.getApplication());
		setThreadInfo(entry.getThreadInfo());
		setSource(entry.getSource());
		setDepartmentCode(entry.getDepartmentCode());
		setLogin(entry.getLogin());
		setUserId(entry.getUserId());
		setLogUID(entry.getLogUID());
		setNodeId(entry.getNodeId());
		setAddInfo(entry.getAddInfo());
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getDepartmentCode()
	{
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode)
	{
		this.departmentCode = departmentCode;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
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

	public LogLevel getLevel()
	{
		return level;
	}

	public void setLevel(LogLevel level)
	{
		this.level = level;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public boolean getException()
	{
		return (message.indexOf("Exception") != -1);
	}

	public void setSource(LogModule source)
	{
		this.source = source;
	}

	/**
	 * @return �������� ��������� (����, ����, �������)
	 */
	public LogModule getSource()
	{
		return source;
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

	public Long getThreadInfo()
	{
		return threadInfo;
	}

	public void setThreadInfo(Long threadInfo)
	{
		this.threadInfo = threadInfo;
	}

	public String getLogUID()
	{
		return logUID;
	}

	public void setLogUID(String logUID)
	{
		this.logUID = logUID;
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

	/**
	 * @return ���
	 */
	public String getOsb()
	{
		return osb;
	}

	/**
	 * @param osb ���
	 */
	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	/**
	 * @return ���
	 */
	public String getVsp()
	{
		return vsp;
	}

	/**
	 * @param vsp ���
	 */
	public void setVsp(String vsp)
	{
		this.vsp = vsp;
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
}
