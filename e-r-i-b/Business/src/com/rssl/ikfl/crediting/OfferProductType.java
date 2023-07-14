package com.rssl.ikfl.crediting;

/**
 * @author Erkin
 * @ created 29.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Вид продукта
 */
public enum OfferProductType
{
	CONSUMER_CREDIT("Consumer Credit"),

	CREDIT_CARD("CreditCard"),

	CHANGE_LIMIT("Change Credit Limit")
	;

	/**
	 * Код CRM
	 */
	public final String crmCode;

	private OfferProductType(String crmCode)
	{
		this.crmCode = crmCode;
	}

	public static OfferProductType fromCRMCode(String crmCode)
	{
		for (OfferProductType type : values())
		{
			if (crmCode.equals(type.crmCode))
				return type;
		}
		throw new IllegalArgumentException("Неожиданный вид продукта: " + crmCode);
	}
}
