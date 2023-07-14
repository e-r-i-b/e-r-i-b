package com.rssl.phizicgate.manager.services.persistent.clients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krenev
 * @ created 21.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class ClientServiceImpl extends PersistentServiceBase<ClientService> implements ClientService
{
	public ClientServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Client getClientById(String id) throws GateLogicException, GateException
	{
		Client client = delegate.getClientById(removeRouteInfo(id));
		return storeRouteInfo(client);
	}

	public Client fillFullInfo(Client client) throws GateLogicException, GateException
	{
		Client rezult = delegate.fillFullInfo(removeRouteInfo(client));
		return storeRouteInfo(rezult);
	}

	public List<Client> getClientsByTemplate(Client client, Office office, int firstResult, int maxResults) throws GateLogicException, GateException
	{
		List<Client> clients = delegate.getClientsByTemplate(client, removeRouteInfo(office), firstResult, maxResults);
		List<Client> rezult = new ArrayList<Client>();
		for (Client c : clients)
		{
			rezult.add(storeRouteInfo(c));
		}
		return rezult;
	}

	public Client getClientByCardNumber(String rbTbBranchId, String cardNumber) throws GateLogicException, GateException
	{
		return delegate.getClientById(cardNumber);
	}
}
