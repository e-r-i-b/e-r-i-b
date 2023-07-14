package com.rssl.phizic.business.offers;

/**
 * @author Rtischeva
 * @ created 21.01.15
 * @ $Author$
 * @ $Revision$
 */
public enum OfferType
{
	OFFER_VERSION_1("1"),

	OFFER_VERSION_2("2");

	private String value;

	OfferType(String value)
	{
		this.value = value;
	}

	public String toValue()
	{
		return value;
	}

	public static OfferType fromValue(String value)
	{
		for (OfferType offer : values())
			if (value.equals(offer.value))
				return offer;

		throw new IllegalArgumentException("Неизвестный тип алгоритма предложения[" + value + "]");
	}
}
