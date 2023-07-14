package com.rssl.phizicgate.rsV55.subscription;

import com.rssl.phizicgate.rsV55.test.RSRetaileGateTestCaselBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.notification.NotificationSubscribeService;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 09.06.2007
 * @ $Author$
 * @ $Revision$
 */

public class NotificationSubscribeServiceTest extends RSRetaileGateTestCaselBase
{
	public void manualAccountNotificationTest() throws GateException, GateLogicException
	{
		String clientId = "45";
		BankrollService bankrollService    = GateSingleton.getFactory().service(BankrollService.class);
		ClientService clientService = GateSingleton.getFactory().service(ClientService.class);

		NotificationSubscribeService subscribeService    = GateSingleton.getFactory().service(NotificationSubscribeService.class);
		List<Account> acconts = bankrollService.getClientAccounts(clientService.getClientById(clientId));

		if(acconts != null)
		{
			subscribeService.subscribeAccount(acconts.get(0));
			subscribeService.unsubscribeAccount(acconts.get(0));
		}
	}
}
