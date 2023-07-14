package com.rssl.phizic.messaging.mail;

/**
 * @author emakarov
 * @ created 30.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class SmsMessage
{
	private String address;
	private String text;

	public SmsMessage(String address, String text)
	{
		this.address = address;
		this.text = text;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}
}
