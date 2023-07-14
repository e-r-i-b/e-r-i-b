package com.rssl.phizicgate.rsretailV6r4.notification;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.notification.*;
import com.rssl.phizic.notifications.Notification;
import com.rssl.phizic.gate.notification.AccountRestChangeNotification;
import com.rssl.phizic.gate.notification.StatusDocumentChangeNotification;
import com.rssl.phizic.gate.notification.AccountRestChangeLowNotification;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizicgate.rsretailV6r4.junit.RSRetailV6r4GateTestCaseBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 25.01.2007
 * @ $Author$
 * @ $Revision$
 */

public class NotificationServiceTest extends RSRetailV6r4GateTestCaseBase
{
	public NotificationServiceTest() throws GateException
	{
	}

	public void testEmpty() throws GateException
	{

	}

	public void manualAccountNotificationTest() throws GateException, GateLogicException
	{
		String accountId = "10012632";
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		GroupResult<String, Account> accounts = bankrollService.getAccount(accountId);


		if (accounts != null)
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("account", accounts.getResult(accountId));
			NotificationService notificationService = GateSingleton.getFactory().service(NotificationService.class);
			List<Notification> restNotes = notificationService.getNotifications(AccountRestChangeNotification.class, params);
			assertNotNull(restNotes);
			restNotes = notificationService.getNotifications(AccountRestChangeLowNotification.class, params);
			assertNotNull(restNotes);
		}
	}

	public void manualStatusNotificationTest() throws GateException
	{
		Map<String, Object> params = new HashMap<String, Object>();
		NotificationService notificationService = GateSingleton.getFactory().service(NotificationService.class);
		List<Notification> restNotes = notificationService.getNotifications(StatusDocumentChangeNotification.class, params);
		assertNotNull(restNotes);
	}

	public void testDeleteGarbageNotificationTest() throws GateException
	{
		NotificationService notificationService = GateSingleton.getFactory().service(NotificationService.class);
		notificationService.deleteGarbageNotification();
	}
}
