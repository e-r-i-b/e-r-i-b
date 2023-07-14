package com.rssl.common.forms.validators;

import com.rssl.common.forms.parsers.FieldValueParser;
import org.apache.commons.collections.CollectionUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 11.05.2012
 * @ $Author$
 * @ $Revision$
 *
 * ’елпер дл€ валидации и парсинга целочисленных интервалов вида 1,2,5,7-10 к листу {1,2,5,7,8,9,10}
 */
public class IntegerIntervalsHelper extends FieldValidatorBase implements FieldValueParser<ArrayList<Integer>>
{
	private static final String DURATION_SEPARATOR = ",";
	private static final String INTERVAL_SEPARATOR = "-";
	private Integer minValue = null;
	private Integer maxValue = null;

	public IntegerIntervalsHelper()
	{}

	public IntegerIntervalsHelper(Integer minValue, Integer maxValue)
	{
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	/**
	 * задать минимальное значение дл€ интервалов
	 * @param minValue минимальное значение дл€ интервалов
	 */
	public void setMinValue(Integer minValue)
	{
		this.minValue = minValue;
	}

	/**
	 * задать максиимальное значение дл€ интервалов
	 * @param maxValue максиимальное значение дл€ интервалов
	 */
	public void setMaxValue(Integer maxValue)
	{
		this.maxValue = maxValue;
	}

	public boolean validate(String value)
	{
		if (isValueEmpty(value))
			return true;

		try
		{
			parse(value, false);
		}
		catch (ParseException ignore)
		{
			return false;
		}

		return true;
	}

	/**
	 * @param value провер€емое число
	 * @return число находитс€ в отрезке [minValue, maxValue]
	 */
	private boolean checkNumber(Integer value)
	{
		if (minValue == null && maxValue == null)
			return true;

		if (minValue == null && maxValue.compareTo(value) < 0)
			return false;

		if (maxValue == null && minValue.compareTo(value) > 0)
			return false;

		return minValue.compareTo(value) <= 0 && maxValue.compareTo(value) >= 0;
	}

	/**
	 * @param value исходна€ строка
	 * @return число
	 * @throws ParseException 1. пустой параметр
	 *                        2. ошибка парсинга числа
	 *                        3. число не принадлежит отрезку [minValue, maxValue]
	 */
	private Integer getIntegerValue(String value) throws ParseException
	{
		try
		{
			if (isValueEmpty(value))
				throw createValidatorException("Ќеверный формат числа");

			Integer intValue = Integer.valueOf(value.trim());
			if (!checkNumber(intValue))
				throw createValidatorException("»нтервал должен быть в пределах от " + minValue + " до " + maxValue + ".");

			return intValue;
		}
		catch (NumberFormatException ignore)
		{
			throw createValidatorException("Ќеверный формат числа");
		}
	}

	private ParseException createValidatorException(String msg)
	{
		return new ParseException(msg, 0);
	}

	/**
	 * ¬спомогательна€ функци€ дл€ преобразовани€
	 * @param interval строка содержаща€ число или интервал ѕример "5" или "7-10"
	 * @return лист со занчени€ми {5} или {7,8,9,10}
	 * @throws ParseException 0. исключительные ситуации метода getIntegerValue(String value)
	 *                        1. интервал пустой
	 *                        2. интервал вида 2-1
	 *                        3. интервал вида 1-2-3
	 */
	private List<Integer> parseInterval(String interval) throws ParseException
	{
		if (isValueEmpty(interval))
			throw createValidatorException("Ќеверно задан интервал");

		List<Integer> intervalList = new ArrayList<Integer>();
		String[] subIntervalArray = interval.split(INTERVAL_SEPARATOR);
		if(subIntervalArray.length == 1)
		{
			intervalList.add(getIntegerValue(subIntervalArray[0].trim()));
			return intervalList;
		}
		if(subIntervalArray.length == 2)
		{
			Integer startInterval = getIntegerValue(subIntervalArray[0].trim());
			Integer endInterval = getIntegerValue(subIntervalArray[1].trim());
			if (startInterval.compareTo(endInterval) == 1)
				throw createValidatorException("Ќеверно задан интервал");

			for(int i = startInterval; i <= endInterval; i++)
				intervalList.add(i);

			return intervalList;
		}
		throw createValidatorException("Ќеверный формат интервала");
	}

	/**
	 * @param value исходна€ строка вида "1,2,5,7-10"
	 * @param needSort нужна ли сортировка результата
	 * @return лист вида {1,2,5,7,8,9,10}
	 * @throws ParseException 0. исключительные ситуации метода parseInterval(String interval)
	 *                        1. найдено пересечение интервалов
	 */
	private ArrayList<Integer> parse(String value, boolean needSort) throws ParseException
	{
		ArrayList<Integer> intervalList = new ArrayList<Integer>();
		if (isValueEmpty(value))
			return intervalList;

		String[] intervalArray = value.split(DURATION_SEPARATOR);
		for(String interval: intervalArray)
		{
			List<Integer> list = parseInterval(interval);
			if (CollectionUtils.containsAny(intervalList, list))
				throw createValidatorException("Ќайдено пересечение интервалов");

			intervalList.addAll(list);
		}
		if (needSort)
			Collections.sort(intervalList);
		return intervalList;
	}

	/**
	 * ѕреобразует строку вида 1,2,5,7-10 к листу {1,2,5,7,8,9,10}
	 * @param value строка дл€ преобразовани€
	 * @return лист значений интервалов
	 */
	public ArrayList<Integer> parse(String value) throws ParseException
	{
		return parse(value, true);
	}
}
