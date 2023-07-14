package com.rssl.phizic.utils.pattern;

import java.util.regex.Pattern;

/**
 * @author komarov
 * @ created 16.04.2013 
 * @ $Author$
 * @ $Revision$
 */

public class PatternUtils
{
	/**
	 * Метод из wildCard строки, да еще с дизъюнкцией через запятую, формирует регулярное выражение
	 * Строка состоит из множества масок разделенных запятой.
	 * Символ «?» маске соответствует любому символу
	 * Символ «*» - любому количеству любых символов.
	 * @param codes сторка из масок
	 * @return регулярное выражение.
	 */
	public static Pattern compileDepartmentsPatten(String codes)
	{
		StringBuffer regexp = new StringBuffer();
		regexp.append("^("); // начало строки и начало первой группы дизъюнкции
		for (int i = 0; i < codes.length(); i++)
		{
			char c = codes.charAt(i);
			switch (c)
			{
				//пребразуем спец символы
				case '*':
					regexp.append(".*");
					break;
				case '?':
					regexp.append(".");
					break;
				case ',':
					regexp.append(")|("); // закрываем предыдущую руппу вставляем оператор дизъюнкции и открываем сл группу
					break;
				//ескейпим ставшиеся спец символы
				case '(':
				case ')':
				case '[':
				case ']':
				case '$':
				case '^':
				case '.':
				case '{':
				case '}':
				case '|':
				case '\\':
					regexp.append("\\");
				default:
					regexp.append(c);
			}
		}
		regexp.append(")$");//конец последней гуппы и всей строки
		return Pattern.compile(regexp.toString());
	}
}
