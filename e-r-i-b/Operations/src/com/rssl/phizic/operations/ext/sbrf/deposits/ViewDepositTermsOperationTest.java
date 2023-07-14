package com.rssl.phizic.operations.ext.sbrf.deposits;

import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author Pankin
 * @ created 03.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewDepositTermsOperationTest extends BusinessTestCaseBase
{
	public void testGetHtml() throws Exception
	{
		ViewDepositTermsOperation operation = new ViewDepositTermsOperation();
		operation.initialize(Long.valueOf("14102"));
		String html = operation.getHtml(null, false);
		return;
	}
}
