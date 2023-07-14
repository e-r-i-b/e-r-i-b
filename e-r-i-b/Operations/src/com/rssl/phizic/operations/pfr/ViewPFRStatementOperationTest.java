package com.rssl.phizic.operations.pfr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.pfr.PFRStatement;
import com.rssl.phizic.business.pfr.PFRStatementServiceTest;
import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author Dorzhinov
 * @ created 11.02.2011
 * @ $Author $
 * @ $Revision $
 */
public class ViewPFRStatementOperationTest extends BusinessTestCaseBase
{
	private PFRStatement statement;

	protected void setUp() throws Exception
	{
		super.setUp();
		statement = PFRStatementServiceTest.createTestPFRStatement();
	}

	protected void tearDown() throws Exception
	{
		PFRStatementServiceTest.clearTestPFRStatement();
		super.tearDown();
	}

	public void testViewPFRStatementOperation() throws BusinessException
	{
		ViewPFRStatementOperation operation = new ViewPFRStatementOperation();
		operation.initialize(statement.getClaimId());
		assertNotNull(operation.getHtml(PFRStatementBaseOperation.VIEW_MODE));
	}
}
