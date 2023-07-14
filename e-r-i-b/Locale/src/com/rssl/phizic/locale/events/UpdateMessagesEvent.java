package com.rssl.phizic.locale.events;

import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.events.Event;

/**
 * Ивент обновления текстовок
 * @author komarov
 * @ created 22.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class UpdateMessagesEvent implements Event
{
	private final UpdateLocaleType type;
	private final ERIBLocale locale;

	/**
	 * Конструктор
	 * @param type тип обновляемого объекта
	 * @param locale идентификатор локаль
	 */
	public UpdateMessagesEvent(UpdateLocaleType type, ERIBLocale locale)
	{
		this.type = type;
		this.locale = locale;
	}

	/**
	 * @return тип обновляемого объекта
	 */
	public UpdateLocaleType getType()
	{
		return type;
	}

	/**
	 * @return идентификатор локали
	 */
	public String getLocaleId()
	{
		return locale.getId();
	}

	/**
	 * @return локаль
	 */
	public ERIBLocale getLocale()
	{
		return locale;
	}

	public String getStringForLog()
	{
		return UpdateMessagesEvent.class.getSimpleName();
	}
}
