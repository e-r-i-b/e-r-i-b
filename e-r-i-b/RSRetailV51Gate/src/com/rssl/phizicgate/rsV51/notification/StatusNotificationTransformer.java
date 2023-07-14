package com.rssl.phizicgate.rsV51.notification;

import com.rssl.phizic.notifications.Notification;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 18.04.2007
 * @ $Author$
 * @ $Revision$
 */

public class StatusNotificationTransformer implements NotificationTransformer
{
	public void transform(List<Notification> list)
	{
		for (Notification notification : list)
		{
			transfromNotification( (StatusDocumentChangeNotificationImpl) notification);
		}

	}

	private void transfromNotification(StatusDocumentChangeNotificationImpl notification)
	{
		notification.getError();
	}

}
