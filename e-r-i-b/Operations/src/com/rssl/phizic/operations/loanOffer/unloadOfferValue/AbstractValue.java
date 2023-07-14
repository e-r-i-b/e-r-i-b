package com.rssl.phizic.operations.loanOffer.unloadOfferValue;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author gulov
 * @ created 13.07.2011
 * @ $Authors$
 * @ $Revision$
 */
abstract class AbstractValue implements Value
{
	private static final String POINT_DELIMITER = ".";

	boolean mandatory = true;
	int commaCount = 1;

	AbstractValue(boolean mandatory, int commaCount)
	{
		this.mandatory = mandatory;
		this.commaCount = commaCount;
	}

	public abstract Object getValue();

	public boolean isMandatory()
	{
		return mandatory;
	}

	public int getCommaCount()
	{
		return commaCount;
	}

	public boolean isEmpty()
	{
		return getValue() == null || StringHelper.isEmpty(String.valueOf(getValue()));
	}

	/**
	 * убираем из входящей строки символ ","
	 * и заменяем его на "."
	 * ("," в конце параметра удаляется)
	 * @param inputString входящая строка
	 */
	String replaceComma(String inputString)
	{
		String[] result = inputString.split(",");
		if (result.length == 1)
			return inputString;

		StringBuilder resultString = new StringBuilder();
		for (int i = 0; i < result.length; i++)
		{
			resultString.append(result[i]);
			if (result.length-1 != i)
				resultString.append(POINT_DELIMITER);
		}

		return resultString.toString();
	}
}
