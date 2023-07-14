package com.rssl.phizic.gate.exceptions;

/**
 * @author krenev
 * @ created 01.10.2010
 * @ $Author$
 * @ $Revision$
 * Исключение сигнализирующее о том, что действие не отностися к объектам системы и
 * не может быть обработано должным образом.
 */
public class InvalidTargetException extends GateException
{
	public InvalidTargetException(String message)
	{
		super(message);
	}

	public InvalidTargetException(Throwable couse)
	{
		super(couse);
	}
}
