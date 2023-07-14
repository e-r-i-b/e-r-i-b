package com.rssl.phizic.gate.longoffer;

import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * ¬раппер расшифровки описани€ по периодичности исполнени€
 *
 * @author khudyakov
 * @ created 27.10.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class PeriodicExecutionEventTypeWrapper
{
	private static final String ONCE_IN_WEEK_TEXT_MESSAGE           = "раз в неделю, %s";
	private static final String ONCE_IN_MONTH_TEXT_MESSAGE          = "раз в мес€ц, %s-го числа";
	private static final String ONCE_IN_QUARTER_TEXT_MESSAGE        = "раз в квартал, %s-го числа %s-го мес€ца квартала";
	private static final String ONCE_IN_YEAR_TEXT_MESSAGE           = "раз в год, %s %s";


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
	 * ѕолучить расшифровку по периодичности исполнени€
	 * @param eventType тип периодичности
	 * @param startDate дата начала дейчтви€
	 * @return расшифровка
	 */
	public static String getExecutionDetail(ExecutionEventType eventType, Calendar startDate)
	{
		return WRAPPERS.get(eventType).getExecutionDetail(startDate);
	}

	protected abstract String getExecutionDetail(Calendar startDate);

	/**
	 * раз в неделю
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
	 * раз в мес€ц
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
	 * раз в квартал
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
	 * раз в полгода
	 */
	private static class OnceInHalfYearExecutionEventTypeWrapper extends PeriodicExecutionEventTypeWrapper
	{
		@Override
		public String getExecutionDetail(Calendar startDate)
		{
			//на данный момент нет текстовки
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * раз в год
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
