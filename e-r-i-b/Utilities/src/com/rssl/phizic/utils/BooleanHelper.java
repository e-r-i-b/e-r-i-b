package com.rssl.phizic.utils;

/**
 * @author Dorzhinov
 * @ created 03.10.2013
 * @ $Author$
 * @ $Revision$
 */
public class BooleanHelper
{
	/**
	 * Если пришедший объект типа Boolean, то возвращает его
	 * если объект типа String, то возвращает Boolean.valueOf(String)
	 * иначе - Boolean.valueOf(obj.toString())
	 * @param obj
	 * @return
	 */
	public static Boolean asBoolean(Object obj)
	{
		if (obj == null)
			throw new IllegalArgumentException("Аргумент obj не может быть null");

		if (obj instanceof Boolean)
			return (Boolean) obj;
		else if (obj instanceof String)
			return Boolean.valueOf((String) obj);
		else
			return Boolean.valueOf(obj.toString());

	}

	/**
	 * Преобразобоать Boolean к простому типау(в случае null дефолтное значение)
	 * @param value объект Boolean
	 * @param defaultValue дефолтное значение
	 * @return резултат
	 */
	public static boolean getNullBooleanValue(Boolean value, boolean defaultValue)
	{
		if (value == null)
		{
			return defaultValue;
		}
		return value;
	}
}
