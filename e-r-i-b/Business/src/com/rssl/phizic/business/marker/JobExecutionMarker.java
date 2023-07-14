package com.rssl.phizic.business.marker;

import java.util.Date;

/**
 * @author mihaylov
 * @ created 25.02.2013
 * @ $Author$
 * @ $Revision$
 * ����� � ������������� ���������� �����
 */
public class JobExecutionMarker
{
	private Long id;        //������������� �����
	private String jobName; //��� �����
	private Date actualDate;//���� ������������ �������

	/**��������� �����������*/
	public JobExecutionMarker()
	{
	}

	/**
	 * �����������
	 * @param jobName - ��� �����
	 * @param actualDate - ���� ������������ �������
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
