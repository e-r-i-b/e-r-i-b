package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author emakarov
 * @ created 27.03.2008
 * @ $Author$
 * @ $Revision$
 */
public class DateTimeCompareValidator extends MultiFieldsValidatorBase
{
	// класс получает дату и время для сравнений отдельно друг от друга
	public static final String FIELD_FROM_DATE = "from_date";
	public static final String FIELD_FROM_TIME = "from_time";
    public static final String FIELD_TO_DATE =   "to_date";
	public static final String FIELD_TO_TIME =   "to_time";

	public static final String GREATE       = "gt";
    public static final String GREATE_EQUAL = "ge";
    public static final String EQUAL        = "eq";
    public static final String NOT_EQUAL    = "ne";
    public static final String LESS         = "lt";
    public static final String LESS_EQUAL   = "le";

    public static final String OPERATOR = "operator";
	public static final String CUR_DATE = "curDate";

	/**
	 * Проверяет значения полей на валидность ЭТОТ МЕТОД ВО ВСЕХ РЕАЛИЗАЦИЯХ ДОЛЖЕН БЫТЬ THREAD SAFE!!!!!!!!!
	 * Если необходимо сравнить значение какого-либо поля с текущей датой, то в валидатор необходимо добавить
	 * параметр CUR_DATE со значением либо "to", либо "from" (постфикс __cleartime означает нужно ли при этом
	 * очищать время). В этом случае метод проверяет наличие этого параметра и соответствующее
	 * значение поля (to или from) заменяет на текущую дату
	 * @param values значения для проверки. Key - имя поля (в форме), Value - значение поля.
	 */
	public boolean validate(Map values) throws TemporalDocumentException
	{
		Date fromDate = (Date)retrieveFieldValue(FIELD_FROM_DATE, values);
		Date fromTime = (Date)retrieveFieldValue(FIELD_FROM_TIME, values);
		Date toDate   = (Date)retrieveFieldValue(FIELD_TO_DATE,   values);
		Date toTime   = (Date)retrieveFieldValue(FIELD_TO_TIME,   values);

		String op = getParameter(OPERATOR);
        if (op == null)
            throw new RuntimeException("Параметр '" + OPERATOR + "' не определен!");

		String cur = getParameter(CUR_DATE);
		if (cur != null)
		{
			if (cur.equals("from"))
			{
				fromDate = Calendar.getInstance().getTime();
			}
			else if(cur.equals("from_cleartime"))
			{
				Calendar currentDate = Calendar.getInstance();
				fromDate = DateHelper.startOfDay(currentDate).getTime();
				if (toTime != null)
					fromTime = (Date)toTime.clone();
			}
			else if(cur.equals("to_cleartime"))
			{
				Calendar currentDate = Calendar.getInstance();
				toDate = DateHelper.startOfDay(currentDate).getTime();
				if (fromTime != null)
					toTime = (Date)fromTime.clone();
			}
			else
			{
				toDate = Calendar.getInstance().getTime();
			}
		}

		if (fromDate == null || toDate == null)
			return true;

        if ((fromDate == null && fromTime == null) || (toDate == null && toTime == null))
            return true;

		if (fromTime == null || toTime == null)
			return compareOnlyDates(fromDate, toDate, op);

        int resultDateCompare = normalize(fromDate.compareTo(toDate));
		int resultTimeCompare = normalize(fromTime.compareTo(toTime));

		if (op.equals(EQUAL))
		{
			return (resultDateCompare == 0) && (resultTimeCompare == 0);
		}
		else if (op.equals(NOT_EQUAL))
		{
			return (resultDateCompare != 0) || ((resultDateCompare == 0) && (resultTimeCompare != 0));
		}
		else if (op.equals(LESS))
		{
			return (resultDateCompare < 0) || ((resultDateCompare == 0) && (resultTimeCompare < 0));
		}
		else if (op.equals(GREATE))
		{
			return (resultDateCompare > 0) || ((resultDateCompare == 0) && (resultTimeCompare > 0));
		}
		else if (op.equals(LESS_EQUAL))
		{
			return (resultDateCompare < 0) || ((resultDateCompare == 0) && (resultTimeCompare <= 0));
		}
		else if (op.equals(GREATE_EQUAL))
		{
			return (resultDateCompare > 0) || ((resultDateCompare == 0) && (resultTimeCompare >= 0));
		}

        return false;
	}

	private Boolean compareOnlyDates(Date fromDate,Date toDate,String op)
	{
		int resultDateCompare = normalize(fromDate.compareTo(toDate));

		if (op.equals(EQUAL))
		{
			return resultDateCompare == 0;
		}
		else if (op.equals(NOT_EQUAL))
		{
			return (resultDateCompare != 0);
		}
		else if (op.equals(LESS))
		{
			return resultDateCompare < 0;
		}
		else if (op.equals(GREATE))
		{
			return resultDateCompare > 0;
		}
		else if (op.equals(LESS_EQUAL))
		{
			return ((resultDateCompare < 0) || (resultDateCompare == 0));
		}
		else if (op.equals(GREATE_EQUAL))
		{
			return ((resultDateCompare > 0) || (resultDateCompare == 0));
		}

        return false;
	}

	private static int normalize(int compareResult)
    {
        if(compareResult == 0)
            return 0;
        return compareResult > 0 ? 1 : -1;
    }

	private Calendar updateTime(Calendar targetCalendar, Calendar sourceCalendar)
	{
		targetCalendar.set(Calendar.HOUR_OF_DAY, sourceCalendar.get(Calendar.HOUR_OF_DAY));
		targetCalendar.set(Calendar.MINUTE, sourceCalendar.get(Calendar.MINUTE));
		targetCalendar.set(Calendar.SECOND, sourceCalendar.get(Calendar.SECOND));
		targetCalendar.set(Calendar.MILLISECOND, sourceCalendar.get(Calendar.MILLISECOND));

		return targetCalendar;
	}
}
