package com.rssl.phizic.notifications.handlers;

import com.rssl.phizic.notifications.Notification;
import com.rssl.phizic.notifications.NotificationLogicException;
import com.rssl.phizic.notifications.NotificationException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author egorova
 * @ created 17.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class ChainNotificationHandler implements NotificationHandler
{
	private List<NotificationHandler> handlers = new ArrayList<NotificationHandler>();

	public void addHandler(NotificationHandler handler){
		handlers.add(handler);
	}

	public void handle(Notification event) throws NotificationLogicException, NotificationException
	{
		for(NotificationHandler handler:handlers){
			handler.handle(event);
		}
	}

	public void setParameters(Map<String, ?> params)
	{
		//throw new UnsupportedOperationException();
	}
}
