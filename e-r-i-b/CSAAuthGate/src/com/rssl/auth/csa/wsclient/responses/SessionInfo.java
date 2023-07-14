package com.rssl.auth.csa.wsclient.responses;

import java.util.Calendar;

/**
 * Информация о сессии
 * @author niculichev
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class SessionInfo
{
	private String SID;                 // идентификатор открытой сессии.
	private Calendar creationDate;      // дата создания сессии
	private Calendar expireDate;        // дата инвалиации сессии (после истечения этой даты запросы по сессии не будут приниматься)
	private Calendar prevSessionDate;   // дата открытия предыдущей сессии
	private String prevSID;             // идентификатор предыдущей сессии

	public String getSID()
	{
		return SID;
	}

	public void setSID(String SID)
	{
		this.SID = SID;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public Calendar getExpireDate()
	{
		return expireDate;
	}

	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}

	public Calendar getPrevSessionDate()
	{
		return prevSessionDate;
	}

	public void setPrevSessionDate(Calendar prevSessionDate)
	{
		this.prevSessionDate = prevSessionDate;
	}

	public String getPrevSID()
	{
		return prevSID;
	}

	public void setPrevSID(String prevSID)
	{
		this.prevSID = prevSID;
	}
}
