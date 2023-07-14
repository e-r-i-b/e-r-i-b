package com.rssl.phizic.gate.claims.sbnkd.impl;

import com.rssl.phizic.gate.claims.sbnkd.IssueCardStatus;

import java.util.Calendar;

/**
 * ����� ����� ������ �����.
 *
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class BaseCardClaim
{
	private Long id;
	private String UID;
	private Long ownerId;
	private String phone;
	private boolean isGuest;
	private String login;
	private Calendar creationDate;
	private IssueCardStatus status;
	private String email;

	/**
	 * @return ���� ��������� ������.
	 */
	public final Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate ���� �������� ������.
	 */
	public final void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return ������������� ������.
	 */
	public final Long getId()
	{
		return id;
	}

	/**
	 * @param id ������������� ������.
	 */
	public final void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return �������� ����.
	 */
	public final boolean isGuest()
	{
		return isGuest;
	}

	/**
	 * @param guest �������� ����.
	 */
	public final void setGuest(boolean guest)
	{
		isGuest = guest;
	}

	/**
	 * @return ������������� ���������.
	 */
	public final Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId ������������� ���������.
	 */
	public final void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

	/**
	 * @return ����� �������� �����.
	 */
	public final String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone ����� ��������.
	 */
	public final void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return ������� �������������.
	 */
	public final String getUID()
	{
		return UID;
	}

	/**
	 * @param UID ������� �������������.
	 */
	public final void setUID(String UID)
	{
		this.UID = UID;
	}

	/**
	 * @return ������ ������.
	 */
	public IssueCardStatus getStatus()
	{
		return status;
	}

	/**
	 * @param status ������ ������.
	 */
	public void setStatus(IssueCardStatus status)
	{
		this.status = status;
	}

	/**
	 * @return email.
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email email.
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return ��������� �����.
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * @param login ��������� �����.
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}
}
