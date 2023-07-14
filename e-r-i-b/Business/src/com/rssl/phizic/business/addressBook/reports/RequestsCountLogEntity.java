package com.rssl.phizic.business.addressBook.reports;

import java.util.Date;

/**
 * @author akrenev
 * @ created 19.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * �������� ���� ������������� �������� �����
 */

public class RequestsCountLogEntity extends ReportEntityBase
{
	private Long id;
	private Date synchronizationDate;
	private Long count;

	/**
	 * @return ������������� ������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ ������������� ������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	/**
	 * @return ���� �������������
	 */
	public Date getSynchronizationDate()
	{
		return synchronizationDate;
	}

	/**
	 * ������ ���� �������������
	 * @param synchronizationDate ����
	 */
	public void setSynchronizationDate(Date synchronizationDate)
	{
		this.synchronizationDate = synchronizationDate;
	}

	/**
	 * @return ���������� ������������������ ���������
	 */
	public Long getCount()
	{
		return count;
	}

	/**
	 * ���������� ���������� ������������������ ���������
	 * @param count ���������� ������������������ ���������
	 */
	public void setCount(Long count)
	{
		this.count = count;
	}
}
