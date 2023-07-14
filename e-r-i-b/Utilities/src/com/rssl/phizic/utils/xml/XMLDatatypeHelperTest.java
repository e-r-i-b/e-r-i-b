package com.rssl.phizic.utils.xml;

import junit.framework.TestCase;

import java.util.Calendar;

import com.rssl.phizic.utils.DateHelper;

/**
 * @author Omeliyanchuk
 * @ created 25.08.2009
 * @ $Author$
 * @ $Revision$
 */

public class XMLDatatypeHelperTest extends TestCase
{
	/**
	 * Проверяет, что методы парсинга не реагируют, или реагируют на timeZone
	 * @throws Exception
	 */
	public void test() throws Exception
	{
		String withTimeZone = "2006-08-09+04:00";
		String withTimeZone2 = "2006-08-09+07:00";
		
		Calendar calendar1 = XMLDatatypeHelper.parseDate(withTimeZone);
		Calendar calendar2 = XMLDatatypeHelper.parseDate(withTimeZone2);
		System.out.println(String.format("Calendar1: %1$tc",calendar1));
		System.out.println(String.format("Calendar2: %1$tc",calendar2));
		System.out.println(String.format("Calendar Local: %1$tc",Calendar.getInstance()));
		assertTrue(calendar1.equals(calendar2));

		Calendar calendarTime1 = XMLDatatypeHelper.parseDateTime(withTimeZone);
		Calendar calendarTime2 = XMLDatatypeHelper.parseDateTime(withTimeZone2);
		System.out.println(String.format("CalendarTime1: %1$tc",calendarTime1));
		System.out.println(String.format("CalendarTime2: %1$tc",calendarTime2));
		assertFalse(calendarTime1.equals(calendarTime2));
	
	}
}
