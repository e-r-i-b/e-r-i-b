package com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer;

/**
 * @author Nady
 * @ created 23.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class OutcomeMessage
{
	private String guid;
	private String address;
	private String priority;
	private String text;

	public String getGuid()
	{
		return guid;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPriority()
	{
		return priority;
	}

	public void setPriority(String priority)
	{
		this.priority = priority;
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
