package com.rssl.auth.csa.back.exceptions;

/**
 * @author krenev
 * @ created 14.09.2012
 * @ $Author$
 * @ $Revision$
 * Исключение, сигнализирующе о том, что выполнить действие с операцией в данном состоянии невозможно.
 * Т.е. действие не предназначено для текущего состояния. Например, повторное исполнение или подтверждение уже исполненной заявки. или исполнение неподтвержденной.
 */
public class IllegalOperationStateException extends IllegalStateException
{
	public IllegalOperationStateException(String message)
	{
		super(message);
	}
}
