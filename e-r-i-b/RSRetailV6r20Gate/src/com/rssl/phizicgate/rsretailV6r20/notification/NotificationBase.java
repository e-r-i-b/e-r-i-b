package com.rssl.phizicgate.rsretailV6r20.notification;

import com.rssl.phizic.notifications.Notification;

import java.util.Calendar;

/**
 * @author Krenev
 * @ created 20.05.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class NotificationBase implements Notification
{
	private long id;
	private Calendar dateCreated;
	private int objectType;
	private String objectNumber;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public Calendar getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(Calendar dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public int getObjectType()
	{
		return objectType;
	}

	public void setObjectType(int objectType)
	{
		this.objectType = objectType;
	}

	public String getObjectNumber()
	{
		return objectNumber;
	}

	public void setObjectNumber(String objectNumber)
	{
		this.objectNumber = objectNumber;
	}

}
