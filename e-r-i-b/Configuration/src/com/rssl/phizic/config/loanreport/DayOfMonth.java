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
 * ���� ������
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
	 * ������ ���� ������ �� ������
	 * @param dayString - ���� ������ � ���� ������
	 * @return ���� ������ (never null)
	 */
	public static DayOfMonth fromString(String dayString)
	{
		if (StringHelper.isEmpty(dayString))
			throw new IllegalArgumentException("�� ������ ���� ������");

		try
		{
			int day = Integer.parseInt(dayString);
			if (day < 1 || day > MAX_DAY)
				throw new IllegalArgumentException("������ ������������ ���� ������: " + dayString);
			return new DayOfMonth(day);
		}
		catch (NumberFormatException ignored)
		{
			throw new IllegalArgumentException("������ ���������� ���� ������: " + dayString);
		}
	}

	/**
	 * ���������� ���� ��� ��������� ������.
	 * ���� � ������ ��� ����������� ��� (��������, 30 �������), ���������� ��������� ���� ������
	 * @param instantMonth - �����, ������������ �������� �������������� ����
	 * @return ���� + 00:00:00
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
