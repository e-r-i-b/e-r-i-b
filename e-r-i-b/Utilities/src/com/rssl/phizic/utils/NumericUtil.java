package com.rssl.phizic.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

/**
 * @author Evgrafov
 * @ created 10.11.2005
 * @ $Author$
 * @ $Revision$
 */

public final class NumericUtil
{
	private static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
	private static final int TEN_RADIX = 10;

	protected static NumberFormat getNumberFormat()
	{
		return (NumberFormat)numberFormat.clone();
	}

	private NumericUtil ()
	{
	}

	public static double parseDouble ( String str ) throws ParseException
	{
		Number number = numberFormat.parse(replaceSeparator(str));
		return number.doubleValue();
	}

	public static double parseMoney ( String str ) throws ParseException
	{
		ParsePosition parsePosition = new ParsePosition(0);
		Number number = numberFormat.parse(replaceSeparator(str), parsePosition);

		if (parsePosition.getErrorIndex()!=-1)
		{
			throw new ParseException("Invalid format", parsePosition.getErrorIndex());
		}

		if (parsePosition.getIndex()!=str.length())
		{
			throw new ParseException("Invalid format", parsePosition.getIndex());
		}

		return number.doubleValue();
	}

	private static String replaceSeparator ( String str )
	{
		return str.replace(',', '.');
	}

	public static BigDecimal parseBigDecimal ( String str ) throws ParseException
	{
		if (StringHelper.isEmpty(str))
			return null;

		try
		{
			return new BigDecimal(replaceSeparator(str));
		}
		catch (NumberFormatException e)
		{
			throw new ParseException(e.getMessage(), 0);
		}
	}

	public static String format ( double value )
	{
		return getNumberFormat().format(value);
	}

	public static int parseInt ( String str )
	{
		if (StringHelper.isNotEmpty(str))
		{
			try
			{
				return Integer.parseInt(str);
			}
			catch (NumberFormatException e)
			{
				return 0;
			}
		}
		return 0;
	}

	public static Integer parseInteger(String str) throws NumberFormatException
	{
		if (StringHelper.isNotEmpty(str))
			return Integer.parseInt(str);
		return null;
	}

	/**
	 * Преобразовать Number в Long
	 * @param data данные
	 * @return null в случае data = null, значение в в виде Long иначе
	 */
	public static Long toLong(Number data)
	{
		return data == null? null:data.longValue();
	}

	/**
	 * Преобразовать Number в Integer
	 * @param data данные
	 * @return null в случае data = null, значение в в виде Long иначе
	 */
	public static Integer toInt(Number data)
	{
		return data == null ? null : data.intValue();
	}

	/**
	 * Проверка на пустоту типа Integer
	 * @param value
	 * @return - true - пусто, false - не пусто
	 */
	public static boolean isEmpty(Integer value)
	{
		return value == null || value.equals(0);
	}

	/**
	 * Проверка на пустоту типа Long
	 * @param value
	 * @return true - пусто, false - не пусто
	 */
	public static boolean isEmpty(Long value)
	{
		return value == null || value.equals(0L);
	}

	/**
	 * Проверка на не пустоту типа Long
	 * @param value
	 * @return true - не пусто, false - пусто
	 */
	public static boolean isNotEmpty(Long value)
	{
		return !isEmpty(value);
	}

	/**
	 * Возвращает 0 если value = null, иначе value
	 * @param value - значение
	 * @return 0 если value = null, иначе value
	 */
	public static Integer getEmptyIfNull(Integer value)
	{
		return value == null ? 0 : value;
	}

	/**
	 * Возвращает 0 если value = null, иначе value
	 * @param value - значение
	 * @return 0 если value = null, иначе value
	 */
	public static Long getEmptyIfNull(Long value)
	{
		return value == null ? 0L : value;
	}

	/**
	 * Возвращает 0 если value = null, иначе value
	 * @param value - значение
	 * @return 0 если value = null, иначе value
	 */
	public static BigDecimal getEmptyIfNull(BigDecimal value)
	{
		return value == null ? new BigDecimal(0) : value;
	}

	/**
	 * Сравнение двух Integer без различия между 0 и null.
	 * @param val1
	 * @param val2
	 * @return true <=> equals
	 */
	public static boolean equalsNullIgnore(Integer val1, Integer val2)
	{
		return getEmptyIfNull(val1).equals(getEmptyIfNull(val2));
	}

	/**
	 * Сравнение двух Long без различия между 0 и null.
	 * @param val1
	 * @param val2
	 * @return true <=> equals
	 */
	public static boolean equalsNullIgnore(Long val1, Long val2)
	{
		return getEmptyIfNull(val1).equals(getEmptyIfNull(val2));
	}

	/**
	 * Сравнение двух BigDecimal без различия между 0.0, 0.00 и null.
	 * @param val1
	 * @param val2
	 * @return true <=> equals
	 */
	public static boolean equalsNullIgnore(BigDecimal val1, BigDecimal val2)
	{
		return getEmptyIfNull(val1).compareTo(getEmptyIfNull(val2)) == 0;
	}

	/**
	 * @param str - строка в которой необходимо посчитать сумму цифр
	 * @return Возвращает сумму цифр в строке
	 */
	public static int sumDigitInString(String str)
	{
		int sum = 0;
		for(int i = 0; i < str.length(); i++)
		{
			char ch = str.charAt(i);
			int digit = Character.digit(ch,TEN_RADIX);
			if(digit > 0)
				sum += digit;
		}
		return sum;
	}

	/**
	 * Сравнивает величины как денежные суммы
	 * @param amount1
	 * @param amount2
	 * @return true, если суммы равны или не заданы (обе null)
	 */
	public static boolean equalsAsMoneyAmount(BigDecimal amount1, BigDecimal amount2)
	{
		if (amount1 == null)
			return amount2 == null;

		return amount1.compareTo(amount2) == 0;
	}

	/**
	 * Сравнение значений, допускающее null
	 * @param val1 значение 1
	 * @param val2 значение 2
	 * @return результат сравнения
	 */
	public static final <T extends Comparable<? super T>> int compare(T val1, T val2)
	{
		if (val1 == null && val2 == null)
			return 0;

		if (val1 == null)
			return -1;

		if (val2 == null)
			return 1;

		return val1.compareTo(val2);
	}
}
