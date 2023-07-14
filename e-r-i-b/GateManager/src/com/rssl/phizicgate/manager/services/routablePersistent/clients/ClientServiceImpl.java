package com.rssl.phizicgate.manager.services.routablePersistent.clients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.objects.ClientWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.objects.OfficeWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.objects.RouteInfoReturner;
import com.rssl.phizicgate.manager.services.routablePersistent.RoutablePersistentServiceBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ClientServiceImpl extends RoutablePersistentServiceBase<ClientService> implements ClientService
{
	public ClientServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected ClientService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(ClientService.class);
	}

	public Client getClientById(String id) throws GateLogicException, GateException
	{
		RouteInfoReturner routeInfoObject = removeRouteInfoString(id);
		String ri = routeInfoObject.getRouteInfo();
		return storeRouteInfo(endService(ri).getClientById(routeInfoObject.getId()), ri);
	}

	public Client fillFullInfo(Client client) throws GateLogicException, GateException
	{
		ClientWithoutRouteInfo clientInner = removeRouteInfo(client);
		String ri = clientInner.getRouteInfo();
		return storeRouteInfo(endService(ri).fillFullInfo(clientInner), ri);
	}

	public List<Client> getClientsByTemplate(Client client, Office office, int firstResult, int maxResults) throws GateLogicException, GateException
	{
		OfficeWithoutRouteInfo officeInner = removeRouteInfo(office);
		String ri = officeInner.getRouteInfo();
		List<Client> clients = endService(ri).getClientsByTemplate(removeRouteInfo(client), officeInner, firstResult, maxResults);
		List<Client> rezult = new ArrayList<Client>();
		for (Client c : clients)
		{
			rezult.add(storeRouteInfo(c, ri));
		}
		return rezult;
	}

	public Client getClientByCardNumber(String rbTbBranchId, String cardNumber) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException();
	}
}
