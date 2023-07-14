package com.rssl.phizic.notifications.handlers;

import com.rssl.phizic.notifications.Notification;

import java.util.Map;
import java.util.List;

/**
 * @author krenev
 * @ created 17.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class NullNotificationHandler implements NotificationHandler
{
	private static NotificationHandler instatnce = new NullNotificationHandler();

	public void handle(Notification event)
	{
		//nothing to do
	}

	public void setParameters(Map<String, ?> params)
	{
		//nothing to do
	}

	public static NotificationHandler getInstatnce()
	{
		return instatnce;
	}
}
