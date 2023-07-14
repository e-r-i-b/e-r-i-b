package com.rssl.auth.csa.back.servises.restrictions;

import com.rssl.auth.csa.back.exceptions.BlockingRuleActiveException;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules.ERIBBlockingRule;
import com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules.ERMBBlockingRule;

/**
 * @author osminin
 * @ created 09.10.13
 * @ $Author$
 * @ $Revision$
 *
 * оганичение на вход ЕРМБ
 */
public class ERMBAuthenticationRestriction implements Restriction<IdentificationContext>
{
	private static final ERMBAuthenticationRestriction INSTANCE = new ERMBAuthenticationRestriction();

	/**
	 * @return инстанс ограничения
	 */
	public static ERMBAuthenticationRestriction getInstance()
	{
		return INSTANCE;
	}

	public void check(IdentificationContext context) throws Exception
	{
		if (context == null)
		{
			throw new IllegalArgumentException("Контекст идентификации не может быть null");
		}
		if (ERIBBlockingRule.isGlobalBlocking())
		{
			throw new BlockingRuleActiveException(ERIBBlockingRule.GLOBAL_BLOCKING_MESSAGE);
		}
		ERIBBlockingRule rule = new ERMBBlockingRule().findByCbCode(context.getCbCode());
		if (rule != null)
		{
			throw new BlockingRuleActiveException(rule.generateErmbMessage());
		}
	}
}
