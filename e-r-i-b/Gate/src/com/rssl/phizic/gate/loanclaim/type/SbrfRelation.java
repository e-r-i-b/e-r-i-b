package com.rssl.phizic.gate.loanclaim.type;

/**
 * Принадлежность клиента к Сбербанку
 * @author Rtischeva
 * @ created 05.02.15
 * @ $Author$
 * @ $Revision$
 */
public enum SbrfRelation
{
	WORK_IN_SBRF("workInSbrf"),

	GET_PAID_ON_SBRF_ACCOUNT("getPaidOnSbrfAccount"),

	NOT_RELATED_TO_SBER ("otherSbrfRelationType")
	;

	private final String value;

	private SbrfRelation(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	public static SbrfRelation fromValue(String value)
	{
		for (SbrfRelation sbrfRelation : values())
			if (value.equals(sbrfRelation.getValue()))
				return sbrfRelation;

		throw new IllegalArgumentException("Неизвестный тип принадлежности клиента к сбербанку[" + value + "]");
	}
}