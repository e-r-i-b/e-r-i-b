package com.rssl.phizicgate.mock.deposit;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Omeliyanchuk
 * @ created 24.03.2008
 * @ $Author$
 * @ $Revision$
 */
public class MockDepositService extends AbstractService implements DepositService
{

	public MockDepositService(GateFactory factory)
	{
		super(factory);
	}

	public List<? extends Deposit> getClientDeposits(Client client) throws GateException
	{
		return new ArrayList<Deposit>();
	}

	public Deposit getDepositById(String depositId) throws GateException
	{
		return null;
	}

	public DepositInfo getDepositInfo(Deposit deposit) throws GateException
	{
		return null;
	}
}
