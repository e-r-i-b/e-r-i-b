package com.rssl.phizic.business.ext.sbrf.technobreaks.event;

import com.rssl.phizic.events.Event;

/**
 * Событие возникающее при окончании или приостановке технического перерыва.
 *
 * @author bogdanov
 * @ created 05.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class TechnoBreakFinishEvent implements Event
{
	public String getStringForLog()
	{
		return getClass().getSimpleName();
	}
}
