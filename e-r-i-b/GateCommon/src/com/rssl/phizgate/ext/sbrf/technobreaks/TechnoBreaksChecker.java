package com.rssl.phizgate.ext.sbrf.technobreaks;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.externalsystem.AutoTechnoBreakConfig;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Проверка актуальности технологических перерывов
 *
 * @author Puzikov
 * @ created 07.11.14
 * @ $Author$
 * @ $Revision$
 */
public class TechnoBreaksChecker
{
	/**
	 * Возвращает первый активный, действующий в данный момент технологический перерыв
	 * @param technoBreaks текущие тех. перерывы
	 * @return первый активный, действующий в данный момент технологический перерыв
	 */
	public static TechnoBreak getActiveBreak(List<TechnoBreak> technoBreaks)
	{
		if (CollectionUtils.isEmpty(technoBreaks))
			return null;

		for (TechnoBreak technoBreak: technoBreaks)
		{
			if (isWithinBreak(technoBreak))
				return technoBreak;
		}

		return null;
	}

	/**
	 * Получить все действующие в данный момент технологические перерывы
	 * @param technoBreaks текущие тех. перерывы
	 * @return список активных, действующих в данный момент технологических перерывов
	 */
	public static List<TechnoBreak> getActiveBreaks(List<TechnoBreak> technoBreaks)
	{
		if (CollectionUtils.isEmpty(technoBreaks))
			return null;

		List<TechnoBreak> withinTechnoBreaks = new ArrayList<TechnoBreak>();
		for (TechnoBreak technoBreak : technoBreaks)
		{
			if (isWithinBreak(technoBreak))
				withinTechnoBreaks.add(technoBreak);
		}
		return withinTechnoBreaks;
	}

	/**
	 * Работает тех. перерыв сейчас или нет.
	 *
	 * @param technoBreak тех. перерыв.
	 * @return работает или нет.
	 */
	private static boolean isWithinBreak(TechnoBreak technoBreak)
	{
		return isWithinBreak(technoBreak, Calendar.getInstance());
	}

	/**
	 * Работает тех. перерыв сейчас или нет.
	 *
	 * @param technoBreak тех. перерыв.
	 * @param date время, по которому проверяется тех. перерыв.
	 * @return работает или нет.
	 */
	private static boolean isWithinBreak(TechnoBreak technoBreak, Calendar date)
	{
		if (technoBreak.getFromDate().compareTo(date) > 0)
			return false;

		if (technoBreak.isAutoEnabled() && isOnlyManualRemoveAutoTechnoBreak() && technoBreak.getStatus() == TechnoBreakStatus.ENTERED)
			return true;

		if (technoBreak.getToDate().compareTo(date) < 0)
			return false;

		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);

		switch(technoBreak.getPeriodic())
		{
			// одноразовый
			case SINGLE:
				return true;

			// ежедневный
			case EVERYDAY:
				// если текущее время в пределах времени указанного в тех перерыве
				return isWithinTime(technoBreak.getFromDate(), technoBreak.getToDate(), date);

			// рабочие дни
			case WORKDAY:
				return (2 <= dayOfWeek && dayOfWeek <= 6)
						&& isWithinTime(technoBreak.getFromDate(), technoBreak.getToDate(), date);

			// выходные дни
			case WEEKEND:
				return (dayOfWeek == 1 || dayOfWeek == 7)
						&& isWithinTime(technoBreak.getFromDate(), technoBreak.getToDate(), date);

			// предвыходные дни
			case BEFOREWEEKEND:
				return (dayOfWeek == 6) && isWithinTime(technoBreak.getFromDate(), technoBreak.getToDate(), date);
		}

		throw new IllegalStateException("Используется некорректный тип технологического перерыва " + technoBreak.getPeriodic() + " для перерыва id = " + technoBreak.getId());
	}

	/**
	 * Стал ли технический перерыв неработающим за время после предыдущего вызова.
	 *
	 * @param technoBreak технический перерыв.
	 * @param lastTestDate время предыдущей проверки.
	 * @return изменил ли тех перерыв статус на неработающий.
	 */
	public static boolean isBecomeNotWithin(TechnoBreak technoBreak, Calendar lastTestDate)
	{
		return isWithinBreak(technoBreak, lastTestDate) && !isWithinBreak(technoBreak);
	}

	private static boolean isWithinTime(Calendar fromDate, Calendar toDate, Calendar date)
	{
		Calendar fromTime = DateHelper.getTime(fromDate);
		Calendar toTime = DateHelper.getTime(toDate);
		Calendar currentTime = DateHelper.getTime(date);

		return fromTime.compareTo(currentTime) <= 0 && currentTime.compareTo(toTime) <= 0;
	}

	public static boolean isOnlyManualRemoveAutoTechnoBreak()
	{
		return ConfigFactory.getConfig(AutoTechnoBreakConfig.class).isManualRemovingAutoTechnoBreak();
	}
}
