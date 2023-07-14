package com.rssl.phizicgate.rsretailV6r20.deposit;

import com.rssl.phizicgate.rsretailV6r20.junit.RSRetailV6r20GateTestCaseBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.GateSingleton;

import java.util.List;

/**
 * @author Danilov
 * @ created 19.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class DepositServiceTest extends RSRetailV6r20GateTestCaseBase
{
	private static final String ACCOUNT_ID = "10000012";
	private static final String DEPOSIT_ID = "10013454";
	private static final String CLIENT_ID = "30667";
	public void testGetDepositById() throws GateException
	{
		DepositServiceImpl depositService = new DepositServiceImpl(gateFactory);
		Deposit deposit = depositService.getDepositById(ACCOUNT_ID);
		assertNotNull(deposit);
		assertNotNull(deposit.getId());
		assertNotNull(deposit.getDescription());
		assertNotNull(deposit.getOpenDate());
		assertNotNull(deposit.getEndDate());
		assertNotNull(deposit.getAmount());
		assertNotNull(deposit.getCloseDate());
		assertNotNull(deposit.getDuration());
		assertNotNull(deposit.getState());
		assertNotNull(deposit.getInterestRate());
	}

	public void testGetClientDeposits() throws GateException, GateLogicException
	{
		DepositServiceImpl depositService = new DepositServiceImpl(gateFactory);
		ClientService clientService = GateSingleton.getFactory().service(ClientService.class);

		List<? extends Deposit> list = depositService.getClientDeposits(clientService.getClientById(CLIENT_ID));
		assertTrue(list.size()>0);
		for (Deposit deposit : list)
		{
			assertNotNull(deposit);
			assertNotNull(deposit.getId());
			assertNotNull(deposit.getDescription());
			assertNotNull(deposit.getOpenDate());
			assertNotNull(deposit.getEndDate());
			assertNotNull(deposit.getAmount());
			assertNotNull(deposit.getCloseDate());
			assertNotNull(deposit.getDuration());
			assertNotNull(deposit.getState());
			assertNotNull(deposit.getInterestRate());
		}
	}

	public void testGetDepositInfo() throws GateException
	{
		DepositServiceImpl depositService = new DepositServiceImpl(gateFactory);
		Deposit deposit = depositService.getDepositById(DEPOSIT_ID);
		DepositInfo depositInfo = depositService.getDepositInfo(deposit);
		assertNotNull(depositInfo);
		assertNotNull(depositInfo.getAccount());
		assertNotNull(depositInfo.getAgreementNumber());
		assertNotNull(depositInfo.getFinalAccounts());
		assertNotNull(depositInfo.getPercentAccount());
		assertNotNull(depositInfo.isRenewalAllowed());
		assertNotNull(depositInfo.getMinBalance());
		assertNotNull(depositInfo.getMinReplenishmentAmount());
	}
}
