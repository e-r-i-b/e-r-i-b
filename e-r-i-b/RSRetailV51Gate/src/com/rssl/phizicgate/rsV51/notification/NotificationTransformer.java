package com.rssl.phizicgate.rsV51.notification;

import com.rssl.phizic.notifications.Notification;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 18.04.2007
 * @ $Author$
 * @ $Revision$
 */

public interface NotificationTransformer
{
	void transform(List<Notification> list);
}
