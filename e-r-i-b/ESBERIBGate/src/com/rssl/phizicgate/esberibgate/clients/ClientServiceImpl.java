package com.rssl.phizicgate.esberibgate.clients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.messaging.ClientResponseSerializer;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;

import java.util.List;

/**
 * Здесь в реальной ситуации может быть вызван только один метод getClientByCardNumber,
 * поэтому остальные не реализуем
 @author Pankin
 @ created 29.09.2010
 @ $Author$
 @ $Revision$
 */
public class ClientServiceImpl extends AbstractService implements ClientService
{
	private ClientRequestHelper requestHelper;
	private ClientResponseSerializer responseSerializer;

	public ClientServiceImpl(GateFactory factory) throws GateException
	{
		super(factory);

		requestHelper = new ClientRequestHelper(getFactory());
		responseSerializer = new ClientResponseSerializer();
	}

	public Client getClientById(String id) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException();
	}

	public Client fillFullInfo(Client client) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = requestHelper.createCustInqRq(client.getId(),client);
		IFXRs_Type ifxRs = getRequest(ifxRq);
		return responseSerializer.fillClient(ifxRs, client.getId(), false);
	}

	public List<Client> getClientsByTemplate(Client client, Office office, int firstResult, int maxResults) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException();
	}

	public Client getClientByCardNumber(String rbTbBranchId, String cardNumber) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = requestHelper.createCustInqRq(rbTbBranchId, cardNumber);
		IFXRs_Type ifxRs = getRequest(ifxRq);
		return responseSerializer.fillClient(ifxRs, rbTbBranchId, true);
	}
}
