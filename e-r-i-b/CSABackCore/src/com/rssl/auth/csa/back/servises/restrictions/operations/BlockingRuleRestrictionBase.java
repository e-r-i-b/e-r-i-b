package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.BlockingRuleActiveException;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules.ERIBBlockingRule;

/**
 * @author osminin
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ������� ����� ��� ����������� �� �������� ����������
 */
public abstract class BlockingRuleRestrictionBase<T extends Operation> implements OperationRestriction<T>
{
	public void check(T operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("�������� �� ����� ���� null");
		}
		if (ERIBBlockingRule.isGlobalBlocking())
		{
			throw new BlockingRuleActiveException(ERIBBlockingRule.GLOBAL_BLOCKING_MESSAGE);
		}
	}
}
