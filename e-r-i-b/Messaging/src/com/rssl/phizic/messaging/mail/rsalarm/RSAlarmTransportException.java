package com.rssl.phizic.messaging.mail.rsalarm;

import javax.mail.MessagingException;

/**
 * @author Evgrafov
 * @ created 02.06.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3813 $
 */
public class RSAlarmTransportException extends MessagingException
{
	public RSAlarmTransportException(String name)
	{
		super(name);
	}

	public RSAlarmTransportException(String name, Throwable exception)
	{
		super(calculateName(name, exception), calculateException(exception));
	}

	private static String calculateName(String name, Throwable exception)
	{
		if(exception instanceof Exception)
			return name;
		else
			return name + exception.getMessage();
	}

	private static Exception calculateException(Throwable exception)
	{
		if (exception instanceof Exception)
			return (Exception) exception;
		else
			return null;
	}
}