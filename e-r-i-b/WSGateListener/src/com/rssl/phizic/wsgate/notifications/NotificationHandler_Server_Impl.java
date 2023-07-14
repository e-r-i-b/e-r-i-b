package com.rssl.phizic.wsgate.notifications;

import com.rssl.phizic.wsgate.notifications.generated.NotificationHandler;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.notifications.impl.BusinessNotification;
import com.rssl.phizic.notifications.service.NotificationService;
import com.rssl.phizic.notifications.BusinessNotificationException;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author hudyakov
 * @ created 01.10.2009
 * @ $Author$
 * @ $Revision$
 */

public class NotificationHandler_Server_Impl implements NotificationHandler
{
	private static final String PREFIX_CLASS  = "com.rssl.phizic.notifications.impl.";
	private static final String POSTFIX_CLASS = "Impl";

	public void handle(List list_1) throws RemoteException
	{
		for (com.rssl.phizic.wsgate.notifications.generated.Notification notification_1 :
								(List<com.rssl.phizic.wsgate.notifications.generated.Notification>) list_1)
		{
			com.rssl.phizic.wsgate.types.NotificationImpl notification = new com.rssl.phizic.wsgate.types.NotificationImpl();

			try
			{
				BeanHelper.copyPropertiesWithDifferentTypes(notification, notification_1, TypesCorrelation.types);
				//создаем экземпляр бизнес оповещения
				String className = PREFIX_CLASS + notification.getType().getSimpleName() + POSTFIX_CLASS;

				Class<BusinessNotification>  loaderClass = ClassHelper.loadClass(className);
				BusinessNotification event = loaderClass.newInstance();
				BeanHelper.copyPropertiesFull(event, notification);

				NotificationService notificationService = new NotificationService();
				notificationService.save(event);
			}
			catch (BusinessNotificationException e)
			{
				throw new RemoteException(e.getMessage(), e);
			}
			catch (Exception e)
			{
				throw new RemoteException(e.getMessage(), e);
			}
		}
	}
}
