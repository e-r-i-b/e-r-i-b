package com.rssl.phizicgate.mock.notification;

import com.rssl.phizic.gate.notification.NotificationSubscribeService;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.impl.AbstractService;

/**
 * @author Omeliyanchuk
 * @ created 24.03.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Заглушка, данный гейт не требует подписки на оповещения.
 */
public class MockNotificationSubscribeService extends AbstractService implements NotificationSubscribeService
{

	public MockNotificationSubscribeService(GateFactory factory)
	{
		super(factory);
	}

	public void subscribeAccount(Account account) throws GateException
	{
		return;
	}

	public void unsubscribeAccount(Account account) throws GateException
	{
		return;
	}
}
