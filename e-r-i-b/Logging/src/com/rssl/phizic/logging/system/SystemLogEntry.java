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
	 * »нформаци€ о текущей нити.
	 */
	private Long     threadInfo;
	private LogModule source;
	//‘амили€ клиента или сотрудника под кем вызывалось сообщение
	private String surName;
	//ќтчество клиента или сотрудника под кем вызывалось сообщение
	private String patrName;
	//»м€ клиента или сотрудника под кем вызывалось сообщение
	private String firstName;
	//Ќазвание департамента клиента или сотрудника под кем вызывалось сообщение
	private String departmentName;
	//серии документов клиента одной строкой через разделитель
	private String personSeries;
	//номера документов клиента одной строкой через разделитель
	private String personNumbers;
	//дата рождени€ клиента
	private Calendar birthDay;
	//логин пользовател€
	private String  login;
	//“Ѕ пользовател€
	private String   departmentCode;
	// уникальный идентификатор действи€ дл€ логировани€.
	private String   logUID;
	//номер блока
	private Long nodeId;

	private String tb;
	private String osb;
	private String vsp;

	//»м€, порт сервера и IP DataPower в формате: AAAЕAAA;BBBB;CCCЕCCC; - где
	//AAAЕAAA и BBBB Ц соответственно им€ и порт сервера, записавшего сообщение в лог;
	//———Е——— Ц IP DataPower, через который работает клиент.
	private String addInfo;

	/**
	 * ctor дл€ hibernate
	 */
	public SystemLogEntry()
	{
	}

	/**
	 * ctor 
	 * @param message сообщение
	 * @param level уровень записи
	 * @param source модуль
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
	 * @param entry запись в журнал
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
	 * @return источник сообшени€ (шлюз, €дро, шедулер)
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
	 * @return номер блока, в котором инициирована запись
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * ”становить номер блока, в котором инициирована запись
	 * @param nodeId номер блока, в котором инициирована запись
	 */
	public void setNodeId(Long  nodeId)
	{
		this.nodeId = nodeId;
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

	/**
	 * @return осб
	 */
	public String getOsb()
	{
		return osb;
	}

	/**
	 * @param osb осб
	 */
	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	/**
	 * @return всп
	 */
	public String getVsp()
	{
		return vsp;
	}

	/**
	 * @param vsp всп
	 */
	public void setVsp(String vsp)
	{
		this.vsp = vsp;
	}

	/**
	 * @return им€, порт сервера и IP DataPower, через который работает клиент
	 */
	public String getAddInfo()
	{
		return addInfo;
	}

	/**
	 * @param addInfo им€, порт сервера и IP DataPower, через который работает клиент
	 */
	public void setAddInfo(String addInfo)
	{
		this.addInfo = addInfo;
	}
}
