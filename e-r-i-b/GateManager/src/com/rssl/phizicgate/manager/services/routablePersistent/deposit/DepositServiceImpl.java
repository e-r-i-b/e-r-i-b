package com.rssl.phizicgate.manager.services.routablePersistent.deposit;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.objects.ClientWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.objects.DepositWithoutRouteInfo;
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
public class DepositServiceImpl extends RoutablePersistentServiceBase<DepositService> implements DepositService
{
	public DepositServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected DepositService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(DepositService.class);
	}

	public List<? extends Deposit> getClientDeposits(Client client) throws GateException, GateLogicException
	{
		ClientWithoutRouteInfo clientInner = removeRouteInfo(client);
		List<? extends Deposit> deposits = endService(clientInner.getRouteInfo()).getClientDeposits(clientInner);
		List<Deposit> rezult = new ArrayList<Deposit>();
		for (Deposit deposit : deposits)
		{
			rezult.add(storeRouteInfo(deposit, clientInner.getRouteInfo()));
		}
		return rezult;
	}

	public Deposit getDepositById(String depositId) throws GateException, GateLogicException
	{
		RouteInfoReturner ri = removeRouteInfoString(depositId);
		Deposit deposit = endService(ri.getRouteInfo()).getDepositById(ri.getId());
		return storeRouteInfo(deposit, ri.getRouteInfo());
	}

	public DepositInfo getDepositInfo(Deposit deposit) throws GateException, GateLogicException
	{
		DepositWithoutRouteInfo di = removeRouteInfo(deposit);
		return endService(di.getRouteInfo()).getDepositInfo(di);
	}
}
