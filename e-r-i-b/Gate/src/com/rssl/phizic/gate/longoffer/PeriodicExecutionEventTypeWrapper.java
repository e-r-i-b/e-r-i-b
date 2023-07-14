package com.rssl.phizic.gate.longoffer;

import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * ������� ����������� �������� �� ������������� ����������
 *
 * @author khudyakov
 * @ created 27.10.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class PeriodicExecutionEventTypeWrapper
{
	private static final String ONCE_IN_WEEK_TEXT_MESSAGE           = "��� � ������, %s";
	private static final String ONCE_IN_MONTH_TEXT_MESSAGE          = "��� � �����, %s-�� �����";
	private static final String ONCE_IN_QUARTER_TEXT_MESSAGE        = "��� � �������, %s-�� ����� %s-�� ������ ��������";
	private static final String ONCE_IN_YEAR_TEXT_MESSAGE           = "��� � ���, %s %s";


	private static final Map<ExecutionEventType, PeriodicExecutionEventTypeWrapper> WRAPPERS = new HashMap<ExecutionEventType, PeriodicExecutionEventTypeWrapper>();
	static
	{
		WRAPPERS.put(ExecutionEventType.ONCE_IN_WEEK,       new OnceInWeeklyExecutionEventTypeWrapper());
		WRAPPERS.put(ExecutionEventType.ONCE_IN_MONTH,      new OnceInMonthExecutionEventTypeWrapper());
		WRAPPERS.put(ExecutionEventType.ONCE_IN_QUARTER,    new OnceInQuarterExecutionEventTypeWrapper());
		WRAPPERS.put(ExecutionEventType.ONCE_IN_HALFYEAR,   new OnceInHalfYearExecutionEventTypeWrapper());
		WRAPPERS.put(ExecutionEventType.ONCE_IN_YEAR,       new OnceInYearExecutionEventTypeWrapper());
	}

	/**
	 * �������� ����������� �� ������������� ����������
	 * @param eventType ��� �������������
	 * @param startDate ���� ������ ��������
	 * @return �����������
	 */
	public static String getExecutionDetail(ExecutionEventType eventType, Calendar startDate)
	{
		return WRAPPERS.get(eventType).getExecutionDetail(startDate);
	}

	protected abstract String getExecutionDetail(Calendar startDate);

	/**
	 * ��� � ������
	 */
	private static class OnceInWeeklyExecutionEventTypeWrapper extends PeriodicExecutionEventTypeWrapper
	{
		@Override
		public String getExecutionDetail(Calendar startDate)
		{
			return String.format(ONCE_IN_WEEK_TEXT_MESSAGE, DateHelper.getDayOfWeekWord(startDate));
		}
	}

	/**
	 * ��� � �����
	 */
	private static class OnceInMonthExecutionEventTypeWrapper extends PeriodicExecutionEventTypeWrapper
	{
		@Override
		public String getExecutionDetail(Calendar startDate)
		{
			return String.format(ONCE_IN_MONTH_TEXT_MESSAGE, startDate.get(Calendar.DATE));
		}
	}

	/**
	 * ��� � �������
	 */
	private static class OnceInQuarterExecutionEventTypeWrapper extends PeriodicExecutionEventTypeWrapper
	{
		@Override
		public String getExecutionDetail(Calendar startDate)
		{
			return String.format(ONCE_IN_QUARTER_TEXT_MESSAGE, startDate.get(Calendar.DATE), DateHelper.getMonthOfQuarter(startDate));
		}
	}

	/**
	 * ��� � �������
	 */
	private static class OnceInHalfYearExecutionEventTypeWrapper extends PeriodicExecutionEventTypeWrapper
	{
		@Override
		public String getExecutionDetail(Calendar startDate)
		{
			//�� ������ ������ ��� ���������
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * ��� � ���
	 */
	private static class OnceInYearExecutionEventTypeWrapper extends PeriodicExecutionEventTypeWrapper
	{
		@Override
		public String getExecutionDetail(Calendar startDate)
		{
			return String.format(ONCE_IN_YEAR_TEXT_MESSAGE, startDate.get(Calendar.DATE), DateHelper.monthNumberToString(startDate.get(Calendar.MONTH) + 1));
		}
	}
}
