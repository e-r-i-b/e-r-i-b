package com.rssl.phizicgate.rsV55.deposit;

import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizicgate.rsV55.test.RSRetaileGateTestCaselBase;
import junit.framework.Assert;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 19.05.2006
 * @ $Author: pakhomova $
 * @ $Revision: 10869 $
 */

public class DepositServiceTest extends RSRetaileGateTestCaselBase
{
	private static final ClientService clientService = GateSingleton.getFactory().service(ClientService.class);

	public DepositServiceTest(String string) throws GateException
	{
		super(string);
	}

	public void test() throws GateException, GateLogicException
	{
		DepositServiceImpl depositService = new DepositServiceImpl(gateFactory);
		List<? extends Deposit> clientDeposits = depositService.getClientDeposits(clientService.getClientById("50"));
		Assert.assertNotNull(clientDeposits);
	}
}