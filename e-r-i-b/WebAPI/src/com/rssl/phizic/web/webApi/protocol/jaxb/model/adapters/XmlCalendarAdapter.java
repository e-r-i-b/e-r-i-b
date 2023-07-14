package com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Адаптер для преобразования объектов типа Calendar
 *
 * @author Balovtsev
 * @since 30.04.2014
 */
public class XmlCalendarAdapter extends XmlAdapter<String, Calendar>
{
	protected static final String DATE_FORMAT = "dd.MM.yyyy";
	protected static final String DATE_TIME_FORMAT = "dd.MM.yyyy'T'HH:mm:ss";

	@Override
	public Calendar unmarshal(String value) throws Exception
	{
		DateFormat format = new SimpleDateFormat(DATE_FORMAT);
		format.parse(value);
		return format.getCalendar();
	}

	@Override
	public String marshal(Calendar value) throws Exception
	{
		DateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
		return format.format(value.getTime());
	}
}
