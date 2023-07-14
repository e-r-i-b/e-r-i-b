package com.rssl.phizic.business.persons.xmlserialize;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.utils.test.annotation.IncludeTest;

/**
 * @author Omeliyanchuk
 * @ created 27.09.2006
 * @ $Author$
 * @ $Revision$
 */
@IncludeTest (configurations = "sbrf")
public class PersonXMLSerializerTest extends BusinessTestCaseBase
{
	public void testCreateXmlTest() throws Exception
	{
		PersonService personService = new PersonService();
		SimpleService simpleService = new SimpleService();
		PersonXMLSerializerService serializeService = new PersonXMLSerializerService();

		ActivePerson testPerson = PersonServiceTest.getTestPerson();

		String xmlString = PersonXMLSerializer.getPersonXMLString(testPerson, null);

		XMLPersonRepresentation represent = PersonXMLSerializer.createXMLRepresentation(testPerson);

		simpleService.addOrUpdate(represent);

		XMLPersonRepresentation represent1 = serializeService.findRepresentationByUserId(testPerson.getLogin().getUserId());

		assertNotNull(represent1);

		PersonXMLComparator comparator = new PersonXMLComparator();

		PersonChanges change = comparator.compare(testPerson.getLogin());

		assertNotNull(change);
		assertFalse(change.getIsChanged());

	}

	public void testChangesTestGeneral() throws Exception
	{
		SimpleService simpleService = new SimpleService();

		ActivePerson testPerson = PersonServiceTest.getTestPerson();

		XMLPersonRepresentation represent = PersonXMLSerializer.createXMLRepresentation(testPerson);

		simpleService.addOrUpdate(represent);

		String agrNumber = testPerson.getAgreementNumber();
		if ((agrNumber == null) || (agrNumber.length() < 5)) agrNumber = "234324234";
		else
		{
			String tmp = agrNumber.substring(agrNumber.length() - 2, agrNumber.length());
			agrNumber = agrNumber.substring(0, agrNumber.length() - 2) + tmp;
		}
		testPerson.setAgreementNumber(agrNumber);
		simpleService.addOrUpdate(testPerson);

		PersonXMLComparator comparator = new PersonXMLComparator();
		PersonChanges change = comparator.compare(testPerson.getLogin());

		assertNotNull(change);
		assertTrue(change.getIsChanged());
	}

	/*
	 public static void testChangesTestReciverJur() throws Exception
	 {
		 ActivePerson testPerson = PersonServiceTest.getTestPerson();

		 XMLPersonRepresentation represent = PersonXMLSerializer.createXMLRepresentation(testPerson);

		 simpleService.addOrUpdate(represent);

		 PaymentReceiverService paymentReceiverService = new PaymentReceiverService();

		 PaymentReceiverJur paymentReceiver = new PaymentReceiverJur();
		 Login login = testPerson.getLogin();
		 paymentReceiver.setLogin(login);
		 paymentReceiver.setAccount("343432432462347");
		 //bic и corAccc должны быть существубщего банка
		 paymentReceiver.setBIC("044583571");
		 paymentReceiver.setCorrespondentAccount("30101810400000000571");
		 paymentReceiver.setKPP("47564");
		 paymentReceiver.setINN("234234264");
		 paymentReceiver.setName("Пупов Петя");
		 paymentReceiver.setAlias("TeastReciever");


		 paymentReceiverService.add(paymentReceiver);

		 PersonXMLComparator comparator = new PersonXMLComparator();
		 PersonChanges change = comparator.compare(testPerson.getLogin().getUserId());

		 paymentReceiverService.remove(paymentReceiver);

		 assertNotNull(change);
		 assertTrue(change.getReceiverJurAdded().size()!=0);
	 }
 */

	public void testChangesTestAccount() throws Exception
	{
		SimpleService simpleService = new SimpleService();

		ActivePerson testPerson = PersonServiceTest.getTestPerson();

		XMLPersonRepresentation represent = PersonXMLSerializer.createXMLRepresentation(testPerson);

		String xmlStr = represent.getXMLString();
		String dep = "<deposits>";
		int pos = xmlStr.indexOf(dep);
		if (pos == -1)
		{
			String per = "<personFullInfo>";
			pos = xmlStr.indexOf(per);
			String last = xmlStr.substring(pos + per.length());
			String begin = xmlStr.substring(0, pos + per.length());
			String res = begin + "<deposits><deposit><account>32423423</account></deposit></deposits>" + last;
			represent.setXMLString(res);
		}
		else
		{
			String last = xmlStr.substring(pos + dep.length());
			String begin = xmlStr.substring(0, pos + dep.length());
			String res = begin + "<deposit><account>32423423</account></deposit>" + last;
			represent.setXMLString(res);
		}

		simpleService.addOrUpdate(represent);

		PersonXMLComparator comparator = new PersonXMLComparator();
		PersonChanges change = comparator.compare(testPerson.getLogin());

		assertNotNull(change);
		assertTrue(change.getAccountDeleted().size() != 0);
	}
}
