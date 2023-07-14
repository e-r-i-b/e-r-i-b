package com.rssl.phizic.business.ext.sbrf.technobreaks;

import com.rssl.phizic.business.ext.sbrf.technobreaks.event.TechnoBreakFinishEvent;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.events.EventHandler;

import java.util.Calendar;

/**
 * Хендлер, обрабатывающий событие TechnoBreakFinishEvent.
 *
 * @author bogdanov
 * @ created 04.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class TechnoBreakFinishHandler implements EventHandler<TechnoBreakFinishEvent>
{
	public void process(TechnoBreakFinishEvent event) throws Exception
	{
		PersonDataProvider personDataProvider = PersonContext.getPersonDataProvider(Application.PhizIC);
		if (personDataProvider != null)
		{
			personDataProvider.setNextUpdateDate(Calendar.getInstance());
		}
	}
}
