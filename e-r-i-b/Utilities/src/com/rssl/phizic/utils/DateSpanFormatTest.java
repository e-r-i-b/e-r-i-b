package com.rssl.phizic.utils;

import junit.framework.TestCase;
import com.rssl.phizic.common.types.DateSpan;

/**
 * @author Kosyakov
 * @ created 02.06.2006
 * @ $Author: kosyakov $
 * @ $Revision: 1433 $
 */
public class DateSpanFormatTest extends TestCase
{
	private String formatDateSpan (String dateSpamValue)
	{
		DateSpan dateSpan = new DateSpan(dateSpamValue);
		DateSpanFormat dateSpanFormat = new DateSpanFormat();
		String stringValue = dateSpanFormat.format(dateSpan);
		System.out.println(dateSpamValue+" = ["+stringValue+"]");
		return stringValue;
	}

	public void testFormat ()
	{
		String stringValue = formatDateSpan("00-12-000");
		assertNotNull(stringValue);
	}
}
