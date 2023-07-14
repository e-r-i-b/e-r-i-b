package com.rssl.auth.csa.back.exceptions;

/**
 * @author krenev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 * Исключение, сигнализирующее о том, что имеется слишком много неподтвержденных запросов
 */
public class TooManyRequestException extends RestrictionException
{
	public TooManyRequestException(Long profileId)
	{
		super("Для профиля " + profileId + " превышено количество неподтвержденных запросов ");
	}

	public TooManyRequestException(String message)
	{
		super(message);
	}
}
