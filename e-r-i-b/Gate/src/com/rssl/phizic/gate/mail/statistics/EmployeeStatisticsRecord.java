package com.rssl.phizic.gate.mail.statistics;

import com.rssl.phizic.gate.multinodes.MultiNodeEntityBase;

import java.util.Calendar;

/**
 * ���������� �� �����������
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
	 * @return ������������� ������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ������������� ������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���� � ����� �����������
	 */
	public Calendar getArriveTime()
	{
		return arriveTime;
	}

	/**
	 * @param arriveTime ���� � ����� �����������
	 */
	public void setArriveTime(Calendar arriveTime)
	{
		this.arriveTime = arriveTime;
	}

	/**
	 * @return ���� � ����� ���������
	 */
	public Calendar getProcessingTime()
	{
		return processingTime;
	}

	/**
	 * @param processingTime ���� � ����� ���������
	 */
	public void setProcessingTime(Calendar processingTime)
	{
		this.processingTime = processingTime;
	}

	/**
	 * @return ������ ������
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * @param state ������ ������
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return ��� ����������
	 */
	public String getEmployeeFIO()
	{
		return employeeFIO;
	}

	/**
	 * @param employeeFIO ��� ����������
	 */
	public void setEmployeeFIO(String employeeFIO)
	{
		this.employeeFIO = employeeFIO;
	}

	/**
	 * @return ��������
	 */
	public String getAreaName()
	{
		return areaName;
	}

	/**
	 * @param areaName ��������
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
