package com.rssl.phizic.business.profile;

import com.rssl.phizic.messaging.MailFormat;

/**
 * @author koptyaev
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("JavaDoc")
public class UserSubscriptionInfo
{
	private String emailAddress; //e-mail адрес
	private MailFormat mailFormat; //формат email: простой текст или html-страница

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public MailFormat getMailFormat()
	{
		return mailFormat;
	}

	public void setMailFormat(MailFormat mailFormat)
	{
		this.mailFormat = mailFormat;
	}
}
