/**
 * @author Alexander Ivanov
 * @ created 12.08.2011
 * @ $Author$
 * @ $Revision$
 */
package com.rssl.phizic.messaging.mail.file;

import javax.mail.MessagingException;
/*
 * Транспортное исключение
 */
public class TransportException extends MessagingException
{
	public TransportException(String cause)
	{
		super(cause);
	}
	public TransportException(String cause,Throwable e)
	{
		this(getCause(cause,e));
	}
	private static String getCause(String cause,Throwable e)
	{
		StringBuilder buff = new StringBuilder(cause);
		if(e != null)
		{
			cause = e.getLocalizedMessage();
			if(cause != null)
			{
				if(buff.length() > 0)
				{
					buff.append(' ');
				}
				buff.append(cause);
			}
		}
		return buff.toString();
	}
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
}
