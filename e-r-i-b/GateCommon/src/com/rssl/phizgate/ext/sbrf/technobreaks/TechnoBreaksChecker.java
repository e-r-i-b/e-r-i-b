package com.rssl.phizgate.ext.sbrf.technobreaks;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.externalsystem.AutoTechnoBreakConfig;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * �������� ������������ ��������������� ���������
 *
 * @author Puzikov
 * @ created 07.11.14
 * @ $Author$
 * @ $Revision$
 */
public class TechnoBreaksChecker
{
	/**
	 * ���������� ������ ��������, ����������� � ������ ������ ��������������� �������
	 * @param technoBreaks ������� ���. ��������
	 * @return ������ ��������, ����������� � ������ ������ ��������������� �������
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
	 * �������� ��� ����������� � ������ ������ ��������������� ��������
	 * @param technoBreaks ������� ���. ��������
	 * @return ������ ��������, ����������� � ������ ������ ��������������� ���������
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
	 * �������� ���. ������� ������ ��� ���.
	 *
	 * @param technoBreak ���. �������.
	 * @return �������� ��� ���.
	 */
	private static boolean isWithinBreak(TechnoBreak technoBreak)
	{
		return isWithinBreak(technoBreak, Calendar.getInstance());
	}

	/**
	 * �������� ���. ������� ������ ��� ���.
	 *
	 * @param technoBreak ���. �������.
	 * @param date �����, �� �������� ����������� ���. �������.
	 * @return �������� ��� ���.
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
			// �����������
			case SINGLE:
				return true;

			// ����������
			case EVERYDAY:
				// ���� ������� ����� � �������� ������� ���������� � ��� ��������
				return isWithinTime(technoBreak.getFromDate(), technoBreak.getToDate(), date);

			// ������� ���
			case WORKDAY:
				return (2 <= dayOfWeek && dayOfWeek <= 6)
						&& isWithinTime(technoBreak.getFromDate(), technoBreak.getToDate(), date);

			// �������� ���
			case WEEKEND:
				return (dayOfWeek == 1 || dayOfWeek == 7)
						&& isWithinTime(technoBreak.getFromDate(), technoBreak.getToDate(), date);

			// ������������ ���
			case BEFOREWEEKEND:
				return (dayOfWeek == 6) && isWithinTime(technoBreak.getFromDate(), technoBreak.getToDate(), date);
		}

		throw new IllegalStateException("������������ ������������ ��� ���������������� �������� " + technoBreak.getPeriodic() + " ��� �������� id = " + technoBreak.getId());
	}

	/**
	 * ���� �� ����������� ������� ������������ �� ����� ����� ����������� ������.
	 *
	 * @param technoBreak ����������� �������.
	 * @param lastTestDate ����� ���������� ��������.
	 * @return ������� �� ��� ������� ������ �� ������������.
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
