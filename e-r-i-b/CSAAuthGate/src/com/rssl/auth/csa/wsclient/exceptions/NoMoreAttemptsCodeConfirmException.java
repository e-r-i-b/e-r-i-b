package com.rssl.auth.csa.wsclient.exceptions;

/**
 * Исключение отсутствия попыток подтверждения операции
 *
 * @author akrenev
 * @ created 03.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class NoMoreAttemptsCodeConfirmException extends InvalidCodeConfirmException
{
	/**
	 * конструктор
	 * @param cause исключение породившее данное
	 */
	public NoMoreAttemptsCodeConfirmException(InvalidCodeConfirmException cause)
	{
		super(cause);
	}
}
