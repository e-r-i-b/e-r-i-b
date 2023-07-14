package com.rssl.phizic.common.types;

import junit.framework.TestCase;
import junit.framework.Assert;

import java.util.Calendar;

/**
 * @author Kosyakov
 * @ created 31.05.2006
 * @ $Author: kosyakov $
 * @ $Revision: 1421 $
 */
public class DateSpanTest extends TestCase
{
    private static void assertDateSpanFormat ( String period, boolean good )
    {
        boolean thrown = false;
        try
        {
            new DateSpan(period);
        }
        catch (IllegalArgumentException e)
        {
            thrown = true;
        }
        Assert.assertTrue(good^thrown);
    }

    private static void assertDateSpanToDaysConvertation ( Calendar from, Calendar to, long days )
    {
		DateSpan span = new DateSpan(from,to);
	    System.out.println("from:" + String.format("%1$te.%1$tm.%1$tY",from.getTime()) );
	    System.out.println("to:" + String.format("%1$te.%1$tm.%1$tY",to.getTime()) );
	    System.out.println("days returned:"+span.getValueInDays() );
	    assertTrue("Ошибка при конвертации в дни", span.getValueInDays() == days );
    }

    private static void assertDateSpanToDaysConvertation ( String spanString, long days )
    {
		DateSpan span = new DateSpan(spanString);
	    System.out.println("span:" + spanString );
	    System.out.println("days returned:"+span.getValueInDays() );
	    assertTrue("Ошибка при конвертации в дни", span.getValueInDays() == days );
    }

    public void testConstructor ()
    {
        assertDateSpanFormat("qw-er-ty", false);
        assertDateSpanFormat("12+34-56", false);
        assertDateSpanFormat("1234567", false);
        assertDateSpanFormat("1-23-45", false);
        assertDateSpanFormat("12-3-45", false);
        assertDateSpanFormat("12-34-5", false);
        assertDateSpanFormat("123-45-67", false);
        assertDateSpanFormat("12-345-67", false);
        assertDateSpanFormat("12-34-56", false);
        assertDateSpanFormat("12-34-567", true);
    }

    public void testCalendarConstructor ()
    {
	    Calendar from = Calendar.getInstance();
	    Calendar to = Calendar.getInstance();
	    //int year, int month, int date
	    from.set(2004,10,10);
	    to.set(2004,10,11);
		assertDateSpanToDaysConvertation(from, to, 1);
	    from.set(2004,10,10);
	    to.set(2004,10,10);
		assertDateSpanToDaysConvertation(from, to, 0);
	    from.set(2008,0,31);
	    to.set(2008,2,1);
		assertDateSpanToDaysConvertation(from, to, 30);
	    from.set(2008,0,1);
	    to.set(2008,1,1);
		assertDateSpanToDaysConvertation(from, to, 31);
	    assertDateSpanToDaysConvertation("01-00-000", 365);
	    assertDateSpanToDaysConvertation("00-01-000", 30);
	    assertDateSpanToDaysConvertation("01-00-001", 366);
    }

    public void testToString ()
    {
        for (String val : new String[] {"12-34-567","00-01-001"})
        {
            DateSpan dateSpan = new DateSpan(val);
            String dateSpanStr = dateSpan.toString();
	        System.out.println(dateSpanStr);
            Assert.assertTrue(val.equals(dateSpanStr));
        }
    }
}
