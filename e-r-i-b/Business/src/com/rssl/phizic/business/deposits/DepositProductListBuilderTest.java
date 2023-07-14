package com.rssl.phizic.business.deposits;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.BusinessException;

/**
 * @author Evgrafov
 * @ created 09.04.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 3965 $
 */

public class DepositProductListBuilderTest extends BusinessTestCaseBase
{
	public void testDepositProductListBuilder() throws BusinessException
	{
		DepositProductListBuilder listBuilder = new DepositProductListBuilder();

		listBuilder.build();
	}
}
