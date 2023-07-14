package com.rssl.phizic.business.monitoring.usercounterreport;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 17.03.2011
 * @ $Author$
 * @ $Revision$
 */
//бин для хранения текущего количества пользователей
public class UserCounterReport
{
	private Long id;
	private String applicationName; // псевдоним сервера, для которого считаем кол-во
	private String module; // модуль приложения для которого считаем кол-во
	private Long TB;
	private Long count;
	private Calendar updateTime;// время обновления данных в БД. Т.е. в какое время было данное кол-во пользователей.

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getApplicationName()
	{
		return applicationName;
	}

	public void setApplicationName(String applicationName)
	{
		this.applicationName = applicationName;
	}

	public String getModule()
	{
		return module;
	}

	public void setModule(String module)
	{
		this.module = module;
	}

	public Long getTB()
	{
		return TB;
	}

	public void setTB(Long TB)
	{
		this.TB = TB;
	}

	public Long getCount()
	{
		return count;
	}

	public void setCount(Long count)
	{
		this.count = count;
	}

	public Calendar getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Calendar updateTime)
	{
		this.updateTime = updateTime;
	}
}
