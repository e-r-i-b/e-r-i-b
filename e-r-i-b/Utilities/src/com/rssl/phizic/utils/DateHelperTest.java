package com.rssl.phizic.utils;
import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 06.09.2005
 * Time: 15:35:35
 */
public class DateHelperTest extends TestCase
{
    public void testDateConversaition()
    {
        Calendar currDate = DateHelper.getCurrentDate();
        Calendar clnd = Calendar.getInstance();

        assertEquals(currDate.get(Calendar.YEAR), clnd.get(Calendar.YEAR));
        assertEquals(currDate.get(Calendar.MONTH), clnd.get(Calendar.MONTH));
        assertEquals(currDate.get(Calendar.DAY_OF_MONTH), clnd.get(Calendar.DAY_OF_MONTH));
        assertEquals(currDate.get(Calendar.HOUR_OF_DAY), 0);
        assertEquals(currDate.get(Calendar.MINUTE), 0);
        assertEquals(currDate.get(Calendar.SECOND), 0);
        assertEquals(currDate.get(Calendar.MILLISECOND), 0);

        Date date  = new Date();
        Calendar currDate2 = DateHelper.toCalendar(date);
        assertEquals(currDate2.getTimeInMillis(), date.getTime());
     }

	public void testISO8601DateFormat()
	{
		String formatedString = DateHelper.toISO8601DateFormat(new GregorianCalendar());
		assertNotNull(formatedString);
	}
}
