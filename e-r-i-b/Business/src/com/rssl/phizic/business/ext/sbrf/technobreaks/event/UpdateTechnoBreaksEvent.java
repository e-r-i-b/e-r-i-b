package com.rssl.phizic.business.ext.sbrf.technobreaks.event;

import com.rssl.phizic.events.Event;

/**
 * @author akrenev
 * @ created 20.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * событие изменения соства тех. перерывов в системе
 */

public class UpdateTechnoBreaksEvent implements Event
{
	private final String externalSystemCode;

	/**
	 * конструктор
	 * @param externalSystemCode код внешней системы для которой изменился состав тех. перерывов
	 */
	public UpdateTechnoBreaksEvent(String externalSystemCode)
	{
		this.externalSystemCode = externalSystemCode;
	}

	/**
	 * @return код внешней системы для которой изменился состав тех. перерывов
	 */
	public String getExternalSystemCode()
	{
		return externalSystemCode;
	}

	public String getStringForLog()
	{
		return getClass().getSimpleName() + " для " + getExternalSystemCode();
	}
}
