package com.rssl.phizic.logging.offerNotification;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author lukina
 * @ created 08.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class NotificationLogEntry  implements Serializable
{
	private Long id;                      // id записи.
	private Long loginId;                      // id записи.
	private Long notificationId;           // ссылка на уведомление.
	private Calendar date;                // время события.
	private String name;                // название уведомления
	private NotificationLogEntryType type; // тип события.

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public Long getNotificationId()
	{
		return notificationId;
	}

	public void setNotificationId(Long notificationId)
	{
		this.notificationId = notificationId;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public NotificationLogEntryType getType()
	{
		return type;
	}

	public void setType(NotificationLogEntryType type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
