package com.rssl.phizic.operations.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.payments.forms.PaymentFormServiceTest;
import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author Evgrafov
 * @ created 23.12.2005
 * @ $Author: kosyakov $
 * @ $Revision: 3395 $
 */

public class DownloadFormsOperationTest extends BusinessTestCaseBase
{
	public void testDownloadPaymentForm() throws BusinessException
	{
		DownloadFormsOperation operation = new DownloadFormsOperation();

		operation.initialize(PaymentFormServiceTest.getSomePaymentFormName());

		assertNotNull(operation.getForm());
		assertNotNull(operation.getHtmlForm());
		assertNotNull(operation.getXmlForm());
		assertNotNull(operation.getListForm());
		assertNotNull(operation.getHtmlListFilterForm());
		assertNotNull(operation.getHtmlListForm());
	}
}
