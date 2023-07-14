package com.rssl.phizicgate.manager.services.routable.deposit;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;

import java.util.List;

/**
 * @author hudyakov
 * @ created 11.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class DepositServiceImpl extends RoutableServiceBase implements DepositService
{
	public DepositServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<? extends Deposit> getClientDeposits(Client client) throws GateException, GateLogicException
	{
		DepositService delegate = getDelegateFactory(client).service(DepositService.class);
		return delegate.getClientDeposits(client);
	}

	public Deposit getDepositById(String depositId) throws GateException, GateLogicException
	{
		DepositService delegate = getDelegateFactory(depositId).service(DepositService.class);
		return delegate.getDepositById(depositId);
	}

	public DepositInfo getDepositInfo(Deposit deposit) throws GateException, GateLogicException
	{
		DepositService delegate = getDelegateFactory(deposit).service(DepositService.class);
		return delegate.getDepositInfo(deposit);
	}
}
