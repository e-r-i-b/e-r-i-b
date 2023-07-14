package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;

/**
 * @author Jatsky
 * @ created 19.11.14
 * @ $Author$
 * @ $Revision$
 */

public class MaskHelper
{
	private static final String OLD_CARD_NUMBER_MASK = "*";            //маскировка карты старая (оставлен для: atm, mAPI, sms, push, email)
	private static final String CARD_NUMBER_MASK = "•";                //маскировка карты

	/**
	 * Получить символ для маскировки номера карты
	 * @return паттерн
	 */
	public static String getActualMaskSymbol()
	{
		if (ApplicationInfo.getCurrentApplication() == Application.PhizIC)
			return CARD_NUMBER_MASK;
		return OLD_CARD_NUMBER_MASK;
	}

	public static String getOldCardNumberMask()
	{
		return OLD_CARD_NUMBER_MASK;
	}
}
