package com.rssl.auth.csa.front.exceptions;

/**
 * Исключение, сигнализируюещее о необходимости сброса аутентификации в начальное состояние
 * @author niculichev
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class ResetStageException extends FrontLogicException
{
	public ResetStageException()
	{
		super();
	}

	public ResetStageException(String message)
	{
		super(message);
	}

	public ResetStageException(Throwable cause)
	{
        super(cause);
    }

	public ResetStageException(String message, Throwable cause)
	{
	    super(message, cause);
	}
}
