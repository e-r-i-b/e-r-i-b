package com.rssl.phizic.gate.mail.statistics;

import com.rssl.phizic.gate.multinodes.MultiNodeEntityBase;

import java.util.Calendar;

/**
 * Статистика по сотрудникам
 * @author komarov
 * @ created 22.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class EmployeeStatisticsRecord extends MultiNodeEntityBase implements Comparable<EmployeeStatisticsRecord>
{
	private Long id;
	private Calendar arriveTime;
	private Calendar processingTime;
	private String state;
	private String employeeFIO;
	private String areaName;

	/**
	 * @return идентификатор письма
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор письма
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return дата и время поступления
	 */
	public Calendar getArriveTime()
	{
		return arriveTime;
	}

	/**
	 * @param arriveTime дата и время поступления
	 */
	public void setArriveTime(Calendar arriveTime)
	{
		this.arriveTime = arriveTime;
	}

	/**
	 * @return Дата и время обработки
	 */
	public Calendar getProcessingTime()
	{
		return processingTime;
	}

	/**
	 * @param processingTime Дата и время обработки
	 */
	public void setProcessingTime(Calendar processingTime)
	{
		this.processingTime = processingTime;
	}

	/**
	 * @return Статус письма
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * @param state Статус письма
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return ФИО сотрудника
	 */
	public String getEmployeeFIO()
	{
		return employeeFIO;
	}

	/**
	 * @param employeeFIO ФИО сотрудника
	 */
	public void setEmployeeFIO(String employeeFIO)
	{
		this.employeeFIO = employeeFIO;
	}

	/**
	 * @return Площадка
	 */
	public String getAreaName()
	{
		return areaName;
	}

	/**
	 * @param areaName Площадка
	 */
	public void setAreaName(String areaName)
	{
		this.areaName = areaName;
	}

	public int compareTo(EmployeeStatisticsRecord o)
	{
		return this.getArriveTime().compareTo(o.getArriveTime());
	}
}
