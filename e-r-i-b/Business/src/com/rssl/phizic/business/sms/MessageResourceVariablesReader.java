package com.rssl.phizic.business.sms;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Balovtsev
 * @version 20.05.13 16:48
 */
public final class MessageResourceVariablesReader
{
	private static final String[] RESERVED_WORDS        = {"true", "false", "gt", "gte", "lt", "lte", "as", "in", "using"};
	private static final char[]   DELIMITERS            = {'>', '<', '=', '&', '?', '|', '!', '/', ' '};
	private static final char     EXPRESSIONS_DELIMITER = ';';

	private int     textMark      = -1;
	private int     directiveMark = -1;

	private boolean closingTag;
	private boolean directive;
	private boolean userDirective;

	/**
	 *
	 */
	public MessageResourceVariablesReader()
	{
		Arrays.sort(DELIMITERS);
	}

	/**
	 *
	 * @param  template шаблон
	 * @return Set&lt;String&gt;
	 * @throws ParseException
	 */
	public Set<String> readVariables(final String template) throws ParseException
	{
		Set<String> expressions  = new HashSet<String>();

		StringBuffer dRawCharSet = new StringBuffer();
		StringBuffer tRawCharSet = new StringBuffer();

		char[] chars = template.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (isDirective(chars, i))
			{
				handleDirective(dRawCharSet, chars, i);

				//пока не кончилась директива читаем только её
				continue;
			}
			else
			{
				//кончилась, фиксируем все найденные выражения
				flushRawCharSet(dRawCharSet, expressions, true);
			}

			if (!isUserDirective() && !isClosingTag(chars, i))
			{
				//читаем символы между тэгами
				handleInnerText(tRawCharSet, chars, i);

				// это на тот случай, когда читаем шаблон без разметки. Нужно же где-то остановиться и сбросить накопленное
				if (i + 1 == chars.length)
				{
					flushRawCharSet(tRawCharSet, expressions, false);
				}
			}
			else
			{
				//начался новый тэг, значит нужно зафиксировать то что найдено
				flushRawCharSet(tRawCharSet, expressions, false);
			}
		}

		return filter(expressions);
	}

	private Set<String> filter(Set<String> expressions) throws ParseException
	{
		Set<String> variables = new HashSet<String>();
		for (String expression : expressions)
		{
			if (MessageResourceVariablesReaderUtil.hasUnclosed(expression, "'"))
			{
				throw new ParseException("Обнаружены незакрытые кавычки " + "'" + " в строке:\\n" + expression, 0);
			}

			if (MessageResourceVariablesReaderUtil.hasUnclosed(expression, "\""))
			{
				throw new ParseException("Обнаружены незакрытые кавычки " + "\"" + " в строке:\\n" + expression, 0);
			}

			if (MessageResourceVariablesReaderUtil.hasUnclosed(expression, "[", "]") ||
				MessageResourceVariablesReaderUtil.hasUnclosed(expression, "{", "}") ||
				MessageResourceVariablesReaderUtil.hasUnclosed(expression, "(", ")"))
			{
				throw new ParseException("Обнаружены незакрытые скобки в строке:\\n" + expression, 0);
			}

			if (!expression.startsWith("\"") && !expression.startsWith("'"))
			{
				boolean startDollar = expression.startsWith("$");
				boolean startBraces = expression.startsWith("{");

				if (startBraces || startDollar)
				{
					// выражение в скобках {} может содержать несколько переменных
					String[] values = expression.replaceAll("[${}]", "").split(Arrays.toString(DELIMITERS));

					for (String value : values)
					{
						value = value.trim();
						if (StringHelper.isNotEmpty(value))
						{
							if (startDollar && StringUtils.isNumeric(value))
							{
								continue;
							}

							variables.add(value.trim());
						}
					}
				}
				else
				{
					variables.add(expression);
				}
			}
		}

		return variables;
	}

	/*
	 * Метод для обработки директивы т.е например в такой строке:
	 * <#if someExpression.mayBePresent && someMethod.mayBePresent(something)>
	 */
	private void handleDirective(StringBuffer buffer, char[] chars, int position)
	{
		/*
		 * запоминаем все что начинается с кавычек, как с одинарных
		 */
		if (isQuoted(chars, position, '\''))
		{
			buffer.append(chars[position]);
			return;
		}

		/*
		 * так и с двойных
		 */
		if (isQuoted(chars, position, '\"'))
		{
			buffer.append(chars[position]);
			return;
		}

		/*
		 * Запоминаем выражения подозреваемые в том, что они методы, например someMethod.mayBePresent(something).
		 */
		if (isMethod(chars, position))
		{
			buffer.append(chars[position]);
			return;
		}

		/*
		 * запоминаем простые выражения, например someExpression.mayBePresent
		 */
		if (isSimpleExpression(chars, position))
		{
			buffer.append(chars[position]);
			return;
		}

		/*
		 * Если дошли до этого места значит выражение прочитано или еще не встретили его объявление. Если это все-таки
		 * было выражение то устанавливаем границу в буфере
         */
		fixVariable(buffer);
	}

	/*
	 * Метод для чтения того, что располагается между тэгов, например:
	 *
	 * <#if>I`m handle it</#if>
	 */
	private void handleInnerText(StringBuffer buffer, char[] chars, int position)
	{
		// Обработка выражений вида %s, %d ...
		if (isPercentMark(chars, position))
		{
			buffer.append(chars[position]);
		}

		// Обработка выражений вида {expression}
		if (isMark(chars, position, '{'))
		{
			buffer.append(chars[position]);
		}

		// Обработка выражений вида ${expression}
		if (isMark(chars, position, '$'))
		{
			buffer.append(chars[position]);
		}

        if (!isLastCharacterInnerTextVariable())
        {
            return;
        }

		/*
		 * Если дошли до этого места значит выражение прочитано или еще не встретили его объявление. Если это все-таки
		 * было выражение то устанавливаем границу в буфере
         */
		fixVariable(buffer);
	}

	private void flushRawCharSet(StringBuffer rawCharacterSet, Set<String> expressions, boolean skipFirst)
	{
		if (rawCharacterSet.length() == 0)
		{
			return;
		}

		//счетчик для количества найденных выражений
		int count = 0;

		//поехали
		for (String value : rawCharacterSet.toString().split( String.valueOf(EXPRESSIONS_DELIMITER) ))
		{
			//Пропускаем первое слово если нужно. А нужно это только для директивы, поскольку первое слово служебное
			if (count++ == 0 && skipFirst)
			{
				continue;
			}

			if (StringUtils.isNumeric(value))
			{
				continue;
			}

			boolean exclude = false;

			//исключить некоторые служебные слова
			for (String reserved : RESERVED_WORDS)
			{
				value = value.trim();
				if (reserved.equals(value))
				{
					exclude = true;
					break;
				}
			}

			if (exclude)
			{
				continue;
			}

			expressions.add(value);
		}

		/*
		 * После того как выражения запомнили:
		 * 1. Сбрасываем позиции меток;
		 * 2. Очищаем буфер.
		 */
		textMark      = -1;
		directiveMark = -1;

		rawCharacterSet.delete(0, rawCharacterSet.length());
	}

	/*
	 * Определяет то что записано в кавычках, чтобы при проверке удобнее было определять незакрытые кавычки.
	 * Кроме того все что записано в кавычках не добавляется в список найденных переменных.
	 */
	private boolean isQuoted(char[] chars, int position, char quote)
	{
		char current = chars[position];

		//если маркер уже на что-то установлен
		if (directiveMark > 0)
		{
			//то проверяем не кавычки ли это
			if (chars[directiveMark] == quote)
			{
				// если это так...

				// то проверяем текущий символ, если он тоже является кавычкой - сбрасываем маркер
				if (chars[position]  == quote)
				{
					directiveMark = -1;
				}

				// если это что-то из разделителей - сбрасываем маркер
				if (Arrays.binarySearch(DELIMITERS, current) > -1)
				{
					directiveMark = -1;
				}

				// выходим только тут, дабы сохранить оригинальную пунктуацию, т.е кавычки
				return true;
			}

			// оказалось что этот маркет указывает куда-то в нитуда, выходим
			return false;
		}

		//маркер ни на что не установлен, проверяем является ли текущий символ тем что мы ищем
		if (current == quote)
		{
			/*
			 * проверка того что перед текущим символом  что-то было
			 */
			if (position - 1 < 0)
			{
				// О.о жизнь началась здесь... странный шаблон какой-то. Скорее всего не содержит разметки
				directiveMark = position;
				return true;
			}

			/*
			 * если какой-то разделатель есть, то нашлось то что нужно
			 */
			if (Arrays.binarySearch(DELIMITERS, chars[position - 1]) > -1)
			{
				directiveMark = position;
				return true;
			}
		}

		return false;
	}

	/*
	 * Определяет простое выражение вида expression.mayBePresent
	 */
	private boolean isSimpleExpression(char[] chars, int position)
	{
		char current = chars[position];

		// смотрим на значение метки возможно мы уже читаем выражение
		if (directiveMark > -1)
		{
			//если метка указывает на символ то это простая переменная
			if (Character.isLetter(chars[directiveMark]))
			{
				// если какой-то разделитель(проверка на символ ')' нужна, когда несколько переменных сгруппированно)...
				if (Arrays.binarySearch(DELIMITERS, current) > -1 || current == ')')
				{
					// заканчиваем читать
					directiveMark = -1;
					return false;
				}

				// продолжаем читать
				return true;
			}
		}

		// всякое простое выражение начинается с буквы
		if (Character.isLetter(current))
		{
			for (int i = position; i < chars.length; i++)
			{
				// и может содержать ряд символов
				if (Character.isLetter(chars[i]) || chars[i] == '.' || chars[i] == '_')
				{
					continue;
				}

				// и не может содержать разделители
				if (Arrays.binarySearch(DELIMITERS, chars[i]) > -1)
				{
					directiveMark = position;
					return true;
				}
			}
		}

		return false;
	}

	private boolean isMethod(char[] chars, int position)
	{
		// если уже определили метод, то нужно определить скобку его закрывающую
		if (directiveMark > -1)
		{
			if (chars[directiveMark] == '(')
			{
				// конец метода - закрывающаяся скобка, но не всегда...
				if (chars[position] == ')')
				{
					int bracesCount = 0;
					//начинаем считать скобки
					for (int i=directiveMark; i<position; i++)
					{
						if (chars[i] == '(')
						{
							bracesCount += 1;
							continue;
						}

						if (chars[i] == ')')
						{
							bracesCount -= 1;
						}
					}

					// если их не хватает, то это никакой не конец метода
					directiveMark = bracesCount == 0 ? -1 : directiveMark;
				}

				// засчитываем пробел потому что внутри скобок метода может быть все что угодно, кроме символов из DELIMITERS
				if (chars[position] == ' ')
				{
					return true;
				}

				// конец метода - разделитель
				if (Arrays.binarySearch(DELIMITERS, chars[position]) > -1)
				{
					directiveMark = -1;
					return false;
				}

				return true;
			}

			return false;
		}

		// выясняем метод ли это
		if (Character.isLetter(chars[position]))
		{
			for (int i = position; i < chars.length; i++)
			{
				// присутствует ли открывающаяся скобка после символа, если да то это метод
				if (chars[i] == '(')
				{
					directiveMark = i;
					return true;
				}

				// если встречаем что-то из разделителей то никакой это не метод
				if (Arrays.binarySearch(DELIMITERS, chars[i]) > -1)
				{
					directiveMark = -1;
					return false;
				}
			}
		}

		return false;
	}

	private boolean isMark(char[] chars, int position, char markChar)
	{
		if (textMark == -1)
		{
			//встетили начало выражения
			if (chars[position] == markChar)
			{
				textMark = position;
				return true;
			}
		}
		else
		{
			if (chars[textMark] == markChar)
			{
				//завершаем чтение при нормальных условиях т.е выражение описано вот так -> {expression}
				if (chars[position] == '}')
				{
					textMark = -1;
				}

				/*
				 * Если оказалось что закрывающей скобки нету вообще, то приходится читать либо до начала
				 * следующего выражения, либо до начала директивы
				 */
				if (textMark > -1)
				{
					// последним символом в массиве оказался, а скобки все равно нет :(
					if (textMark + 1 == chars.length)
					{
						textMark = -1;
						return false;
					}

					// дальше ничего нету
					if (position + 1 == chars.length)
					{
						textMark = -1;
						return false;
					}

					// или начало нового выражения оказалось
					char nextChar = chars[position + 1];
					if (nextChar == '$' || nextChar == '{')
					{
						textMark = -1;
						return false;
					}
				}

				return true;
			}
		}

		return false;
	}

	private boolean isPercentMark(char[] chars, int position)
	{
		if (textMark == -1)
		{
			if (chars[position] == '%')
			{
				//запоминаем позицию в которой найдено начало выражения, если это вообще выражение
				int nextCharIndex = position + 1;
				if (nextCharIndex < chars.length && Character.isLetter(chars[nextCharIndex]))
				{
					textMark = position;
				}

				return true;
			}
		}
		else
		{
			if (chars[textMark] == '%')
			{
				if (Character.isLetter(chars[position]))
				{
					return true;
				}

				textMark = -1;
			}
		}

		return false;
	}

	private void fixVariable(StringBuffer characterSet)
	{
		if (characterSet.length() > 0)
		{
			if (characterSet.charAt(characterSet.length() - 1) == EXPRESSIONS_DELIMITER)
			{
				return;
			}

			characterSet.append(EXPRESSIONS_DELIMITER);
		}
	}

	private boolean isClosingTag(char[] chars, int position)
	{
		if (chars[position] == '<')
		{
			for (int i= position+1; i<chars.length; i++)
			{
				if (chars[i] == ' ')
				{
					continue;
				}

				closingTag = (chars[i] == '/');
				break;
			}
		}

		if (chars[position] == '>')
		{
			if (closingTag)
			{
				closingTag = !closingTag;
			}
		}

		return closingTag;
	}

	private boolean isDirective(char[] chars, int position)
	{
		if (chars[position] == '<')
		{
			for (int i= position+1; i<chars.length; i++)
			{
				if (chars[i] == ' ')
				{
					continue;
				}

				directive     = (chars[i] == '#');
				userDirective = (chars[i] == '@');
				break;
			}
		}

		if (chars[position] == '>')
		{
			if (directive)
			{
				directive = !directive;
			}

			if (userDirective)
			{
				userDirective = !userDirective;
			}
		}

		return directive;
	}

	private boolean isUserDirective()
	{
		return userDirective;
	}

    public boolean isLastCharacterInnerTextVariable()
    {
        return textMark == -1;
    }
}