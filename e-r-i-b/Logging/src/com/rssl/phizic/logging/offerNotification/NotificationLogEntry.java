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
	private Long id;                      // id ������.
	private Long loginId;                      // id ������.
	private Long notificationId;           // ������ �� �����������.
	private Calendar date;                // ����� �������.
	private String name;                // �������� �����������
	private NotificationLogEntryType type; // ��� �������.

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
