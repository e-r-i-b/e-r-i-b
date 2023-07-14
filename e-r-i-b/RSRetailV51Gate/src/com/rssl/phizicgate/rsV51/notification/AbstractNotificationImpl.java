package com.rssl.phizicgate.rsV51.notification;

import com.rssl.phizic.notifications.Notification;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Omeliyanchuk
 * @ created 24.01.2007
 * @ $Author$
 * @ $Revision$
 */

/*
описание читать в интерфейсе
 */
public abstract class AbstractNotificationImpl implements Notification
{
	private long     id;
	private int      notificationObjectType;
	private int      objectType;
	protected String   objectNumber;
	private Calendar createDate;
	private Calendar dateCreated;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public int getObjectType()
	{
		return objectType;
	}

	public void setObjectType(int objectType)
	{
		this.objectType = objectType;
	}

	public int getNotificationObjectType()
	{
		return notificationObjectType;
	}

	public void setNotificationObjectType(int notificationObjectType)
	{
		this.notificationObjectType = notificationObjectType;
	}

	public String getObjectNumber()
	{
		return objectNumber;
	}

	public void setObjectNumber(String objectNumber)
	{
		this.objectNumber = objectNumber;
	}

	public Calendar getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Calendar createDate)
	{
		this.createDate = createDate;
	}


	public Calendar getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(Calendar dateCreated)
	{
		this.dateCreated = dateCreated;
	}
}
