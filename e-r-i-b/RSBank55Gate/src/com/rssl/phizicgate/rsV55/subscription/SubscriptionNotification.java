package com.rssl.phizicgate.rsV55.subscription;

/**
 * @author Omeliyanchuk
 * @ created 08.06.2007
 * @ $Author$
 * @ $Revision$
 */

public class SubscriptionNotification
{

    private long isCur;
	private long branch;
	private long objectType;
	private long id;
	private long notifyType;
	//private String objectID;
	//private SubscriptionNotificationId id;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getNotifyType()
	{
		return notifyType;
	}

	public void setNotifyType(long notifyType)
	{
		this.notifyType = notifyType;
	}

/*	public SubscriptionNotificationId getId()
	{
		return id;
	}

	public void setId(SubscriptionNotificationId id)
	{
		this.id = id;
	}
*/
	
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
}
