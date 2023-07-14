package com.rssl.phizic.gate.notification;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;

/**
 * @author Omeliyanchuk
 * @ created 08.06.2007
 * @ $Author$
 * @ $Revision$
 */

public interface NotificationSubscribeService extends Service
{
	/**
	 * подписаться на получение оповещения при событии со счетом
	 */
	void subscribeAccount(Account account)  throws GateException, GateLogicException;

	/**
	 * отписаться от получения оповещений
	 * @param account
	 * @throws GateException
	 */
	void unsubscribeAccount(Account account) throws GateException, GateLogicException;		
}
