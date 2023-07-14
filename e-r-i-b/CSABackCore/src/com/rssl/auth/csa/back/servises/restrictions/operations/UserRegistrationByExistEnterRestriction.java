package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.UserAlreadyEnteredRegisterException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.UserRegistrationOperation;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @ author: Vagin
 * @ created: 16.08.13
 * @ $Author
 * @ $Revision
 * Ограничение на повторную регистрацию для клиента, если он уже ранее входил в ЕРИБ.
 */
public class UserRegistrationByExistEnterRestriction implements OperationRestriction<UserRegistrationOperation>
{
	private static final UserRegistrationByExistEnterRestriction INSTANCE = new UserRegistrationByExistEnterRestriction();

	public static UserRegistrationByExistEnterRestriction getInstance()
	{
		return INSTANCE;
	}

	public void check(UserRegistrationOperation operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция на может быть null");
		}
		if (ConfigFactory.getConfig(Config.class).isDenyExistEnteredRegistration())
		{
			//Регистрация запрещена если пользователь ранее входивший в ЕРИБ
			Long profileId = operation.getProfileId();
			if (!Connector.findExistEnterByProfileId(profileId).isEmpty())
			{
				throw new UserAlreadyEnteredRegisterException("Для профиля " + profileId + " уже существует зарегистрированныe коннекторы по которым совершался вход в систему.");
			}
		}
	}
}
