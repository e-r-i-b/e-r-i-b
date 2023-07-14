package com.rssl.phizic.business.businessProperties;

import com.rssl.common.forms.parsers.SqlTimeParser;
import com.rssl.phizic.common.types.transmiters.Triplet;
import org.apache.commons.lang.time.DateFormatUtils;

import java.sql.Time;
import java.text.ParseException;

/**
 * хелпер для помощи в работе с настройками времени оплаты кредита для кредитных систем
 * @author basharin
 * @ created 04.04.14
 * @ $Author$
 * @ $Revision$
 */

public class LoanReceptionTimeHelper
{
	private static final String TIME_FORMAT = "HH:mm";
	public static final String PREFIX_KEY = "com.rssl.business.LoanReceptionTimeProperty.";
	public static final String SEPARATOR = "-";
	private static final SqlTimeParser parser = new SqlTimeParser();

	/**
	 * Получить значение настройки для записи в бд из ее значений
	 * @param fromTime - времени оплаты кредита для кредитных систем (от скольки)
	 * @param toTime - времени оплаты кредита для кредитных систем (до скольки)
	 * @param timeZone - код часового пояса
	 * @return значение настройки для записи в бд
	 */
	public static String getValueFromTime(Time fromTime, Time toTime, String timeZone)
	{
		return DateFormatUtils.format(fromTime, TIME_FORMAT) + SEPARATOR + DateFormatUtils.format(toTime, TIME_FORMAT) +SEPARATOR + timeZone;
	}

	/**
	 * Получить временя оплаты кредита для кредитных систем
	 * @param value - значение настройки
	 * @return тройка из значений (от скольки, до скольки, код часового пояса)
	 * @throws ParseException
	 */
	public static Triplet<Time, Time, String> getTimeFromValue(String value) throws ParseException
	{
		String[] arr = value.split(SEPARATOR);
		return new Triplet<Time, Time, String>(parser.parse(arr[0]), parser.parse(arr[1]), arr[2]);
	}

	/**
	 * ключ настройки для записи в бд.
	 * @param loanSystem - кредитной система
	 * @return ключ настройки для записи в бд.
	 */
	public static String getFullKey(String loanSystem)
	{
		return PREFIX_KEY + loanSystem;
	}

	/**
	 * получить название кредитной системы
	 * @param fullKey - ключ настройки для записи в бд
	 * @return название кредитной системы
	 */
	public static String getNameLoanSystem(String fullKey)
	{
		return fullKey.substring(PREFIX_KEY.length());
	}
}
