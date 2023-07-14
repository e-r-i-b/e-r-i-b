package com.rssl.phizic.utils.counter;

import java.util.List;

/**
 * @author osminin
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Методы для работы со счетчиками
 */
public class CounterUtils
{
	/**
	 * Рассчитать очередной номер вида: (номер) или пустая строка, если имя еще не задействовано
	 * @param objects список объектов, по которым производится подсчет
	 * @param standardName наименование, для которого получаем номер
	 * @param action экземпляр NameCounterAction с определенным методом getName
	 * @return номер
	 */
	public static String calcNumber(List<?> objects, String standardName, NameCounterAction action)
	{
		return action.calcNumber(objects, standardName);
	}
}
