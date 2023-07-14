package com.rssl.auth.csa.wsclient.events;

import com.rssl.phizic.events.Event;

/**
 * @author basharin
 * @ created 02.02.2013
 * @ $Author$
 * @ $Revision$
 * событие обработки сообщения об изменении новостей на главной странице входа в систему.
 */

public class ClearCacheAuthNewsEvent implements Event
{
	public String getStringForLog()
	{
		return "ClearCacheAuthNewsEvent";
	}
}
