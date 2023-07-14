package com.rssl.phizic.test;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.business.persons.clients.PersonImportService;
import com.rssl.phizic.business.departments.DepartmentTest;
import com.rssl.phizic.common.types.client.DefaultSchemeType;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateSingleton;

/**
 * @author Omeliyanchuk
 * @ created 08.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class RusslavTestClientCreator
{
	private static final String             TEST_CLIENT_ID     = "45";

    public static ActivePerson getTestPerson()  throws Exception
    {

	    PersonService personService = new PersonService();
        PersonImportService personImportService = new PersonImportService();

	    ActivePerson person = personService.findByClientId( getTestClientId() );

	    if (person == null)
        {
            Client testClient = getTestClient( getTestClientId() );
            person = personImportService.createPerson(testClient, testClient.getId(), DepartmentTest.getTestDepartment(),"1",null, CreationType.SBOL, DefaultSchemeType.SBOL);
        }

        return person;
    }

	private  static  String getTestClientId()
	{
		return TEST_CLIENT_ID;
	}

    private  static  Client getTestClient(String clientId) throws GateException, GateLogicException
    {
	    ClientService service = GateSingleton.getFactory().service(ClientService.class);
	    return service.getClientById(clientId);
    }
}
