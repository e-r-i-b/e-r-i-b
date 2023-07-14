package com.rssl.phizic.operations.deposits;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.restrictions.defaults.PersonDepartmentDepositProductRestriction;

import javax.xml.transform.Source;

/**
 * @author Evgrafov
 * @ created 15.05.2006
 * @ $Author: pankin $
 * @ $Revision: 22102 $
 */

public class GetDepositProductsListOperationTest extends BusinessTestCaseBase
{
	public void testGetDepositListOperation() throws BusinessException
	{
		GetDepositProductsListOperation operation = new GetDepositProductsListOperation();
		operation.setRestriction(PersonDepartmentDepositProductRestriction.INSTANCE);
		Source listSource = operation.getListSource("1");

		assertNotNull(listSource);
	}
}
