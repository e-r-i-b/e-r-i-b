package com.rssl.phizicgate.rsbankV50.clients;

import com.rssl.phizicgate.rsbankV50.junit.RSBankGateTestCaselBase;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * @author Roshka
 * @ created 01.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class ClientServiceTest extends RSBankGateTestCaselBase
{
	private ClientService service = new ClientBankServiceImpl();

	public ClientServiceTest(String string) throws GateException
    {
        super(string);
    }

    public void testGetClientById() throws GateException, GateLogicException
    {
        String id     = "10";
        Client client = service.getClientById(id);

        assertNotNull("В БД нет клиента, у которого id=" + id, client);

        service.getClientById("-1");
    }

    public void testGetClientName() throws GateException, GateLogicException
    {
        String       surName   = "Новиков";
        String       firstName = "Антон";
        String       patrName  = "Сергеевич";
	    //todo maybe we have to use some real client and office here...
        List<Client> clients   = service.getClientsByTemplate(null, null, 0, 0);

        assertTrue( "В БД нет клиентов, у которых surName=" + surName
                       +  " firstName="                          + firstName
                       +  " patrName="                           + patrName
                          , clients.size() != 0);

	    //todo - use something more sensible here instead nulls again

        surName        = null;
        firstName      = null;
        patrName       = null;
        clients        = service.getClientsByTemplate(null, null, 2, 0);//выдать начиная со 2го клиента

        assertTrue( "В БД менее двух клиентов, у которых surName=" + surName
                       +  " firstName="                            + firstName
                       +  " patrName="                             + patrName
                          , clients.size() != 0);
    }
}
