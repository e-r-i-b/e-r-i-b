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
	 * ����������� �� ��������� ���������� ��� ������� �� ������
	 */
	void subscribeAccount(Account account)  throws GateException, GateLogicException;

	/**
	 * ���������� �� ��������� ����������
	 * @param account
	 * @throws GateException
	 */
	void unsubscribeAccount(Account account) throws GateException, GateLogicException;		
}
