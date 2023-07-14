package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 06.02.2006
 * @ $Author: dorzhinov $
 * @ $Revision: 52675 $
 */
public class ListBankrollsOperation extends OperationBase implements ListEntitiesOperation
{
	private static BankrollService clientService = GateSingleton.getFactory().service(BankrollService.class);

	private Client client;

	public Client getClient()
	{
		return client;
	}

	public void initialize(ClientSource clientSource)
	{
		client = clientSource.getClient();
	}

	public List<Card> getCards() throws BusinessException, BusinessLogicException
	{
		try
		{
			return clientService.getClientCards(client);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	public List<Account> getAccouts() throws BusinessException, BusinessLogicException
	{
		try
		{
			return clientService.getClientAccounts(client);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}
}