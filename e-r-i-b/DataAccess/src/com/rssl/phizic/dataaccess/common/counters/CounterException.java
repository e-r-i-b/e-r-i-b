package com.rssl.phizic.dataaccess.common.counters;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 26.09.2006
 * Time: 15:11:19
 * To change this template use File | Settings | File Templates.
 */
public class CounterException extends Exception
{
	public CounterException()
	{		
	}
	public CounterException(Throwable cause)
	{
		super(cause);
	}

	public CounterException(String message)
	{
		super(message);
	}
}
