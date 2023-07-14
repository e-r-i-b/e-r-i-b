package com.rssl.phizic.utils.counter;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author osminin
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Экшен для формирования наименования со следующим номером
 *
 * PREFIX
 * PREFIX(1)
 * PREFIX(2)
 * ...
 *
 */
public class DefaultNamingStrategy implements NamingStrategy
{
	public String transform(String name)
	{
		return name;
	}

	/**
	 * Рассчитать очередной номер вида: (номер) или пустая строка, если имя еще не задействовано
	 * @param names список объектов, по которым производится подсчет
	 * @param standardName наименование, для которого получаем номер
	 * @return номер
	 */
	public String unify(Set<String> names, String standardName)
	{
		BigDecimal counter = new BigDecimal(-1);
		String number;      //Значение (которое в скобках) из имени из списка.
		for (String name : names)
		{
			if (name.length() == standardName.length())         //Это значит, что строки идентичны,
			{                                                   //т.к отбираются только строки, содержащие standardName
				if (counter.compareTo(BigDecimal.ZERO) < 0)
					counter = BigDecimal.ZERO;

				continue;
			}
			name = name.substring(standardName.length() + 1);   //Корень имени нам уже не нужен (вместе с левой скобкой).
			int rBkt = name.indexOf(")");                       //Позиция правой скобки.
			if (rBkt < 0)
			{
				continue;
			}
			number = name.substring(0, rBkt);                   //Вытаскиваем значение между скобок,
			if (!number.matches("\\d+"))                        //в скобках должны быть только цифры.
			{
				continue;
			}
			BigDecimal iNum = new BigDecimal(number);          //Числовое значение (которое в скобках) из из списка.
			if (iNum.compareTo(counter) > 0)
			{
				counter = iNum;
			}
		}

		number = (counter.compareTo(BigDecimal.ZERO) < 0) ? "" : "(" + counter.add(BigDecimal.ONE).toString() + ")";
		return number;
	}
}
