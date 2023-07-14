package com.rssl.phizic.notifications.impl;

import com.rssl.phizic.gate.notification.AccountNotification;

/**
 * @author hudyakov
 * @ created 11.10.2009
 * @ $Author$
 * @ $Revision$
 */

public class AccountNotificationBase extends NotificationBase implements AccountNotification
{
	public String accountNumber;

	/**
	 * ��� �����: ���� ��� �����, ��. ResourceType enum
	 */
	private String accountResourceType;
	private String recipientAccountNumber;                        //����� �����/����� ����������
	private String recipientAccountResourceType;                  //��� �����(���� ��� �����) ����������

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public String getAccountResourceType()
	{
		return accountResourceType;
	}

	public void setAccountResourceType(String accountResourceType)
	{
		this.accountResourceType = accountResourceType;
	}

	public String getRecipientAccountNumber()
	{
		return recipientAccountNumber;
	}

	public void setRecipientAccountNumber(String recipientAccountNumber)
	{
		this.recipientAccountNumber = recipientAccountNumber;
	}

	public String getRecipientAccountResourceType()
	{
		return recipientAccountResourceType;
	}

	public void setRecipientAccountResourceType(String recipientAccountResourceType)
	{
		this.recipientAccountResourceType = recipientAccountResourceType;
	}
}
