package com.rssl.phizic.wsgate.notifications;

import java.util.Map;
import java.util.HashMap;

/**
 * @author hudyakov
 * @ created 01.10.2009
 * @ $Author$
 * @ $Revision$
 */

public class TypesCorrelation
{
	public static Map<Class, Class> types = new HashMap<Class, Class>();

	static
	{
		types.put(com.rssl.phizic.wsgate.notifications.generated.Notification.class, com.rssl.phizic.wsgate.types.NotificationImpl.class);
	}
}
