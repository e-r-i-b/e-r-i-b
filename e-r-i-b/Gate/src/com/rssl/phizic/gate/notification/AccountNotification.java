package com.rssl.phizic.gate.notification;

import com.rssl.phizic.notifications.Notification;

/**
 * @author Krenev
 * @ created 15.05.2008
 * @ $Author$
 * @ $Revision$
 */
public interface AccountNotification extends Notification
{
	/**
	 * @return номер номер счета, по которому получено оповещение.
	 */
	public String getAccountNumber();
}
