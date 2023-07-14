package com.rssl.phizic.business.marker;

import java.util.Date;

/**
 * @author mihaylov
 * @ created 25.02.2013
 * @ $Author$
 * @ $Revision$
 * Метка о необходимости выполнения джоба
 */
public class JobExecutionMarker
{
	private Long id;        //идентификатор метки
	private String jobName; //имя джоба
	private Date actualDate;//дата актуальности маркера

	/**Дефолтный конструктор*/
	public JobExecutionMarker()
	{
	}

	/**
	 * Конструктор
	 * @param jobName - имя джоба
	 * @param actualDate - дата актуальности маркера
	 */
	public JobExecutionMarker(String jobName, Date actualDate)
	{
		this.jobName = jobName;
		this.actualDate = actualDate;
	}	

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getJobName()
	{
		return jobName;
	}

	public void setJobName(String jobName)
	{
		this.jobName = jobName;
	}

	public Date getActualDate()
	{
		return actualDate;
	}

	public void setActualDate(Date actualDate)
	{
		this.actualDate = actualDate;
	}
}
