package com.rssl.phizic.business;

/**
 * @author osminin
 * @ created 28.12.2010
 * @ $Author$
 * @ $Revision$
 *
 * Кидаем исключение в случае, если задолженность отрицательная
 */
public class NegativeDebtsBusinessLogicException extends BusinessLogicException
{
	public NegativeDebtsBusinessLogicException(String message)
	{
		super(message);
	}

	public NegativeDebtsBusinessLogicException(Throwable cause)
	{
		super(cause);
	}

	public NegativeDebtsBusinessLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
