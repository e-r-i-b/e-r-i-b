package com.rssl.phizicgate.rsV51.senders;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.rsV51.bankroll.AccountImpl;
import com.rssl.phizicgate.rsV51.bankroll.BankrollServiceImpl;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Krenev
 * @ created 17.08.2007
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractDocumentSender implements DocumentSender
{
	private Map<String, ?> parameters = new HashMap<String, Object>();
	private ClientService clientService;

	protected Client getOwner(GateDocument document) throws GateLogicException, GateException
	{
		if (clientService == null)
		{
			clientService = GateSingleton.getFactory().service(ClientService.class);
		}

		return clientService.getClientById(document.getClientInfo().getExternalOwnerId());
	}

	protected AccountImpl getAccount(String accountNumber, Long clientId) throws GateException
	{
		BankrollServiceImpl bankrollService = (BankrollServiceImpl)GateSingleton.getFactory().service(BankrollService.class);
		AccountImpl account = bankrollService.getClientAccountByNumber(accountNumber, Long.toString(clientId));
		if(account == null)
			throw new GateException("Не найдена информация по счету:"+ accountNumber);

		return account;
	}

	public void setParameters(Map<String, ?> params)
	{
		parameters = params;
	}

	protected Object getParameter(String name)
	{
		return parameters.get(name);
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Возможность подтверждения платежа не реализована");
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Возможность валидации платежа не реализована");
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Возможность повторной отправки платежа не реализована");
	}
}
