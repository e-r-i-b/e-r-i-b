package com.rssl.phizic.business.persons.clients;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.business.departments.DepartmentTest;
import com.rssl.phizic.common.types.client.DefaultSchemeType;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.test.BusinessTestCaseBase;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 10.10.2005 Time: 16:49:01 */
public class PersonImportServiceTest extends BusinessTestCaseBase
{
	protected void setUp() throws Exception
	{
		super.setUp();

		initializeRSGate();
	}

	public void testPersonImportService() throws Exception
    {
        PersonImportService importService = new PersonImportService();
        PersonService personService = new PersonService();

        Client client = getTestClient();

	    if (client != null)
	    {
		    ActivePerson person = importService.findByClientId(client.getId());

		    if(person != null)
		        personService.markDeleted(person);

		    person = importService.createPerson(client, client.getId(), DepartmentTest.getTestDepartment(),"1",null, CreationType.SBOL, DefaultSchemeType.SBOL);

		    assertNotNull(person);
		    assertNotNull(importService.findByClientId(person.getClientId()));

		    personService.markDeleted(person);
	    }
    }
}
