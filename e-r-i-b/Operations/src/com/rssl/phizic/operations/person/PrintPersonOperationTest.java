package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.dictionaries.PaymentReceiverJur;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.business.operations.restrictions.defaults.NullUserRestriction;
import com.rssl.phizic.business.ext.sbrf.receivers.PaymentReceiverPhizSBRF;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 26.07.2006
 * @ $Author: hudyakov $
 * @ $Revision: 15515 $
 */

public class PrintPersonOperationTest extends RSSLTestCaseBase
{
	public void test () throws Exception
	{
		ActivePerson testPerson = PersonServiceTest.getTestPerson();
		PrintPersonOperation operation = new PrintPersonOperation();
		operation.setRestriction(NullUserRestriction.INSTANCE);

		operation.setPersonId(testPerson.getId());

		List<ActivePerson> empoweredPersons = operation.createQuery("empoweredPersons").executeList();
		assertNotNull(empoweredPersons);

		List<PaymentReceiverJur> paymentReceiversJur = operation.getPaymentReceiversJur();
		assertNotNull(paymentReceiversJur);

		List<PaymentReceiverPhizSBRF> paymentReceiversPhisInternal = operation.getPaymentReceiversPhizSBRF();
		assertNotNull(paymentReceiversPhisInternal);
	}
}