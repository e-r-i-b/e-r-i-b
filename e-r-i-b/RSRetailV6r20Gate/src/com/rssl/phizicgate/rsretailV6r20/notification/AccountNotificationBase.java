package com.rssl.phizicgate.rsretailV6r20.notification;

import com.rssl.phizic.gate.notification.AccountNotification;

/**
 * @author Krenev
 * @ created 20.05.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class AccountNotificationBase extends NotificationBase implements AccountNotification
{
	private String accountNumber;

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}
}
