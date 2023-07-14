package com.rssl.phizicgate.rsretailV6r4.subscription;

import java.util.Set;

/**
 * @author Omeliyanchuk
 * @ created 08.06.2007
 * @ $Author$
 * @ $Revision$
 */

public class SubscriptionObject
{
	private String id;
	private long isCur;
	private long branch;
	private long objectType;
	private long referenc;
	private Set<SubscriptionNotification> notificationTypes = null;

	public long getReferenc()
	{
		return referenc;
	}

	public void setReferenc(long referenc)
	{
		this.referenc = referenc;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public long getIsCur()
	{
		return isCur;
	}

	public void setIsCur(long cur)
	{
		isCur = cur;
	}

	public long getBranch()
	{
		return branch;
	}

	public void setBranch(long branch)
	{
		this.branch = branch;
	}

	public long getObjectType()
	{
		return objectType;
	}

	public void setObjectType(long objectType)
	{
		this.objectType = objectType;
	}

	public Set<SubscriptionNotification> getNotificationTypes()
	{
		return notificationTypes;
	}

	public void setNotificationTypes(Set<SubscriptionNotification> notificationTypes)
	{
		this.notificationTypes = notificationTypes;
	}
}
