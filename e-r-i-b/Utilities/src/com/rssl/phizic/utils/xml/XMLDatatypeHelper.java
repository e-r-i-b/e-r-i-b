package com.rssl.phizic.utils.xml;

import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Evgrafov
 * @ created 20.09.2006
 * @ $Author: osminin $
 * @ $Revision: 62199 $
 */
public final class XMLDatatypeHelper
{
	private static final DatatypeFactory dataTypeFactory = createDateTypeFactory();

	private static DatatypeFactory createDateTypeFactory()
	{
		try
		{
			return DatatypeFactory.newInstance();
		}
		catch (DatatypeConfigurationException e)
		{
			throw new RuntimeException(e);
		}
	}

	private XMLDatatypeHelper()
	{
	}

	/**
	 * Парсит строку xs:date в GregorianCalendar
	 * @param lexicalRepresentation дата в виде строки
	 * @throws IllegalArgumentException If the <code>lexicalRepresentation</code> is not a valid <code>XMLGregorianCalendar</code>.
	 * @throws NullPointerException If lexicalRepresentation is null
	 */
	public static final GregorianCalendar parseDate(String lexicalRepresentation)
	{
		return dataTypeFactory.newXMLGregorianCalendar(lexicalRepresentation).toGregorianCalendar(TimeZone.getDefault(), Locale.getDefault(),null);
	}

	/**
	 * Парсит строку xs:datetime в GregorianCalendar, т.е. с учетом времени и часового пояса.
	 * @param lexicalRepresentation дата в виде строки
	 * @throws IllegalArgumentException If the <code>lexicalRepresentation</code> is not a valid <code>XMLGregorianCalendar</code>.
	 * @throws NullPointerException If lexicalRepresentation is null
	 */
	public static final GregorianCalendar parseDateTime(String lexicalRepresentation)
	{
		return dataTypeFactory.newXMLGregorianCalendar(lexicalRepresentation).toGregorianCalendar();
	}

	/**
	 * Форматирует только дату и часовой пояс
	 * @param calendar форматируемое значение
	 * @return дата в формате YYYY-MM-DD+XX:XX (+XX:XX - смещение в относительно гринвича +03:00)
	 * @throws NullPointerException If calendar is null
	 */
	public static final String formatDate(Calendar calendar)
	{
		XMLGregorianCalendar xmlCalendar = dataTypeFactory.newXMLGregorianCalendar
				(
						calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH)+1,
						calendar.get(Calendar.DAY_OF_MONTH),
						DatatypeConstants.FIELD_UNDEFINED, // hours
						DatatypeConstants.FIELD_UNDEFINED, // minutes
						DatatypeConstants.FIELD_UNDEFINED, // seconds
						DatatypeConstants.FIELD_UNDEFINED, // millis
						calendar.get(Calendar.ZONE_OFFSET) / DateHelper.MILLISECONDS_IN_MINUTE
				);

		return xmlCalendar.toXMLFormat();
	}

	/**
	 * Форматирует Calendar как xs:date
	 * @param calendar форматируемое значение
	 * @return  строку в формате <a href="http://www.w3.org/TR/xmlschema-2/#dateTime-order">XML Schema 1.0 Part 2, Section 3.2.[7-14].1,
	 * <i>Lexical Representation</i>".</a>
	 * @throws NullPointerException If calendar is null
	 */
	public static final String formatDateTime(Calendar calendar)
	{
		XMLGregorianCalendar xmlCalendar = dataTypeFactory.newXMLGregorianCalendar
				(
						calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH)+1,
						calendar.get(Calendar.DAY_OF_MONTH),
						calendar.get(Calendar.HOUR_OF_DAY),
						calendar.get(Calendar.MINUTE),
						calendar.get(Calendar.SECOND),
						calendar.get(Calendar.MILLISECOND),
						calendar.get(Calendar.ZONE_OFFSET) / DateHelper.MILLISECONDS_IN_MINUTE
				);

		return xmlCalendar.toXMLFormat();
	}

	/**
	 * Форматирует Calendar как xs:date без timeZone
	 * @param calendar форматируемое значение
	 * @return  строку в формате "2010-05-13"
	 * @throws NullPointerException If calendar is null
	 */
	public static final String formatDateWithoutTimeZone(Calendar calendar)
	{
		XMLGregorianCalendar xmlCalendar = dataTypeFactory.newXMLGregorianCalendar
				(
						calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH)+1,
						calendar.get(Calendar.DAY_OF_MONTH),
						DatatypeConstants.FIELD_UNDEFINED, // hours
						DatatypeConstants.FIELD_UNDEFINED, // minutes
						DatatypeConstants.FIELD_UNDEFINED, // seconds
						DatatypeConstants.FIELD_UNDEFINED, // millis
						DatatypeConstants.FIELD_UNDEFINED // timeZone
				);

		return xmlCalendar.toXMLFormat();
	}

	/**
	 * Форматирует Calendar как xs:datetime без timeZone
	 * @param calendar форматируемое значение
	 * @return  строку в формате "2010-05-13T13:48:22"
	 * @throws NullPointerException If calendar is null
	 */
	public static final String formatDateTimeWithoutTimeZone(Calendar calendar)
	{
		XMLGregorianCalendar xmlCalendar = dataTypeFactory.newXMLGregorianCalendar
				(
						calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH)+1,
						calendar.get(Calendar.DAY_OF_MONTH),
						calendar.get(Calendar.HOUR_OF_DAY),
						calendar.get(Calendar.MINUTE),
						calendar.get(Calendar.SECOND),
						DatatypeConstants.FIELD_UNDEFINED, // millis
						DatatypeConstants.FIELD_UNDEFINED // timeZone
				);

		return xmlCalendar.toXMLFormat();
	}

	/**
	 * Форматирует Calendar как xs:datetime без миллисекунд
	 * @param calendar форматируемое значение
	 * @return  строку в формате "YYYY-MM-DDT00:00:00+00:00
	 * @throws NullPointerException If calendar is null
	 */
	public static final String formatDateTimeWithoutMillis(Calendar calendar)
	{
		XMLGregorianCalendar xmlCalendar = dataTypeFactory.newXMLGregorianCalendar
				(
						calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH)+1,
						calendar.get(Calendar.DAY_OF_MONTH),
						calendar.get(Calendar.HOUR_OF_DAY),
						calendar.get(Calendar.MINUTE),
						calendar.get(Calendar.SECOND),
						DatatypeConstants.FIELD_UNDEFINED, // millis
						calendar.get(Calendar.ZONE_OFFSET) / DateHelper.MILLISECONDS_IN_MINUTE
				);

		return xmlCalendar.toXMLFormat();
	}

	/**
	 * Calendar -> XMLGregorianCalendar
	 * @param calendar (never null)
	 * @return
	 */
	public static final XMLGregorianCalendar getXMLCalendar(Calendar calendar)
	{
		GregorianCalendar gregorianCalendar;
		if (calendar instanceof GregorianCalendar)
		{
			gregorianCalendar = (GregorianCalendar) calendar;
		}
		else
		{
			gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(calendar.getTime());
		}

		return dataTypeFactory.newXMLGregorianCalendar(gregorianCalendar);
	}
}