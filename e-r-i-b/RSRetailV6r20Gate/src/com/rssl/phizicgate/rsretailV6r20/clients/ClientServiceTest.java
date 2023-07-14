package com.rssl.phizicgate.rsretailV6r20.clients;

import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.rsretailV6r20.junit.RSRetailV6r20GateTestCaseBase;

import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

/**
 * @author Kidyaev
 * @ created 03.10.2005
 * @ $Author: danilov $
 * @ $Revision$
 */

public class ClientServiceTest extends RSRetailV6r20GateTestCaseBase
{
    private ClientService service = new ClientServiceImpl();

    public ClientServiceTest(String string) throws GateException
    {
        super(string);
    }


    public void testGetClientById() throws GateException, GateLogicException
    {
        String id     = "32323";
        Client client = service.getClientById(id);

        assertNotNull("В БД нет клиента, у которого id=" + id, client);

	    Calendar birthDay = client.getBirthDay();
	    Calendar docIssueDate = null;
	    if (client.getDocuments()!=null)
	    {
	        for(ClientDocument document : client.getDocuments())
	        {
		        docIssueDate = document.getDocIssueDate();
		        break;
	        }
	    }
	    assertNotNull(birthDay);
	    Assert.assertNotNull(docIssueDate);

        service.getClientById("-1");
    }

    public void testGetClientName() throws GateException, GateLogicException
    {
        String       surName   = "Виноградова";
        String       firstName = "Татьяна";
        String       patrName  = "Анатольевна";
        List<Client> clients   = service.getClientsByTemplate(null, null, 0, 0);

        Assert.assertTrue( "В БД нет клиентов, у которых surName=" + surName
                       +  " firstName="                          + firstName
                       +  " patrName="                           + patrName
                          , clients.size() != 0);


        surName        = null;
        firstName      = null;
        patrName       = null;
        clients        = service.getClientsByTemplate(null, null, 2, 0);//выдать начиная со 2го клиента

        Assert.assertTrue( "В БД менее двух клиентов, у которых surName=" + surName
                       +  " firstName="                            + firstName
                       +  " patrName="                             + patrName
                          , clients.size() != 0);
    }
}
