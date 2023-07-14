package com.rssl.phizicgate.manager.services.routable.clients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;

import java.util.List;

/**
 * @author hudyakov
 * @ created 09.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class ClientServiceImpl extends RoutableServiceBase implements ClientService
{
	private ClientService businessDelegate;
	private ClientService gateDelegate;

	public ClientServiceImpl(GateFactory factory)
	{
		super(factory);
		businessDelegate = (ClientService) getDelegate(ClientService.class.getName() + BUSINESS_DELEGATE_KEY);
		gateDelegate = (ClientService) getDelegate(ClientService.class.getName() + GATE_DELEGATE_KEY);
	}

	public Client getClientById(String id) throws GateLogicException, GateException
	{
		GateInfoService gateInfoService = getDelegateFactory(id).service(GateInfoService.class);
		if (gateInfoService.isClientImportEnable(null))
		{
			ClientService delegate = getDelegateFactory(id).service(ClientService.class);
			return delegate.getClientById(id);
		}
		else
		{
			return businessDelegate.getClientById(id);
		}
	}

	public Client fillFullInfo(Client client) throws GateLogicException, GateException
	{
		//todo ENH025292: Вынести работу с CEBDO в отдельный гейтовый сервис  
		return gateDelegate.fillFullInfo(client);
	}

	public Client getClientByCardNumber(String rbTbBranchId, String cardNumber) throws GateLogicException, GateException
	{
		return gateDelegate.getClientByCardNumber(rbTbBranchId, cardNumber);
	}

	public List<Client> getClientsByTemplate(Client client, Office office, int firstResult, int maxResults) throws GateLogicException, GateException
	{
		GateInfoService gateInfoService = getDelegateFactory(office).service(GateInfoService.class);
		if (gateInfoService.isClientImportEnable(null))
		{
			ClientService delegate = getDelegateFactory(office).service(ClientService.class);
			return delegate.getClientsByTemplate(client, office, firstResult, maxResults);
		}
		else
		{
			return businessDelegate.getClientsByTemplate(client, office, firstResult, maxResults);
		}
	}
}
