package com.rssl.common.forms.parsers;


import com.rssl.common.forms.validators.FieldValidatorBase;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * ¬алидатор/парсер перечислени€ целых чисел через зап€тую
 * @author koptyaev
 * @ created 14.11.13
 * @ $Author$
 * @ $Revision$
 */
public class ListLongParser extends FieldValidatorBase implements FieldValueParser<ArrayList<Long>>
{
	public static final String SEPARATOR = ",";

	public ListLongParser(String message)
	{
		super();
		setMessage(message);
	}

	public ListLongParser()
	{
		super();
	}


	protected boolean isValueEmpty(String value)
	{
	    if (value == null || value.equals(""))
	        return true;

	    return false;
	}

	private Long getLongValue(String value) throws ParseException
	{
		if (isValueEmpty(value))
			throw createValidatorException("Ќеверный формат числа");
		try
		{
			return Long.valueOf(value.trim());
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
	 * ѕарсит строку
	 * @param value строка
	 * @return распарсенный ArrayList из чисел
	 * @throws ParseException ошибки парсинга
	 */
	public ArrayList<Long> parse(String value) throws ParseException
	{
		ArrayList<Long> list = new ArrayList<Long>();
		if (isValueEmpty(value))
			return list;

		String[] valuesArray = value.split(SEPARATOR);
		for (String val:valuesArray)
		{
			list.add(getLongValue(val));
		}
		return list;
	}

	/**
	 * ¬алидирует строку
	 * @param value значение дл€ проверки
	 * @return валидна ли строка
	 */
	public boolean validate(String value)
	{
		if (isValueEmpty(value))
			return true;

		try
		{
			parse(value);
		}
		catch (ParseException ignore)
		{
			return false;
		}

		return true;
	}
}
