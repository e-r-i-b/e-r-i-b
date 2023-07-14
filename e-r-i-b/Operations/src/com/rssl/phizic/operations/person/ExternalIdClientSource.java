package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author Evgrafov
 * @ created 06.02.2006
 * @ $Author: Kidyaev $
 * @ $Revision: 2220 $
 */

public class ExternalIdClientSource implements ClientSource
{
    private static final ClientService clientService = GateSingleton.getFactory().service(ClientService.class);

    private Client client;

    public ExternalIdClientSource(String clientId) throws BusinessException
    {
        try
        {
            client = clientService.getClientById(clientId);
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