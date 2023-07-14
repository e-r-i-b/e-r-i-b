package com.rssl.phizicgate.sbrf.xbank;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * @author gololobov
 * @ created 27.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class DepositServiceImpl extends AbstractService implements DepositService
{
	protected DepositServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<? extends Deposit> getClientDeposits(Client client) throws GateException, GateLogicException
	{
		return new ArrayList<Deposit>();
	}

	public Deposit getDepositById(String depositId) throws GateException, GateLogicException
	{
		return null;
	}

	public DepositInfo getDepositInfo(Deposit deposit) throws GateException, GateLogicException
	{
		return null;
	}
}
