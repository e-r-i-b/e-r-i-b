package com.rssl.phizic.csaadmin.business.session;

import com.rssl.phizic.csaadmin.business.login.Login;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ������
 */

public class Session
{
	private String sid;
	private Login login;
	private Calendar creationDate;
	private Calendar closeDate;
	private SessionState state;

	/**
	 * ������ ����������� ��� ����������
	 */
	public Session()
	{
	}

	/**
	 * �������� ������ ��� ������
	 * @param login - ����� ����������
	 */
	public Session(Login login)
	{
		if (login == null)
		{
			throw new IllegalArgumentException("����� �� ����� ���� null");
		}
		this.login = login;
		this.creationDate = Calendar.getInstance();
		this.state = SessionState.ACTIVE;
	}


	/**
	 * @return ������������� ������
	 */
	public String getSid()
	{
		return sid;
	}

	/**
	 * ������ ������������� ������
	 * @param sid ������������� ������
	 */
	public void setSid(String sid)
	{
		this.sid = sid;
	}

	/**
	 * @return �����, ��� �������� ��������� ������
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * ������ �����, ��� �������� ��������� ������
	 * @param login �����, ��� �������� ��������� ������
	 */
	public void setLogin(Login login)
	{
		this.login = login;
	}

	/**
	 * @return ���� �������� ������
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * ������ ���� �������� ������
	 * @param creationDate ���� �������� ������
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return ���� �������� ������
	 */
	public Calendar getCloseDate()
	{
		return closeDate;
	}

	/**
	 * ������ ���� �������� ������
	 * @param closeDate ���� �������� ������
	 */
	public void setCloseDate(Calendar closeDate)
	{
		this.closeDate = closeDate;
	}

	/**
	 * @return ��������� ������
	 */
	public SessionState getState()
	{
		return state;
	}

	/**
	 * ������ ��������� ������
	 * @param state ��������� ������
	 */
	public void setState(SessionState state)
	{
		this.state = state;
	}
}
