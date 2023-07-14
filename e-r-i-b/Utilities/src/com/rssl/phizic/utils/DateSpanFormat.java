package com.rssl.phizic.utils;

import java.text.*;
import java.util.Calendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rssl.phizic.common.types.DateSpan;

/**
 * @author Kosyakov
 * @ created 31.05.2006
 * @ $Author: egorova $
 * @ $Revision: 16364 $
 */
public class DateSpanFormat extends Format
{
	private static final Log log = LogFactory.getLog(DateSpanFormat.class);

	private static final String[] days = {"день","дн€","дней"};
	private static final String[] months = {"мес€ц","мес€ца","мес€цев"};
	private static final String[] years = {"год","года","лет"};
	private static final int[] indexes = {2,0,1,1,1,2,2,2,2,2};

	public static final int MILLISECONDS_IN_DAYS = 1000*60*60*24;

	static private String formatPart ( int part, String[] desc )
	{
		if (part==0)
		{
			return "";
		}
		int index = Math.floor((part%100)/10)==1 ? 0 : part%10;
		return Integer.toString(part)+" "+desc[indexes[index]];
	}

	public StringBuffer format ( Object obj, StringBuffer toAppendTo, FieldPosition pos )
	{
		//TODO: разобратьс€ с FieldPosition (пока не используетс€)
		if (obj instanceof DateSpan)
		{
			String s = format((DateSpan)obj);
			return toAppendTo.append(s);
		}
		else
		{
			throw new IllegalArgumentException("Cannot format given Object as a DateSpan");
		}
	}

	public Object parseObject ( String source, ParsePosition pos )
	{
		throw new UnsupportedOperationException();
	}

	static public String format ( DateSpan dateSpan )
	{
		try
		{
			String y = formatPart(dateSpan.getYears(), years);
			String m = formatPart(dateSpan.getMonths(), months);
			String d = formatPart(dateSpan.getDays(), days);
			return (y+" "+m+" "+d).trim();
		}
		catch (Exception e)
		{
			log.error("ќшибка преобразовани€ даты в строку", e);
			return "";	
		}
	}

	public static String periodInDays(Calendar fromDate, Calendar toDate)
	{
		try
		{
			long diff = toDate.getTimeInMillis() - fromDate.getTimeInMillis();
			int period = (int)(diff/MILLISECONDS_IN_DAYS);
			//TODO уточнить, где должна обрабатыватьс€ подобна€ ситуаци€
			if(period<0)
				return "дата закрыти€ меньше даты открыти€";
			return formatPart(period,days);
		}
		catch (Exception e)
		{
			log.error("ќшибка получени€ периода в дн€х",e);
			return "";
		}
	}
}
