package com.rssl.phizic.config.loanreport;

import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 18.11.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ƒень мес€ца
 */
@Immutable
public final class DayOfMonth
{
	private static final int MAX_DAY = 31;

	private final int day;

	private DayOfMonth(int day)
	{
		this.day = day;
	}

	/**
	 * „итает день мес€ца из строки
	 * @param dayString - день мес€ца в виде строки
	 * @return день мес€ца (never null)
	 */
	public static DayOfMonth fromString(String dayString)
	{
		if (StringHelper.isEmpty(dayString))
			throw new IllegalArgumentException("Ќе указан день мес€ца");

		try
		{
			int day = Integer.parseInt(dayString);
			if (day < 1 || day > MAX_DAY)
				throw new IllegalArgumentException("”казан недопустимый день мес€ца: " + dayString);
			return new DayOfMonth(day);
		}
		catch (NumberFormatException ignored)
		{
			throw new IllegalArgumentException("”казан непон€тный день мес€ца: " + dayString);
		}
	}

	/**
	 * ¬озвращает дату дл€ заданного мес€ца.
	 * ≈сли в мес€це нет подход€щего дн€ (например, 30 феврал€), возвращает последний день мес€ца
	 * @param instantMonth - мес€ц, относительно которого рассчитываетс€ дата
	 * @return дата + 00:00:00
	 */
	public Calendar toInstantDate(Calendar instantMonth)
	{
		int lastDayOfMonth = instantMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
		int instantDay = Math.min(day, lastDayOfMonth);

		Calendar instantDate = (Calendar) instantMonth.clone();
		instantDate.set(Calendar.DAY_OF_MONTH, instantDay);
		instantDate.set(Calendar.HOUR_OF_DAY, 0);
		instantDate.set(Calendar.MINUTE, 0);
		instantDate.set(Calendar.SECOND, 0);
		instantDate.set(Calendar.MILLISECOND, 0);
		return instantDate;
	}

	@Override
	public String toString()
	{
		return String.format("%02d", day);
	}
}
