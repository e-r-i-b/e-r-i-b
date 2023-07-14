package com.rssl.phizicgate.rsV55.notification;

import com.rssl.phizic.notifications.Notification;
import com.rssl.phizic.gate.notification.AccountRestChangeLowNotification;

/**
 * @author Omeliyanchuk
 * @ created 03.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class AccountRestChangeLowNotificationImpl  extends AbstractNotificationImpl implements AccountRestChangeLowNotification
{
	private Double   rest;
	private Double   minRest;

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

	public Class<? extends Notification> getType()
	{
		return AccountRestChangeLowNotification.class;
	}

	public String getAccountNumber()
	{
		return objectNumber.substring(2);
	}
}
