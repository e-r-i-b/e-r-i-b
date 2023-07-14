package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

import java.util.Date;
import java.util.List;

/**
 * @author Kosyakova
 * @ created 31.01.2007
 * @ $Author$
 * @ $Revision$
 */
public class GetCommonPaymentListOperationTest  extends BusinessTestCaseBase
{
	private Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void testCommonPaymentListOperation() throws Exception
	{
		GetCommonPaymentListOperation operation = new GetCommonPaymentListOperation();
		ActivePerson testPerson = PersonServiceTest.getTestPerson();		
		Query query = operation.createQuery("list");
		query.setParameter("number", "11");
		query.setParameter("fromDate", new Date());
		query.setParameter("toDate", new Date());

		List list = query.executeList();

		log.info("Записей в списке получателей: " + list.size());
	}
}

