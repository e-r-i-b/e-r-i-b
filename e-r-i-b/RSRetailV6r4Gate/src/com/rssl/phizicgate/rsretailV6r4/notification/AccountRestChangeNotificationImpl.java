package com.rssl.phizicgate.rsretailV6r4.notification;

import com.rssl.phizic.notifications.Notification;
import com.rssl.phizic.gate.notification.AccountRestChangeNotification;

/**
 * @author Krenev
 * @ created 20.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class AccountRestChangeNotificationImpl extends AccountNotificationBase implements AccountRestChangeNotification
{
	private Double oldRest;
	private Double currentRest;
	private Double transactionSum;

	public Class<? extends Notification> getType()
	{
		return AccountRestChangeNotification.class;
	}

	public Double getOldRest()
	{
		return oldRest;
	}

	public void setOldRest(Double oldRest)
	{
		this.oldRest = oldRest;
	}

	public Double getCurrentRest()
	{
		return currentRest;
	}

	public void setCurrentRest(Double currentRest)
	{
		this.currentRest = currentRest;
	}

	public Double getTransactionSum()
	{
		return transactionSum;
	}

	public void setTransactionSum(double transactionSum)
	{
		this.transactionSum = transactionSum;
	}
}
