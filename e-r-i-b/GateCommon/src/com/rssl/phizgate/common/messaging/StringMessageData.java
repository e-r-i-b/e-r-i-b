package com.rssl.phizgate.common.messaging;

/**
 * @author Krenev
 * @ created 03.02.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class StringMessageData extends MessageDataBase
{
	private String body;

	public String getBody()
	{
		return body;
	}

	public void setBody(Object body)
	{
		this.body = (String) body;
	}

	public String getBodyAsString(String coding)
	{
		return getBody();
	}
}
