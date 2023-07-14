package com.rssl.auth.csa.back.exceptions;

/**
 * Исключение при срабатывании ограничения доступности операций, в разере ТБ
 * @author niculichev
 * @ created 05.02.14
 * @ $Author$
 * @ $Revision$
 */
public class DenyCbCodeOperationException extends RestrictionException
{
	public DenyCbCodeOperationException(String message)
	{
		super(message);
	}
}
