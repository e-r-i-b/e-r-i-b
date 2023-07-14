package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.config.SmsConfig;
import com.rssl.phizic.config.ConfigFactory;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 30.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Препроцессор СМС-запросов ЕРМБ
 * 1. Проверяет, есть ли в сообщении ключевые слова. Если есть, заменяет по соответствующим правилам
 * 2. Заменяет неизвестные символы на пробел
 * 3. Уплотняет пробелы: группу пробельных символов заменяет на один пробел
 * 4. Удаляет пробелы в начале и конце текста
 * 5. Заменяет парные пробелы на один
 */
class SmsPreprocessor
{
	static String preprocess(String text)
	{
		Collection<PreprocessorFilter> filters = ConfigFactory.getConfig(SmsConfig.class).getPreprocessorFilters();
		for(PreprocessorFilter filter : filters)
			text = filter.filter(text);

		StringBuilder sb = new StringBuilder(text.length());
		boolean lastSpace = false;
		for (int i=0; i<text.length(); i++)
		{
			char c = text.charAt(i);
			boolean space = Alphabet.isWhiteSpace(c);

			// Заменяем неизвестные символы на пробел
			space = space || Alphabet.isUnknownChar(c);

			if (space)
			{
				// Убираем пробелы в начале текста
				if (sb.length() == 0)
					continue;

				// Уплотняем подряд идущие пробельные символы до одного пробела
				if (lastSpace)
					continue;

				// Заменяем (разные) пробельные символы на пробел
				c = ' ';
			}

			lastSpace = space;
			sb.append(c);
		}

		// Убираем пробелы в конце текста
		if (lastSpace && sb.length() > 0)
			sb.deleteCharAt(sb.length()-1);

		return sb.toString();
	}

}
