package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.OperationBase;
import com.rssl.auth.csa.back.servises.operations.*;
import com.rssl.auth.csa.back.servises.restrictions.CompositeRestriction;
import com.rssl.auth.csa.back.servises.restrictions.Restriction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class OperationRestrictionProvider
{
	private static final OperationRestrictionProvider instance = new OperationRestrictionProvider();
	private static final Map<Class<? extends Operation>, Restriction> initializationRestrictions = new HashMap<Class<? extends Operation>, Restriction>();
	private static final Map<Class<? extends Operation>, Restriction> executionRestrictions = new HashMap<Class<? extends Operation>, Restriction>();
	private static final Map<Class<? extends Operation>, Restriction> postConfirmationRestrictions = new HashMap<Class<? extends Operation>, Restriction>();

	static
	{
		initializationRestrictions.put(UserLogonOperation.class, new CompositeRestriction<UserLogonOperation>(UserLogonByBlockingRuleRestriction.getInstance(), ProfileLockedRestriction.getInstance(), MultipleConnetorsRestriction.getInstance()));
		initializationRestrictions.put(UserRegistrationOperation.class, new CompositeRestriction<UserRegistrationOperation>(UserRegistrationByBlockingRuleRestriction.getInstance(), UserRegistrationRequestCountRestriction.getInstance(), UserRegistrationByExistEnterRestriction.getInstance()));
		initializationRestrictions.put(MobileRegistrationOperation.class, new CompositeRestriction<MobileRegistrationOperation>(MobileRegistrationByBlockingRuleRestriction.getInstance(), MobileConnecotrsCountRestriction.getInstance(), MobileRegistrationRequestCountRestriction.getInstance()));
		initializationRestrictions.put(MobileAuthenticationOperation.class, MobileAuthenticationByBlockingRuleRestriction.getInstance());
		initializationRestrictions.put(ATMAuthenticationOperation.class, ATMAuthenticationRestriction.getInstance());

		postConfirmationRestrictions.put(UserRegistrationOperation.class, new CompositeRestriction<UserRegistrationOperation>(UserRegistrationInactiveCardRestriction.getInstance(), UserRegistrationNotMainCardRestriction.getInstance(), UserRegistrationCbCodeRestriction.getInstance(), MultipleUserRegistrationRestriction.getInstance()));
		postConfirmationRestrictions.put(RestorePasswordOperation.class, RestorePasswordCbCodeRestriction.getInstance());
	}

	private OperationRestrictionProvider() {}

	/**
	 * @return инстанс
	 */
	public static OperationRestrictionProvider getInstance()
	{
		return instance;
	}

	/**
	 * Проверить ограничение на операцию при инициализации
	 * @param operation операция для проверки
	 * @throws Exception
	 */
	public void checkBeforeInitialization(OperationBase operation) throws Exception
	{
		check(operation, initializationRestrictions);
	}

	/**
	 * Проверить ограничение на операцию при исполнении
	 * @param operation операция для проверки
	 * @throws Exception
	 */
	public void checkBeforeExecution(OperationBase operation) throws Exception
	{
		check(operation, executionRestrictions);
	}

	/**
	 * Проверить ограничение на операцию при исполнении
	 * @param operation операция для проверки
	 * @throws Exception
	 */
	public void checkPostConfirmation(OperationBase operation) throws Exception
	{
		check(operation, postConfirmationRestrictions);
	}

	private void check(OperationBase operation, Map<Class<? extends Operation>, Restriction> restrictions) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция на может быть null");
		}
		Restriction restriction = restrictions.get(operation.getClass());
		if (restriction != null)
		{
			restriction.check(operation);
		}
	}
}
