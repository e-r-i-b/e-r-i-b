package com.rssl.phizic.logging.contact.synchronization;

import com.rssl.phizic.logging.LogEntry;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 30.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ���� � ���������� � ���������� ���������� ������������� �������� �����
 */
public class ContactSyncCountExceedLog implements LogEntry
{
	private Long loginId;
	private String name;
	private String document;
	private Calendar birthDay;
	private String tb;
	private Calendar syncDate;
	private String message;

	/**
	 * @return ������������� ������ �������
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * ���������� ������������� ������ �������
	 * @param loginId ������������� ������ �������
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return ��� �������(���)
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ���������� ��� �������(���)
	 * @param name ��� �������(���)
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ��� �������
	 */
	public String getDocument()
	{
		return document;
	}

	/**
	 * ���������� ��� �������
	 * @param document ��� �������
	 */
	public void setDocument(String document)
	{
		this.document = document;
	}

	/**
	 * @return ���� ��������� �������
	 */
	public Calendar getBirthDay()
	{
		return birthDay;
	}

	/**
	 * ���������� ���� ��������� �������
	 * @param birthDay ���� ��������� �������
	 */
	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	/**
	 * @return �� �������
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * ���������� �� �������
	 * @param tb �� �������
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return ���� �������������
	 */
	public Calendar getSyncDate()
	{
		return syncDate;
	}

	/**
	 * ���������� ���� �������������
	 * @param syncDate ���� �������������
	 */
	public void setSyncDate(Calendar syncDate)
	{
		this.syncDate = syncDate;
	}

	/**
	 * @return ������������ ���������� ���������
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * ���������� ������������ ���������� ���������
	 * @param message ������������ ���������� ���������
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
}
