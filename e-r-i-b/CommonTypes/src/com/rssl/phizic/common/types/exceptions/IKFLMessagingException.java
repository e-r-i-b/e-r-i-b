package com.rssl.phizic.common.types.exceptions;

/**
 * @author Erkin
 * @ created 01.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class IKFLMessagingException extends Exception
{
	/**
	 * ctor
	 * @param message
	 */
	public IKFLMessagingException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param message
	 * @param cause
	 */
	public IKFLMessagingException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * ctor
	 * @param cause
	 */
	public IKFLMessagingException(Throwable cause)
	{
		super(cause);
	}
}
