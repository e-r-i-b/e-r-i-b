package com.rssl.phizic.logging.settings;


import java.io.Serializable;
import java.util.Calendar;

/**
 * ������ ���� �� ���������� ������������
 * @author tisov
 * @ created 01.11.13
 * @ $Author$
 * @ $Revision$
 */

public class UserNotificationLogRecord  implements Serializable
{
	private Long id;
	private Long loginId;
	private Calendar additionDate;
	private NotificationInputType type;
	private String value;

	/**
	 * ������ ��� �������������� ������
	 * @return - �� ������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������� ������
	 * @param id - ����� �� ������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * ������ �������������� ������ ������������
	 * @return - �� ������
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * ������ �������������� ������ ������������
	 * @param loginId - ����� ��
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * ������ ���� �������� ������
	 * @return - ���� ��������
	 */
	public Calendar getAdditionDate()
	{
		return additionDate;
	}

	/**
	 * ������ ���� �������� ������
	 * @param additionDate - ����� ���� ��������
	 */
	public void setAdditionDate(Calendar additionDate)
	{
		this.additionDate = additionDate;
	}

	/**
	 * ������ ���� ����������
	 * @return - ��� ����������
	 */
	public NotificationInputType getType()
	{
		return type;
	}

	/**
	 * ������ ���� ����������
	 * @param type - ����� ��� ����������
	 */
	public void setType(NotificationInputType type)
	{
		this.type = type;
	}

	/**
	 * ������ ��������
	 * @return - ��������
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * ������ ��������
	 * @param value - ����� ��������
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
}
