package com.rssl.phizic.notifications.handlers;

import com.rssl.phizic.notifications.Notification;
import com.rssl.phizic.notifications.NotificationException;
import com.rssl.phizic.notifications.NotificationLogicException;

import java.util.Map;
import java.util.List;

/**
 * @author Krenev
 * @ created 13.05.2008
 * @ $Author$
 * @ $Revision$
 */
public interface NotificationHandler
{
	/**
	 * Обработать событие
	 * @param event событие
	 */
	void handle(Notification event) throws NotificationException, NotificationLogicException;

	/**
	 * Установить параметры
	 * @param params параметры
	 */
	void setParameters(Map<String, ?> params);
}
