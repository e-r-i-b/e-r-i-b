package com.rssl.phizic.utils.jaxb;

import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Erkin
 * @ created 13.02.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Абстрактный JAXB-адаптер для даты в виде календаря
 */
public abstract class AbstractCalendarAdapter extends XmlAdapter<String, Calendar>
{
	public Calendar unmarshal(String dateAsString)
	{
		SimpleDateFormat dateFormat = getDateFormat();
		try
		{
			return XMLDatatypeHelper.parseDateTime(dateAsString);
		}
		catch (RuntimeException e)
		{
			throw new IllegalArgumentException("Не удалось разобрать строку: " + dateAsString + " по формату " + dateFormat.toPattern(), e);
		}
	}

	public String marshal(Calendar dateAsCalendar)
	{
		SimpleDateFormat dateFormat = getDateFormat();
		return dateFormat.format(dateAsCalendar.getTime());
	}

	protected abstract SimpleDateFormat getDateFormat();
}
