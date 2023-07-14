package com.rssl.phizic.messaging.mail.rsalarm;

import com.rssl.phizic.messaging.TranslitMode;

import javax.mail.Address;

/**
 * @author Evgrafov
 * @ created 02.06.2006
 * @ $Author: erkin $
 * @ $Revision: 20152 $
 */

public class PhoneAddress extends Address
{
	private String phoneNumber;
	private TranslitMode translit;

	public PhoneAddress(String phoneNimber)
	{
		this.phoneNumber = phoneNimber;
	}

	public PhoneAddress(String phoneNumber, TranslitMode translit)
	{
		this.phoneNumber = phoneNumber;
		this.translit = translit;
	}

	public String getType()
	{
		return "phone";
	}

	public String toString()
	{
		return phoneNumber;
	}

	public boolean equals(Object object)
	{
		if(!(object instanceof PhoneAddress))
			return false;

		PhoneAddress that = (PhoneAddress) object;
		return this.phoneNumber.equals(that.phoneNumber);
	}

	public TranslitMode getTranslit()
	{
		return translit;
	}

	public void setTranslit(TranslitMode translit)
	{
		this.translit = translit;
	}
}