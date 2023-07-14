package com.rssl.phizic.business.messaging.info;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.messaging.MailFormat;
import com.rssl.phizic.messaging.mail.messagemaking.ContactInfo;
import com.rssl.phizic.messaging.TranslitMode;

/**
 * @author Kosyakov
 * @ created 18.01.2007
 * @ $Author: osminin $
 * @ $Revision: 71731 $
 */
public class PersonalSubscriptionData implements ContactInfo
{
	private Long id;
	private CommonLogin login;
	private String EmailAddress;
	private String MobilePhone;
	private TranslitMode smsTranslitMode;
	private MailFormat mailFormat;   //формат отправки mail-оповещений

	public PersonalSubscriptionData()
	{
		this.smsTranslitMode = TranslitMode.DEFAULT;
		this.mailFormat = MailFormat.PLAIN_TEXT;
	}

	public MailFormat getMailFormat()
	{
		return mailFormat;
	}

	public void setMailFormat(MailFormat mailFormat)
	{
		this.mailFormat = mailFormat;
	}


	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public CommonLogin getLogin()
	{
		return login;
	}

	public void setLogin(CommonLogin login)
	{
		this.login = login;
	}

	public Long getLoginId()
	{
		return login == null ? null : login.getId();
	}

	public String getEmailAddress()
	{
		return EmailAddress;
	}

	public void setEmailAddress(String emailAddress)
	{
		EmailAddress = emailAddress;
	}

	public String getMobilePhone()
	{
		return MobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		MobilePhone = mobilePhone;
	}

	public TranslitMode getSmsTranslitMode()
	{
		return smsTranslitMode;
	}

	public void setSmsTranslitMode(TranslitMode smsTranslitMode)
	{
		this.smsTranslitMode = smsTranslitMode;
	}
}
