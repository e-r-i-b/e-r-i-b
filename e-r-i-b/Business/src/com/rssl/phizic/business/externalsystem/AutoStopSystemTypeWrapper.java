package com.rssl.phizic.business.externalsystem;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;
import com.rssl.phizic.gate.loans.Loan;

import java.util.HashMap;
import java.util.Map;

/**
 * Вспомогательный класс для получения типа внешней системы по классу продукта
 * @author Pankin
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class AutoStopSystemTypeWrapper
{
	private static final Map<Class, AutoStopSystemType> SYSTEM_MAP;

	static
	{
		SYSTEM_MAP = new HashMap<Class, AutoStopSystemType>(4);
		SYSTEM_MAP.put(Card.class, AutoStopSystemType.CARD);
		SYSTEM_MAP.put(Account.class, AutoStopSystemType.COD);
		SYSTEM_MAP.put(IMAccount.class, AutoStopSystemType.COD);
		SYSTEM_MAP.put(Loan.class, AutoStopSystemType.LOAN);
	}

	/**
	 * Возвращает тип ВС по классу продукта
	 * @param clazz класс продукта
	 * @return тип ВС
	 */
	public static AutoStopSystemType getSystemTypeByClass(Class clazz)
	{
		return SYSTEM_MAP.get(clazz);
	}
}
