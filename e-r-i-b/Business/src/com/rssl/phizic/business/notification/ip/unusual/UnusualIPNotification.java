package com.rssl.phizic.business.notification.ip.unusual;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 10.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ����� � �������������� IP
 */

public class UnusualIPNotification
{
	private Long id;
	private Calendar dateCreated;
	private Long loginId;
	private long attemptsCount;
	private String message;

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
	 * @return ���� ��������
	 */
	public Calendar getDateCreated()
	{
		return dateCreated;
	}

	/**
	 * ������ ���� ��������
	 * @param dateCreated ����
	 */
	public void setDateCreated(Calendar dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	/**
	 * @return ������������� ������ ����������
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * ������ ������������� ������ ����������
	 * @param loginId ������������� ������
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return ���������� �������
	 */
	public long getAttemptsCount()
	{
		return attemptsCount;
	}

	/**
	 * ������ ���������� �������
	 * @param attemptsCount ���������� �������
	 */
	public void setAttemptsCount(long attemptsCount)
	{
		this.attemptsCount = attemptsCount;
	}

	/**
	 * @return ��������� � ���
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * ������ ��������� � ���
	 * @param message ���������
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
}
