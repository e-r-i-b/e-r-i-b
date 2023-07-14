package com.rssl.phizgate.common.messaging;

import com.rssl.phizgate.common.messaging.MessageDataBase;

import java.io.UnsupportedEncodingException;

/**
 * @author Krenev
 * @ created 03.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class ByteArrayMessageData extends MessageDataBase
{
	private byte[] body;

	/**
	 * ctor
	 */
	public ByteArrayMessageData()
	{
	}

	/**
	 * ctor
	 * @param body - данные
	 */
	public ByteArrayMessageData(byte[] body)
	{
		this.body = body;
	}

	public byte[] getBody()
	{
		return body;
	}

	public void setBody(Object body)
	{
		this.body = (byte[]) body;
	}

	public String getBodyAsString(String coding)
	{
		if (coding == null)
		{
			return new String(body);
		}
		try
		{
			return new String(body, coding);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
	}
}
