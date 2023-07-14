package com.rssl.phizic.business.persons.xmlserialize;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.dictionaries.PaymentReceiverJur;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.business.ext.sbrf.receivers.PaymentReceiverPhizSBRF;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.test.annotation.IncludeTest;
import org.w3c.dom.Document;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Omeliyanchuk
 * @ created 12.10.2006
 * @ $Author$
 * @ $Revision$
 */
@IncludeTest(configurations = "sbrf")
public class CODRequestHelperTest  extends BusinessTestCaseBase
{
	public void testAccounts() throws Exception
	{
		PersonChanges ch = new PersonChanges();
		ch.addAccountToAdd("12345678901234567890");

		ActivePerson 	testPerson = PersonServiceTest.getTestPerson();
		Document doc = CODRequestHelper.buildClientUpdateRequest(testPerson, ch,null);

		WebBankServiceFacade webBankServiceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		webBankServiceFacade.sendOnlineMessage(doc, null);
	}

	public void testAllExceptEmpowered() throws Exception
	{
		ActivePerson 	testPerson = PersonServiceTest.getTestPerson();
		PersonChanges ch = new PersonChanges();
		Login login = testPerson.getLogin();

		ch.addAccountToAdd("12345678901234567890");
		ch.addAccountToDelete("12345678901234567890");
		ch.addFeeAccountToAdd("12345678901234567890");
		ch.addFeeAccountToDelete("12345678901234567890");

		Document doc = CODRequestHelper.buildClientUpdateRequest(testPerson, ch, null);

		WebBankServiceFacade webBankServiceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		webBankServiceFacade.sendOnlineMessage(doc, null);
	}

	public void testEmpowered() throws Exception
	{
		ActivePerson 	testPerson = PersonServiceTest.getTestPerson();
		PersonChanges ch = new PersonChanges();

		ch.addEmpoweredPersonAdded(testPerson.getClientId());
		ch.addEmpoweredPersonDeleted(testPerson);
		ch.addEmpoweredPersonModified(testPerson.getClientId());

		Document doc = CODRequestHelper.buildClientUpdateRequest(testPerson, ch, null);

		WebBankServiceFacade webBankServiceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		webBankServiceFacade.sendOnlineMessage(doc, null);
	}
}
