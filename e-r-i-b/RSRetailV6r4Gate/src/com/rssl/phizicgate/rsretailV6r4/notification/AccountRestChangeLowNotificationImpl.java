package com.rssl.phizicgate.rsretailV6r4.notification;

import com.rssl.phizic.notifications.Notification;
import com.rssl.phizic.gate.notification.AccountRestChangeLowNotification;

/**
 * @author Krenev
 * @ created 20.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class AccountRestChangeLowNotificationImpl extends AccountNotificationBase implements AccountRestChangeLowNotification
{
	private Double rest;
	private Double minRest;

	public Class<? extends Notification> getType()
	{
		return AccountRestChangeLowNotification.class;
	}

	public Double getRest()
	{
		return rest;
	}

	public void setRest(Double rest)
	{
		this.rest = rest;
	}

	public Double getMinRest()
	{
		return minRest;
	}

	public void setMinRest(Double minRest)
	{
		this.minRest = minRest;
	}
}
