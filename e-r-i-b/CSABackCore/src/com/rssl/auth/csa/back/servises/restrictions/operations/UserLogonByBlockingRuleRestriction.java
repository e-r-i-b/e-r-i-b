package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.BlockingRuleActiveException;
import com.rssl.auth.csa.back.servises.operations.UserLogonOperation;
import com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules.ERIBBlockingRule;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class UserLogonByBlockingRuleRestriction extends BlockingRuleRestrictionBase<UserLogonOperation>
{
	private static final UserLogonByBlockingRuleRestriction INSTANCE = new UserLogonByBlockingRuleRestriction();

	/**
	 * @return инстанс ограничения
	 */
	public static UserLogonByBlockingRuleRestriction getInstance()
	{
		return INSTANCE;
	}

	public void check(UserLogonOperation operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция на может быть null");
		}
		//Вход в мАпм и АТМ совершается назависимо от блокировки ЕРИБ-а
		if (operation.isERIBUser())
		{
			if (ERIBBlockingRule.isGlobalBlocking())
			{
				throw new BlockingRuleActiveException(ERIBBlockingRule.GLOBAL_BLOCKING_MESSAGE);
			}

			ERIBBlockingRule rule = new ERIBBlockingRule().findByCbCode(operation.getCbCode());
			if (rule != null)
			{
				throw new BlockingRuleActiveException(rule.generateEribMessage());
			}
		}
	}
}
