package com.rssl.phizic.config.events;

/**
 * событие обновления настроек ЦСА.
 *
 * @author bogdanov
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class RefreshCsaConfigEvent extends RefreshPhizIcConfigEvent
{
	public String getStringForLog()
	{
		return "RefreshCsaConfigEvent";
	}
}
