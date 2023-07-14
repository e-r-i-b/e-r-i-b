package com.rssl.phizic.business.dictionaries.pfp.personData;

/**
 * @author mihaylov
 * @ created 02.03.2012
 * @ $Author$
 * @ $Revision$
 */

/** Количество детей. Вынесено в енум для значения "более 5" */
public enum ChildCount
{
	NONE(0),
	ONE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5),
	moreThanFIVE(900); // "Магическое" число, означающее "больше 5"

	private Integer value;

	ChildCount(Integer value)
	{
		this.value = value;
	}

	public int toValue()
	{
		return value;
	}

	public static ChildCount fromValue(int value)
	{
		switch (value)
		{
			case 0:
				return NONE;
			case 1:
				return ONE;
			case 2:
				return TWO;
			case 3:
				return THREE;
			case 4:
				return FOUR;
			case 5:
				return FIVE;
			case 900:
				return moreThanFIVE;
			default:
				throw new IllegalArgumentException("Некорректное количество детей: " + value);
		}
	}
}
