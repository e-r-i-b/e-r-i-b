package com.rssl.phizicgate.rsV51.notification;

import com.rssl.phizic.notifications.Notification;
import com.rssl.phizic.gate.notification.AccountRestChangeNotification;

/**
 * @author Omeliyanchuk
 * @ created 06.02.2007
 * @ $Author$
 * @ $Revision$
 */

public class AccountRestChangeNotificationImpl  extends AbstractNotificationImpl implements AccountRestChangeNotification
{
	private Double   oldRest;
	private Double   currentRest;
	private Double   transactionSum;

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

	public void setTransactionSum(Double transactionSum)
	{
		this.transactionSum = transactionSum;
	}

	public Class<? extends Notification> getType()
	{
		return AccountRestChangeNotification.class;
	}

	public String getAccountNumber()
	{
		return objectNumber.substring(2);
	}
}
