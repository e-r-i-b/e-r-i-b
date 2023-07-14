package ru.softlab.phizicgate.rsloansV64.common;

import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Maleyev
 * @ created 11.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class DateSpanHelper
{
	/**
	 * Вернуть срок из типа+значение
	 * @param type - тип
	 * @param count - значение
	 * @return Срок
	 */
	public static DateSpan getDateSpan(String type, String count) throws GateException
	{
		// 0-месяцы
		if ("0".equals(type))
			return new DateSpan(0,Integer.valueOf(count),0);
		throw new GateException("Неизвестное значение типа периода :" +type);
	}
}
