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
 * ��������� ������� ��� ��������� ����������.
 */
public interface NotificationService extends Service
{
	/**
	 * �������� ������ �����/��������������  �������
	 * @return
	 */

	List<Notification> getAllNotifications() throws GateException;
	/**
	 * �������� ������ �����/��������������  �������
	 * @param notificationType
	 * @return
	 * @throws GateException
	 */
	List<Notification> getNotifications(Class<? extends Notification> notificationType, Map<String,Object> params) throws GateException;

	/**
	 * �������� ������ ����������
	 * @throws GateException
	 */
	public void deleteGarbageNotification() throws GateException;
}
