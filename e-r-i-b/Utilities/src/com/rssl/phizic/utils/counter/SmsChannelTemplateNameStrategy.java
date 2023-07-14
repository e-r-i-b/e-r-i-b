package com.rssl.phizic.utils.counter;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Puzikov
 * @ created 19.05.15
 * @ $Author$
 * @ $Revision$
 *
 * Именование шаблонов в смс канале
 * PREFIX_1
 * PREFIX_2
 * PREFIX_3
 * ...
 *
 */

public class SmsChannelTemplateNameStrategy implements NamingStrategy
{
	private static final String REPLACEMENT = "_";

	public String transform(String name)
	{
		if (StringUtils.isEmpty(name))
			return name;

		//убираем недопустимые в смс канале символы
		String s = name.replaceAll("[^a-zA-Zа-яА-ЯёЁ_0-9]", REPLACEMENT);
		//первый символ не должен быть цифрой
		if (Character.isDigit(s.charAt(0)))
		{
			s = REPLACEMENT + s.substring(1);
		}
		//Формально тут еще нужна проверка на совпадение c алиасами смс команд и поставщиков.
		//Но команд вида *_№ точно нет, а поставщики с таким именем очень маловероятны, поэтому оставляем так.
		return s;
	}

	public String unify(Set<String> names, String standardName)
	{
		BigDecimal counter = getExistingCounter(names, standardName);
		return "_" + counter.add(BigDecimal.ONE).toString();
	}

	private BigDecimal getExistingCounter(Set<String> names, String standardName)
	{
		BigDecimal result = BigDecimal.ZERO;
		for (String name : names)
		{
			if (name.length() == standardName.length())         //Это значит, что строки идентичны,
			{                                                   //т.к отбираются только строки, содержащие standardName
				continue;
			}
			name = name.substring(standardName.length() + 1);   //Корень имени нам уже не нужен (вместе с левой скобкой).
			if (!name.matches("\\d+"))                          //в скобках должны быть только цифры.
			{
				continue;
			}
			BigDecimal iNum = new BigDecimal(name);             //Числовое значение (которое в скобках) из из списка.
			if (iNum.compareTo(result) > 0)
			{
				result = iNum;
			}
		}
		return result;
	}
}
