package com.rssl.phizic.business.persons;

import com.rssl.phizic.auth.CheckLoginTest;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentTest;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.person.Address;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.person.PersonDocument;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 07.09.2005
 * Time: 16:50:12
 */
@SuppressWarnings({"JavaDoc"})
public class PersonServiceTest extends BusinessTestCaseBase
{
	private final static PersonService personService = new PersonService();

	private static final String PASSPORT_NUMBER = "555555";
	private static final String PASSPORT_SERIES = "7777";

	public void testGetAllPersons () throws BusinessException
	{
		List<ActivePerson> persons = personService.getAll();
		assertNotNull("Если нет пользователей должен возвращаться пустой список!", persons);
	}

	public static ActivePerson getTestPerson () throws Exception
	{
		// always use new person

		Login testLogin = CheckLoginTest.getTestLogin();
		ActivePerson testPerson = personService.findByLoginId(testLogin.getId());
        if (testPerson!=null)
		{
			personService.markDeleted(testPerson);
			testLogin = CheckLoginTest.getTestLogin();
		}

		ActivePerson newPerson = new ActivePerson();
		initializeTestPerson(newPerson);

		newPerson.setLogin(testLogin);
		personService.add(newPerson);

		return newPerson;
	}
	private static Address createTestAddress () throws BusinessException
	{
		Address tempAddress = new Address();
		tempAddress.setPostalCode("162355");
		tempAddress.setProvince("Вологодская");
		tempAddress.setDistrict("Вологодский");
		tempAddress.setCity("Вологда");
		tempAddress.setStreet("Герцена");
		tempAddress.setHouse("1");
		tempAddress.setBuilding("а");
		tempAddress.setFlat("1");
		return tempAddress;
	}

	public static void initializeTestPerson ( ActivePerson person ) throws Exception
    {
        person.setClientId( new RandomGUID().getStringValue() );
        person.setFirstName("Петр");
        person.setPatrName("Петрович");
        person.setSurName("Петров");
        person.setBirthDay(DateHelper.getCurrentDate());
        person.setBirthPlace("г. Какойнибудь");

	    Set<PersonDocument> personDocuments = new HashSet<PersonDocument>();
	    PersonDocument document = new PersonDocumentImpl();
	    document.setDocumentNumber(PASSPORT_NUMBER);
	    document.setDocumentSeries(PASSPORT_SERIES);
	    document.setDocumentIssueDate(DateHelper.toCalendar(new Date()));
	    document.setDocumentIssueBy("ОВД г. Какойнибудь");
	    document.setDocumentIssueByCode("№");
	    document.setDocumentType(PersonDocumentType.REGULAR_PASSPORT_RF);
	    personDocuments.add(document);
	    person.setPersonDocuments(personDocuments);

        person.setRegistrationAddress(createTestAddress());
        person.setTrustingPersonId(null);
        person.setResidenceAddress(createTestAddress());
        person.setMobileOperator("MTS");
        person.setAgreementNumber("1");
        person.setAgreementDate(new GregorianCalendar());
        person.setServiceInsertionDate(new GregorianCalendar());
        person.setGender("M");
//        person.setIdentityTypeName("IdentityTypeName");
        person.setCitizenship("РФ");
        person.setProlongationRejectionDate(new GregorianCalendar());        
        person.setDepartmentId(DepartmentTest.getTestDepartment().getId());
	    person.setCreationType(CreationType.SBOL);
    }
}
