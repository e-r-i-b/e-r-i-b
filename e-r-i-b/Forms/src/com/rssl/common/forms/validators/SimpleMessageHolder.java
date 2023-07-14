package com.rssl.common.forms.validators;

/**
 * @author krenev
 * @ created 30.12.2010
 * @ $Author$
 * @ $Revision$
 */
public class SimpleMessageHolder implements MessageHolder
{
	private String message;

	public SimpleMessageHolder(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessageKey()
	{
		return null;
	}
}
