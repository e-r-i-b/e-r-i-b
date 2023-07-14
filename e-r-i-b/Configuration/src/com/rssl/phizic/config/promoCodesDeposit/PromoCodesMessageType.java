package com.rssl.phizic.config.promoCodesDeposit;

import com.rssl.phizic.utils.StringHelper;

import javax.management.InstanceNotFoundException;

/**
 * Тип отображаемого сообщения
 *
 * @ author: Gololobov
 * @ created: 18.12.14
 * @ $Author$
 * @ $Revision$
 */
public enum PromoCodesMessageType
{
	WARNING  ("Важный"),
	STANDART ("Стандарт"),
	INFO     ("Информационный");

	private String description;

	PromoCodesMessageType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public static PromoCodesMessageType getPromoCodesMessageType(String eventMessageType)
	{
		if (StringHelper.isEmpty(eventMessageType))
			return null;

		for (PromoCodesMessageType promoCodesMessageType : PromoCodesMessageType.values())
		{
			if (promoCodesMessageType.description.equalsIgnoreCase(eventMessageType))
				return promoCodesMessageType;
		}

		return null;
	}
}
