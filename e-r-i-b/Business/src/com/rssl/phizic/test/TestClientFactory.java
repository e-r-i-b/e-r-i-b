package com.rssl.phizic.test;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.config.ConfigurationContext;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.GateSingleton;

/**
 * @author Omeliyanchuk
 * @ created 08.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class TestClientFactory
{
	private ActivePerson testPerson;
	private static final PersonService personService = new PersonService();

	public ActivePerson getTestPerson() throws Exception
	{
		if(testPerson != null)
		{
			testPerson = (ActivePerson)personService.findById(testPerson.getId());
			if(testPerson!=null && !testPerson.getStatus().equals(Person.DELETED))
				return testPerson;
		}


		ConfigurationContext configurationContext = ConfigurationContext.getIntstance();

		if(configurationContext.getActiveConfiguration().equals("russlav"))
			testPerson = RusslavTestClientCreator.getTestPerson();

		if(configurationContext.getActiveConfiguration().equals("sbrf"))
			testPerson = SBRFTestClientCreator.getTestPerson();

		return testPerson;
	}

    public String getTestClientId() throws Exception
    {
        return getTestPerson().getClientId();
    }

    public Client getTestClient() throws Exception
    {
	    ClientService service = GateSingleton.getFactory().service(ClientService.class);
	    return service.getClientById( getTestClientId() );
    }
}
