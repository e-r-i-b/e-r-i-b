package com.rssl.phizic.rsa.exceptions;

/**
 * Исключение блокирования профиля клиента при взаимодействии с ФМ
 *
 * @author khudyakov
 * @ created 18.08.15
 * @ $Author$
 * @ $Revision$
 */
public class BlockClientOperationFraudException extends RuntimeException
{
	public BlockClientOperationFraudException(String message)
	{
		super(message);
	}

	public BlockClientOperationFraudException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
