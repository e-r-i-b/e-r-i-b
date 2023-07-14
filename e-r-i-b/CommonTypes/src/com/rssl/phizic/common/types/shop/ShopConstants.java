package com.rssl.phizic.common.types.shop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Erkin
 * @ created 24.05.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 *  онстанты ‘ормата ¬заимодействи€ с интернет-магазинами
 */
public class ShopConstants
{
	/**
	 * «начение дл€ пол€ "Ќаименование типа карты, с которой был оплачен документ", если карту определить не удалось
	 */
	public static final String UNKNOWN_CHARGE_OFF_CARD_TYPE = "unknown";

	/**
	 * @return используемый формат даты-времени
	 */
	public static DateFormat getDateFormat()
	{
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	}
}
