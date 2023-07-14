package com.rssl.auth.csa.front.operations.blockingmessage;

import com.rssl.auth.csa.front.business.blockingrules.BlockingRules;
import com.rssl.auth.csa.front.business.blockingrules.BlockingRulesUtils;
import com.rssl.phizic.business.BusinessException;

/**
 * �������� ����������� ��������� �� ����������� �����.
 * @author mihaylov
 * @ created 21.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowBlockingMessageOperation
{
	private String blockingMessage = null;

	/**
	 * ������������� ��������
	 * @throws BusinessException
	 */
	public void initialize() throws BusinessException
	{
		BlockingRules blockingRule = BlockingRulesUtils.findBlockingRule();
		if(blockingRule != null)
			blockingMessage = blockingRule.getMessage();
	}

	/**
	 * @return ������� ����������
	 */
	public String getBlockingMessage()
	{
		return blockingMessage;
	}
}
