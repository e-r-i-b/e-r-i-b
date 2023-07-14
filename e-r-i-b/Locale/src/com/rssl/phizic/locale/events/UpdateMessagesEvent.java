package com.rssl.phizic.locale.events;

import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.events.Event;

/**
 * ����� ���������� ���������
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
	 * �����������
	 * @param type ��� ������������ �������
	 * @param locale ������������� ������
	 */
	public UpdateMessagesEvent(UpdateLocaleType type, ERIBLocale locale)
	{
		this.type = type;
		this.locale = locale;
	}

	/**
	 * @return ��� ������������ �������
	 */
	public UpdateLocaleType getType()
	{
		return type;
	}

	/**
	 * @return ������������� ������
	 */
	public String getLocaleId()
	{
		return locale.getId();
	}

	/**
	 * @return ������
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
