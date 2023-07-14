package com.rssl.auth.csa.front.exceptions;

/**
 * Исключение прерывающее шагалку
 * @author niculichev
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class InterruptStageException extends FrontLogicException
{
	public InterruptStageException()
	{
		super();
	}

	public InterruptStageException(String message)
	{
		super(message);
	}

	public InterruptStageException(Throwable cause)
	{
        super(cause);
    }

	public InterruptStageException(String message, Throwable cause)
	{
	    super(message, cause);
	}
}
