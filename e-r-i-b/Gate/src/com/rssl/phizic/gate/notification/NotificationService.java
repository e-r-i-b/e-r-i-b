package com.rssl.phizic.gate.notification;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.notifications.Notification;

import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 24.01.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * интерфейс сервиса для получения оповещений.
 */
public interface NotificationService extends Service
{
	/**
	 * Получить список новых/необработанных  событий
	 * @return
	 */

	List<Notification> getAllNotifications() throws GateException;
	/**
	 * Получить список новых/необработанных  событий
	 * @param notificationType
	 * @return
	 * @throws GateException
	 */
	List<Notification> getNotifications(Class<? extends Notification> notificationType, Map<String,Object> params) throws GateException;

	/**
	 * удаление старых оповещений
	 * @throws GateException
	 */
	public void deleteGarbageNotification() throws GateException;
}
