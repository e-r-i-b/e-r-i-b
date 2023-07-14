package com.rssl.phizic.business.clients.list;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �������
 */

public class Connector
{
	private String userId;
	private String login;
	private Calendar lastLogonDate;

	/**
	 * @return �������������
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * ������ �������������
	 * @param userId �������������
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	/**
	 * @return �����
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * ������ �����
	 * @param login �����
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}

	/**
	 * @return ���� ���������� �����
	 */
	public Calendar getLastLogonDate()
	{
		return lastLogonDate;
	}

	/**
	 * ������ ���� ���������� �����
	 * @param lastLogonDate ���� ���������� �����
	 */
	public void setLastLogonDate(Calendar lastLogonDate)
	{
		this.lastLogonDate = lastLogonDate;
	}
}
