package com.rssl.phizic.operations.dictionaries.receivers;

import java.util.List;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author Kidyaev
 * @ created 29.11.2005
 * @ $Author$
 * @ $Revision$
 */
public class GetPaymentReceiverListOperationTest extends BusinessTestCaseBase
{
	public void testGetPaymentReceiverListOperation () throws Exception
	{
		createTestClientContext();

		GetPaymentReceiverListOperation operation = new GetPaymentReceiverListOperation();

		Query query = operation.createQuery("list");

		query.setParameter("name", "");
		query.setParameter("account", "");
		query.setParameter("bankName", "");

		List<PaymentReceiverBase> list = query.executeList();
		assertNotNull(list);

		query = operation.createQuery("listJ");
		list = query.executeList();
		assertNotNull(list);

		query = operation.createQuery("listI");
		list = query.executeList();
		assertNotNull(list);
    }
}
