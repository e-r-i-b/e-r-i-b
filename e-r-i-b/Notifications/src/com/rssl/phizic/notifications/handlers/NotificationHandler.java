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
	 * ���������� �������
	 * @param event �������
	 */
	void handle(Notification event) throws NotificationException, NotificationLogicException;

	/**
	 * ���������� ���������
	 * @param params ���������
	 */
	void setParameters(Map<String, ?> params);
}
