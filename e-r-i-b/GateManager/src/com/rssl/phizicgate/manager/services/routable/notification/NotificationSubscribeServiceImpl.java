package com.rssl.phizicgate.manager.services.routable.notification;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.notification.NotificationSubscribeService;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * @author hudyakov
 * @ created 11.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class NotificationSubscribeServiceImpl extends RoutableServiceBase implements NotificationSubscribeService
{
	public NotificationSubscribeServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void subscribeAccount(Account account) throws GateException, GateLogicException
	{
		if (!ESBHelper.isESBSupported(account.getId()))
		{
			NotificationSubscribeService delegate = getDelegateFactory(account).service(NotificationSubscribeService.class);
			delegate.subscribeAccount(account);
		}
	}

	public void unsubscribeAccount(Account account) throws GateException, GateLogicException
	{
		if (!ESBHelper.isESBSupported(account.getId()))
		{
			NotificationSubscribeService delegate = getDelegateFactory(account).service(NotificationSubscribeService.class);
			delegate.unsubscribeAccount(account);
		}
	}
}