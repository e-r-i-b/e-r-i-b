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
 * ������ ��� ��������� � �������� ������������� ���������� ���� 1,2,5,7-10 � ����� {1,2,5,7,8,9,10}
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
	 * ������ ����������� �������� ��� ����������
	 * @param minValue ����������� �������� ��� ����������
	 */
	public void setMinValue(Integer minValue)
	{
		this.minValue = minValue;
	}

	/**
	 * ������ ������������� �������� ��� ����������
	 * @param maxValue ������������� �������� ��� ����������
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
	 * @param value ����������� �����
	 * @return ����� ��������� � ������� [minValue, maxValue]
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
	 * @param value �������� ������
	 * @return �����
	 * @throws ParseException 1. ������ ��������
	 *                        2. ������ �������� �����
	 *                        3. ����� �� ����������� ������� [minValue, maxValue]
	 */
	private Integer getIntegerValue(String value) throws ParseException
	{
		try
		{
			if (isValueEmpty(value))
				throw createValidatorException("�������� ������ �����");

			Integer intValue = Integer.valueOf(value.trim());
			if (!checkNumber(intValue))
				throw createValidatorException("�������� ������ ���� � �������� �� " + minValue + " �� " + maxValue + ".");

			return intValue;
		}
		catch (NumberFormatException ignore)
		{
			throw createValidatorException("�������� ������ �����");
		}
	}

	private ParseException createValidatorException(String msg)
	{
		return new ParseException(msg, 0);
	}

	/**
	 * ��������������� ������� ��� ��������������
	 * @param interval ������ ���������� ����� ��� �������� ������ "5" ��� "7-10"
	 * @return ���� �� ���������� {5} ��� {7,8,9,10}
	 * @throws ParseException 0. �������������� �������� ������ getIntegerValue(String value)
	 *                        1. �������� ������
	 *                        2. �������� ���� 2-1
	 *                        3. �������� ���� 1-2-3
	 */
	private List<Integer> parseInterval(String interval) throws ParseException
	{
		if (isValueEmpty(interval))
			throw createValidatorException("������� ����� ��������");

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
				throw createValidatorException("������� ����� ��������");

			for(int i = startInterval; i <= endInterval; i++)
				intervalList.add(i);

			return intervalList;
		}
		throw createValidatorException("�������� ������ ���������");
	}

	/**
	 * @param value �������� ������ ���� "1,2,5,7-10"
	 * @param needSort ����� �� ���������� ����������
	 * @return ���� ���� {1,2,5,7,8,9,10}
	 * @throws ParseException 0. �������������� �������� ������ parseInterval(String interval)
	 *                        1. ������� ����������� ����������
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
				throw createValidatorException("������� ����������� ����������");

			intervalList.addAll(list);
		}
		if (needSort)
			Collections.sort(intervalList);
		return intervalList;
	}

	/**
	 * ����������� ������ ���� 1,2,5,7-10 � ����� {1,2,5,7,8,9,10}
	 * @param value ������ ��� ��������������
	 * @return ���� �������� ����������
	 */
	public ArrayList<Integer> parse(String value) throws ParseException
	{
		return parse(value, true);
	}
}
