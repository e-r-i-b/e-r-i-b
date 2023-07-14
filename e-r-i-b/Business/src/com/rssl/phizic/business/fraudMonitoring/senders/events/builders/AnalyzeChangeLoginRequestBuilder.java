package com.rssl.phizic.business.fraudMonitoring.senders.events.builders;

import com.rssl.phizic.rsa.senders.types.EventsType;

/**
 * Билдер запросов по событию смены логина
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeChangeLoginRequestBuilder extends AnalyzeEventRequestBuilderBase
{
	@Override
	protected String getEventDescription()
	{
		return "Изменение логина клиента";
	}

	@Override
	protected EventsType getEventsType()
	{
		return EventsType.CHANGE_LOGIN_ID;
	}
}
