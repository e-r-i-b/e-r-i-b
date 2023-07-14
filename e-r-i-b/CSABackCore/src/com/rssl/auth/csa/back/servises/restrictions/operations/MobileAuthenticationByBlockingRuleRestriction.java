package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.BlockingRuleActiveException;
import com.rssl.auth.csa.back.servises.operations.MobileAuthenticationOperation;
import com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules.ERIBBlockingRule;
import com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules.MAPIBlockingRule;

/**
 * @author osminin
 * @ created 09.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ����������� �� ���� � ��������� ����������
 */
public class MobileAuthenticationByBlockingRuleRestriction extends BlockingRuleRestrictionBase<MobileAuthenticationOperation>
{
	private static final MobileAuthenticationByBlockingRuleRestriction INSTANCE = new MobileAuthenticationByBlockingRuleRestriction();

	/**
	 * @return ������� �����������
	 */
	public static MobileAuthenticationByBlockingRuleRestriction getInstance()
	{
		return INSTANCE;
	}

	/**
	 * ��������� �� ������������ ����������
	 * @param operation ��������
	 * @throws Exception
	 */
	public void check(MobileAuthenticationOperation operation) throws Exception
	{
		super.check(operation);

		ERIBBlockingRule rule = new MAPIBlockingRule().findByCbCode(operation.getCbCode());
		if (rule != null)
		{
			throw new BlockingRuleActiveException(rule.generateMapiMessage(), rule.getDate());
		}
	}
}
