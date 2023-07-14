package com.rssl.phizic.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @author gulov
 * @ created 24.11.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Склонение слов
 */
public class DeclensionUtils
{
	/**
	 * Склонение числительных.
	 * Рассмотрено три варианта (больше не бывает):
	 *   1 вариант - числительное заканчивается на число, которое находится в интервале от 10 до 20.
	 *   2 вариант - числительное заканчивается на 1.
	 *   3 вариант - числительное заканчивается на 2, 3, 4.
	 * В зависимости от варианта подставляется нужное окончание (endings) в слово word.
	 * @param number - числительное
	 * @param word - склоняемое слово
	 * @param endings1 - первое окончание
	 * @param endings2 - второе окончание
	 * @param endings3 - третье окончание
	 * @return - склоненное слово
	 */
	public static String numeral(long number, String word, String endings1, String endings2, String endings3)
	{
		int variant = variant(number);
		if (variant == 0)
			return word.concat(endings1);
		if (variant == 1)
			return word.concat(endings2);
		return word.concat(endings3);
	}

	private static int variant(long number)
	{
		long twoLast = number % 100;
		if (twoLast >= 10 && 20 >= twoLast)
			return 2;
		String last = StringUtils.right(String.valueOf(number), 1);
		if ("1".equals(last))
			return 0;
		if ("2".equals(last) || "3".equals(last) || "4".equals(last))
			return 1;
		return 2;
	}
}
