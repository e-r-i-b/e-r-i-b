package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.BlockingRuleActiveException;
import com.rssl.auth.csa.back.servises.operations.MobileRegistrationOperation;
import com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules.ERIBBlockingRule;
import com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules.MAPIBlockingRule;

/**
 * @author osminin
 * @ created 09.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Ограничение на регистрацию мобильного приложения
 */
public class MobileRegistrationByBlockingRuleRestriction extends BlockingRuleRestrictionBase<MobileRegistrationOperation>
{
	private static final MobileRegistrationByBlockingRuleRestriction INSTANCE = new MobileRegistrationByBlockingRuleRestriction();

	/**
	 * @return инстанс ограничения
	 */
	public static MobileRegistrationByBlockingRuleRestriction getInstance()
	{
		return INSTANCE;
	}

	public void check(MobileRegistrationOperation operation) throws Exception
	{
		super.check(operation);

		ERIBBlockingRule rule = new MAPIBlockingRule().findByCbCode(operation.getCbCode());
		if (rule != null)
		{
			throw new BlockingRuleActiveException(rule.generateMapiMessage(), rule.getDate());
		}
	}
}
