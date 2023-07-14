package com.rssl.phizicgate.manager.services.persistent.deposit;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krenev
 * @ created 30.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class DepositServiceImpl extends PersistentServiceBase<DepositService> implements DepositService
{
	public DepositServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<? extends Deposit> getClientDeposits(Client client) throws GateException, GateLogicException
	{
		List<? extends Deposit> deposits = delegate.getClientDeposits(removeRouteInfo(client));
		List<Deposit> rezult = new ArrayList<Deposit>();
		for (Deposit deposit : deposits)
		{
			rezult.add(storeRouteInfo(deposit));
		}
		return rezult;
	}

	public Deposit getDepositById(String depositId) throws GateException, GateLogicException
	{
		Deposit deposit = delegate.getDepositById(removeRouteInfo(depositId));
		return storeRouteInfo(deposit);
	}

	public DepositInfo getDepositInfo(Deposit deposit) throws GateException, GateLogicException
	{
		return delegate.getDepositInfo(removeRouteInfo(deposit));
	}
}
