package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.BlockingRuleActiveException;
import com.rssl.auth.csa.back.servises.operations.UserRegistrationOperation;
import com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules.ERIBBlockingRule;

/**
 * @author osminin
 * @ created 09.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Ограничение на регистрацию пользователя в ЦСА
 */
public class UserRegistrationByBlockingRuleRestriction extends BlockingRuleRestrictionBase<UserRegistrationOperation>
{
	private static final UserRegistrationByBlockingRuleRestriction INSTANCE = new UserRegistrationByBlockingRuleRestriction();

	/**
	 * @return инстанс ограничения
	 */
	public static UserRegistrationByBlockingRuleRestriction getInstance()
	{
		return INSTANCE;
	}

	public void check(UserRegistrationOperation operation) throws Exception
	{
		super.check(operation);

		ERIBBlockingRule rule = new ERIBBlockingRule().findByCbCode(operation.getCbCode());
		if (rule != null)
		{
			throw new BlockingRuleActiveException(rule.generateEribMessage());
		}
	}
}
