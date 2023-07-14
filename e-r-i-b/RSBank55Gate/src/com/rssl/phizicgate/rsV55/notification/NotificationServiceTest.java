package com.rssl.phizicgate.rsV55.notification;

import com.rssl.phizicgate.rsV55.test.RSRetaileGateTestCaselBase;
import com.rssl.phizic.gate.notification.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.notifications.Notification;
import com.rssl.phizic.gate.notification.AccountRestChangeNotification;
import com.rssl.phizic.gate.notification.StatusDocumentChangeNotification;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Omeliyanchuk
 * @ created 25.01.2007
 * @ $Author$
 * @ $Revision$
 */

public class NotificationServiceTest extends RSRetaileGateTestCaselBase
{
	public NotificationServiceTest() throws GateException
	{
	}

	public void testEmpty() throws GateException
	{
		
	}

	public void manualAccountNotificationTest() throws GateException, GateLogicException
	{
		String clientId = "45";
		BankrollService bankrollService    = GateSingleton.getFactory().service(BankrollService.class);
		ClientService clientService = GateSingleton.getFactory().service(ClientService.class);
		List<Account> acconts = bankrollService.getClientAccounts(clientService.getClientById(clientId));

		if(acconts != null)
		{
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("account", acconts.get(0));
			NotificationService notificationService = GateSingleton.getFactory().service(NotificationService.class);
			List<Notification> restNotes = notificationService.getNotifications(AccountRestChangeNotification.class, params);
		}
	}

	public void manualStatusNotificationTest() throws GateException
	{
		Map<String,Object> params = new HashMap<String, Object>();
		NotificationService notificationService = GateSingleton.getFactory().service(NotificationService.class);
		List<Notification> restNotes = notificationService.getNotifications(StatusDocumentChangeNotification.class, params);
	}

	public void testDeleteGarbageNotificationTest() throws GateException
	{
		NotificationService notificationService = GateSingleton.getFactory().service(NotificationService.class);
		notificationService.deleteGarbageNotification();
	}
}
