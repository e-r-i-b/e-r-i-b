package com.rssl.phizicgate.manager.services.routablePersistent.notification;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.notification.NotificationSubscribeService;
import com.rssl.phizicgate.manager.services.objects.AccountWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.routablePersistent.RoutablePersistentServiceBase;

/**
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class NotificationSubscribeServiceImpl extends RoutablePersistentServiceBase<NotificationSubscribeService> implements NotificationSubscribeService
{
	public NotificationSubscribeServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected NotificationSubscribeService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(NotificationSubscribeService.class);
	}

	public void subscribeAccount(Account account) throws GateException, GateLogicException
	{
		AccountWithoutRouteInfo ai = removeRouteInfo(account);
		endService(ai.getRouteInfo()).subscribeAccount(ai);
	}

	public void unsubscribeAccount(Account account) throws GateException, GateLogicException
	{
		AccountWithoutRouteInfo ai = removeRouteInfo(account);
		endService(ai.getRouteInfo()).unsubscribeAccount(ai);
	}
}