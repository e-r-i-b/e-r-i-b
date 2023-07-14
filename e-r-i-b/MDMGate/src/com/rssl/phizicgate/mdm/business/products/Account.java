package com.rssl.phizicgate.mdm.business.products;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * �����/����
 */

public class Account
{
	private Long id;
	private Long profileId;
	private String number;
	private Calendar startDate;
	private Calendar closingDate;

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
	 * @return �����
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * ������ �����
	 * @param number �����
	 */
	public void setNumber(String number)
	{
		this.number = number;
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
	public Calendar getClosingDate()
	{
		return closingDate;
	}

	/**
	 * ������ ���� ��������� ��������
	 * @param closingDate ����
	 */
	public void setClosingDate(Calendar closingDate)
	{
		this.closingDate = closingDate;
	}
}
