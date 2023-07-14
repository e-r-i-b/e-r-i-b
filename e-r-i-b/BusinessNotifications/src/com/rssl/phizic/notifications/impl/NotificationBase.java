package com.rssl.phizic.notifications.impl;

import com.rssl.phizic.notifications.Notification;

import java.util.Calendar;

/**
 * @author eMakarov
 * @ created 28.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class NotificationBase implements BusinessNotification
{
	private Long id;
	private Long loginId;
	private Calendar dateCreated;

	public Long getId()
	{
		return id;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public Calendar getDateCreated()
	{
		return dateCreated;
	}

	public Class<? extends Notification> getType()
	{
		return NotificationBase.class;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public void setDateCreated(Calendar dateCreated)
	{
		this.dateCreated = dateCreated;
	}
}
