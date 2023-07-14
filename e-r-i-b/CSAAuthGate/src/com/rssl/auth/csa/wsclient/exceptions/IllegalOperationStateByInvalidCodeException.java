package com.rssl.auth.csa.wsclient.exceptions;

/**
 * »сключение, сигнализирующе о том, что выполнить действие с операцией в данном состо€нии невозможно
 * изза нарушени€ ограничени€ количества ввода неправильного кода подтверждени€
 * @author niculichev
 * @ created 18.11.13
 * @ $Author$
 * @ $Revision$
 */
public class IllegalOperationStateByInvalidCodeException extends IllegalOperationStateException
{
	public IllegalOperationStateByInvalidCodeException()
	{
		super();
	}

	public IllegalOperationStateByInvalidCodeException(Throwable cause)
	{
		super(cause);
	}
}
