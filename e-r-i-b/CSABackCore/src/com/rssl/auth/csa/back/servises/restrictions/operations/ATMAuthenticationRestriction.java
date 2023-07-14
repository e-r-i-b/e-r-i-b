package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.BlockingRuleActiveException;
import com.rssl.auth.csa.back.servises.operations.ATMAuthenticationOperation;
import com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules.ATMBlockingRule;
import com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules.ERIBBlockingRule;

/**
 * @author osminin
 * @ created 09.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Ограничение на вход в АТМ
 */
public class ATMAuthenticationRestriction extends BlockingRuleRestrictionBase<ATMAuthenticationOperation>
{
	private static final ATMAuthenticationRestriction INSTANCE = new ATMAuthenticationRestriction();

	/**
	 * @return инстанс ограничения
	 */
	public static ATMAuthenticationRestriction getInstance()
	{
		return INSTANCE;
	}

	public void check(ATMAuthenticationOperation operation) throws Exception
	{
		super.check(operation);

		ERIBBlockingRule rule = new ATMBlockingRule().findByCbCode(operation.getCbCode());
		if (rule != null)
		{
			throw new BlockingRuleActiveException(rule.generateAtmMessage());
		}
	}
}
