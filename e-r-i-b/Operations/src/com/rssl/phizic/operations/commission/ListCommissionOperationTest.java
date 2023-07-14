package com.rssl.phizic.operations.commission;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.business.commission.CommissionRule;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 12.09.2007
 * @ $Author: krenev $
 * @ $Revision: 5386 $
 */

public class ListCommissionOperationTest extends BusinessTestCaseBase
{
	public void testListCommissionOperation() throws DataAccessException
	{
		ListCommissionOperation operation = new ListCommissionOperation();
		List<CommissionRule> rules = operation.createQuery("listRules").executeList();

		assertNotNull(rules);

		List<CommissionRule> types = operation.createQuery("listTypes").executeList();

		assertNotNull(types);
	}
}
