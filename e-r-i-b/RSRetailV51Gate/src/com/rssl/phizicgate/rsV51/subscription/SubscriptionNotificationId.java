package com.rssl.phizicgate.rsV51.subscription;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 08.06.2007
 * @ $Author$
 * @ $Revision$
 */

public class SubscriptionNotificationId implements Serializable
{
	private String objectId;
	private long notifyType;

	public String getObjectId()
	{
		return objectId;
	}

	public void setObjectId(String objectId)
	{
		this.objectId = objectId;
	}

	public long getNotifyType()
	{
		return notifyType;
	}

	public void setNotifyType(long notifyType)
	{
		this.notifyType = notifyType;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		SubscriptionNotificationId id = (SubscriptionNotificationId) o;

		return (objectId != null ? objectId.equals(id.objectId) : id.objectId == null);
	}

	public int hashCode()
	{
		return objectId.hashCode();
	}
}
