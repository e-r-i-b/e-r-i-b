package com.rssl.auth.csa.back.exceptions;

/**
 * Исключение, сигнализирующее о неактивности карты регистрации
 * @author niculichev
 * @ created 05.02.14
 * @ $Author$
 * @ $Revision$
 */
public class InactiveCardRegistrationException extends RestrictionException
{
	public InactiveCardRegistrationException(String message)
	{
		super(message);
	}
}
