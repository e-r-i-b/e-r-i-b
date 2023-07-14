package com.rssl.phizicgate.manager.services.persistent.notification;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.notification.NotificationSubscribeService;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

/**
 * @author Krenev
 * @ created 30.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class NotificationSubscribeServiceImpl extends PersistentServiceBase<NotificationSubscribeService> implements NotificationSubscribeService
{
	public NotificationSubscribeServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void subscribeAccount(Account account) throws GateException, GateLogicException
	{
		delegate.subscribeAccount(removeRouteInfo(account));
	}

	public void unsubscribeAccount(Account account) throws GateException, GateLogicException
	{
		delegate.unsubscribeAccount(removeRouteInfo(account));
	}
}