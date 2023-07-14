package com.rssl.phizicgate.mdm.business.products;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������
 */

public class Service
{
	private Long id;
	private Long profileId;
	private String type;
	private Calendar startDate;
	private Calendar endDate;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������������� ������� (��������� ��������)
	 */
	public Long getProfileId()
	{
		return profileId;
	}

	/**
	 * ������ ������������� ������� (��������� ��������)
	 * @param profileId �������������
	 */
	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	/**
	 * @return ���
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * ������ ���
	 * @param type ���
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return ���� ������ ��������
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * ������ ���� ������ ��������
	 * @param startDate ����
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return ���� ��������� ��������
	 */
	public Calendar getEndDate()
	{
		return endDate;
	}

	/**
	 * ������ ���� ��������� ��������
	 * @param endDate ����
	 */
	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}
}
