package com.rssl.phizic.gate.claims.sbnkd.impl;

import com.rssl.phizic.gate.claims.sbnkd.IssueCardStatus;

import java.util.Calendar;

/**
 * Общая часть заявок СБНКД.
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
	 * @return дата созданияя заявки.
	 */
	public final Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate дата создания заявки.
	 */
	public final void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return идентификатор заявки.
	 */
	public final Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор заявки.
	 */
	public final void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return гостевой вход.
	 */
	public final boolean isGuest()
	{
		return isGuest;
	}

	/**
	 * @param guest гостевой вход.
	 */
	public final void setGuest(boolean guest)
	{
		isGuest = guest;
	}

	/**
	 * @return идентификатор владельца.
	 */
	public final Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId идентификатор владельца.
	 */
	public final void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

	/**
	 * @return номер телефона входа.
	 */
	public final String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone номер телефона.
	 */
	public final void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return внешний идентификатор.
	 */
	public final String getUID()
	{
		return UID;
	}

	/**
	 * @param UID внешний идентификатор.
	 */
	public final void setUID(String UID)
	{
		this.UID = UID;
	}

	/**
	 * @return статус заявки.
	 */
	public IssueCardStatus getStatus()
	{
		return status;
	}

	/**
	 * @param status статус заявки.
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
	 * @return текстовый логин.
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * @param login текстовый логин.
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}
}
