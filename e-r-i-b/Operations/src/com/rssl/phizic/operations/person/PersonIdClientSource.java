package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author Evgrafov
 * @ created 06.02.2006
 * @ $Author: erkin $
 * @ $Revision: 48493 $
 */

public class PersonIdClientSource implements ClientSource
{
    private static final PersonService personService = new PersonService();
    private static final ClientService clientService = GateSingleton.getFactory().service(ClientService.class);

    private Client client;

    public PersonIdClientSource(Long personId) throws BusinessException
    {
        Person person = personService.findById(personId);
        if(person == null)
            throw new BusinessException("Person with id:" + personId + " not found");

        try
        {
            String clientId = person.getClientId();
            if(clientId != null)
            {
                client = clientService.getClientById(clientId);
            }
            else
                client = null;
        }
        catch (GateException e)
        {
            throw new BusinessException(e);
        }
        catch (GateLogicException e)
        {
            throw new BusinessException(e);            
        }
    }

    public Client getClient()
    {
        return client;
    }
}