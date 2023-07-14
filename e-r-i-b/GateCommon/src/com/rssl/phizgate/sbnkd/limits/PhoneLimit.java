package com.rssl.phizgate.sbnkd.limits;

import java.util.Calendar;

/**
 * ����� �� ����� ������ ������� ���� ��� �����
 * @author basharin
 * @ created 26.01.15
 * @ $Author$
 * @ $Revision$
 */

public class PhoneLimit
{
	private Long id;                                //���������� ��� ������
	private String phone;                           //����� �������� �������
	private PhoneLimitType type;                    //��� ������
	private Long count;                             //���������� ������
	private Calendar lastDate;                      //����. ����� ��� ���������� �������� ��� � �����

	/**
	 * @return - id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return - ����� �������� �������
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone - ����� �������� �������
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return - ��� ������
	 */
	public PhoneLimitType getType()
	{
		return type;
	}

	/**
	 * @param type - ��� ������
	 */
	public void setType(PhoneLimitType type)
	{
		this.type = type;
	}

	/**
	 * @return ���������� ������
	 */
	public Long getCount()
	{
		return count;
	}

	/**
	 * @param count - ���������� ������
	 */
	public void setCount(Long count)
	{
		this.count = count;
	}

	/**
	 * @return ����. ����� ��� ���������� �������� ��� � �����
	 */
	public Calendar getLastDate()
	{
		return lastDate;
	}

	/**
	 * @param date - ����. ����� ��� ���������� �������� ��� � �����
	 */
	public void setLastDate(Calendar date)
	{
		this.lastDate = date;
	}
}
