package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.NotMainCardRegistrationException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.operations.UserRegistrationOperation;

/**
 * @author niculichev
 * @ created 05.02.14
 * @ $Author$
 * @ $Revision$
 */
public class UserRegistrationNotMainCardRestriction implements OperationRestriction<UserRegistrationOperation>
{
	private static final UserRegistrationNotMainCardRestriction INSTANCE = new UserRegistrationNotMainCardRestriction();
	private static final String INVALID_CARD_RESTRICTION_MESSAGE =
			"По номеру данной карты невозможна регистрация. Начните процесс регистрации заново с номером основной карты. " +
					"Если вы испытываете трудности, обратитесь в Контактный центр Сбербанка за помощью: +7(495)500 55 50, 8(800)555 5550";

	public static UserRegistrationNotMainCardRestriction getInstance()
	{
		return INSTANCE;
	}

	public void check(UserRegistrationOperation operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция на может быть null");
		}
		if (!operation.isMainCard())
		{
			throw new NotMainCardRegistrationException(INVALID_CARD_RESTRICTION_MESSAGE);
		}
	}
}
