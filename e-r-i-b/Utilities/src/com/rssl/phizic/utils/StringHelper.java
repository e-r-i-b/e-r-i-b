package com.rssl.phizic.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Pattern;

import static org.apache.commons.lang.StringUtils.*;

/**
 * @author Kidyaev
 * @ created 10.10.2005
 * @ $Author: akimov $
 * @ $Revision$
 */
public class StringHelper
{
	private static final Log log = LogFactory.getLog(StringHelper.class);
	private static final String[] EMPTY_STRING_ARRAY = new String[0];
	private static final char GROUPING_SEPARATOR = ' ';
	private static final Pattern NUMBERS_PATTERN = Pattern.compile("\\d+");
	private static final Pattern SPASE_PATTERN = Pattern.compile("\\s+");
	private static final String DEFAULT_ENCODING = "windows-1251";

	/**
	 * @return пустой массив строк
	 */
	public static String[] emptyStringArray()
	{
		return EMPTY_STRING_ARRAY;
	}

	public static String getNullIfEmpty ( String str )
	{
		if (str == null)
		{
			return null;
		}
		return str.equals(EMPTY) ? null : str;
	}

	public static String getEmptyIfNull ( String str )
	{
		return str == null ? EMPTY : str;
	}

	/**
	 * @param value объект для преобразования в строку.
	 * @return в случае null, пустая строка, иначе toString
	 */
	public static String getEmptyIfNull(Object value)
	{
	   return value == null ? EMPTY : getEmptyIfNull(value.toString());
	}

	/**
	 * @param value объект для преобразования в строку.
	 * @return в случае null - null, иначе toString
	 */
	public static String getNullIfNull(Object value)
	{
	   return value == null ? null: getEmptyIfNull(value.toString());
	}

	public static String getZeroIfEmptyOrNull ( String str )
	{
		if ((str == null) || (str.length() == 0))
		{
			return "0";
		}
		return str;
	}

	/**
	 * Удаление ведущих нулей
	 * @param str
	 * @return
	 */
	public static String removeLeadingZeros ( String str )
	{
		if (StringHelper.isEmpty(str))
			return str;
		
		str = org.apache.commons.lang.StringUtils.stripStart(str, "0");
		return StringHelper.isEmpty( str ) ? "0" : str;
	}

	/**
	 * Дополняем строку слева нулями до указанной длины
	 * @param s
	 * @param needLength
	 * @return
	 */
	public static String appendLeadingZeros ( String s, int needLength )
	{
		int len = 0;
		if (s != null)
		{
			len = s.length();
		}
		else
		{
			// TODO всегда ли правильно не писать null после прибавления нулей в начало?
			s = EMPTY;
		}
		String add = EMPTY;

		while (len+add.length()<needLength)
		{
			add += "0";
		}

		return add+s;
	}

	/**
	 * Дополняем строку справа нулями до указанной длины
	 * @param s
	 * @param needLength
	 * @return
	 */
	public static String appendEndingZeros ( String s, int needLength )
	{
		if (s == null)
		{
			// TODO всегда ли правильно не писать null после прибавления нулей в начало?
			s = EMPTY;
		}
		while (s.length()<needLength)
		{
			s += "0";
		}
		return s;
	}

	/**
	 * Убирает перевод на новую строку.
	 * @param fix строка из которой нужно удалить лишние символы
	 * @return результат после удаления символов перевода строки
	 */
	public static String fixLineTransfer ( String fix )
	{
		if (fix == null)
		{
			return fix;
		}
		return fix.replaceAll("\\u000D", EMPTY).replaceAll("\\u000A", EMPTY);
	}

	/**
	 * Перезаписывает символы перевода строки на символ <br/>, поскольку с html они не дружат.
	 * Поскольку в Windows перевод строки это символы \r\n то дополнительно ищем \r(\u000D)
	 * в Unix перевод строки просто \n(\u000A)
	 * Экранирует слеш в теге </script>
	 * @param str - входная строка
	 * @param usedInJS - используется в JavaScript (true) или html (false)
	 * @return результат после замещения символов
	 */
	public static String formatStringForJavaScript ( String str, boolean usedInJS )
	{
		try
		{
			if (str == null)
			{
				return str;
			}
			String result = str.replace("\\", "\\\\").replaceAll("</script>", "<\\\\/script>").replaceAll("\\u000D", "").replace("\"", "\\\"").replace("\'", "\\\'");
			if (usedInJS)
			{
				return result.replaceAll("\\\\n", "\\n").replaceAll("\\u000A", "\\\\n");
			}
				
			return result.replaceAll("\\u000A", "<br/>");
		}
		catch (Exception e)
		{
			log.error("Ошибка замены символа обратного слеша", e);
			return EMPTY;
		}
	}

	/**
	 * Экранирование одинарных кавычек для корректного отображения строк в js-коде
	 * @param str - строка для обработки
	 * @return строка с экранированными кавычками
	 */
	public static String escapeStringForJavaScript(String str)
	{
		if (isEmpty(str))
			return str;

		// экранирование кавычек
		return str.replace("'", "\\\'");
	}

	public static boolean equals ( String pin, String oldPin )
	{
		try
		{
			boolean result = false;

			if ((pin==null) && (oldPin==null))
			{
				result = true;
			}
			else if (pin!=null)
			{
				result = pin.equals(oldPin);
			}

			return result;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Стравнение двух строк (допускается значение null для строк)
	 * @param str1 первая строка
	 * @param str2 вторая строка
	 * @return результат сравнения строк
	 */
	public static int compare(String str1, String str2)
	{
		if (str1 == null && str2 == null)
			return 0;

		if (str1 == null)
			return -1;

		if (str2 == null)
			return 1;

		return str1.compareTo(str2);
	}

	/**
	 * Сравнение длин строк
	 * @param str1 первая строка
	 * @param str2 вторая строка
	 * @return результат сравнения строк
	 */
	public static int compareLength(String str1, String str2)
	{
		int len1;
		int len2;

		if (str1 == null)
			len1 = 0;
		else
			len1 = str1.length();

		if (str2 == null)
			len2 = 0;
		else
			len2 = str2.length();

		if (len1 < len2)
			return -1;
		else if (len1 == len2)
			return 0;
		else
			return 1;
	}

	/**
	 * Расчет контрольного разряда ( для расчета контрольного разряда банковской карты и карты ключей )
	 * @param value номер содержащий только символы от '0' до '9' БЕЗ контрольного разряда
	 * @return вычесленный котрольный разряд ('0' до '9')
	 */
	public static char calculateCheckDigit(String value)
	{
		int zero = '0';

		char[] chars = value.toCharArray();

		int checksum = 0;

		for (int i = 0; i < chars.length; i++)
		{
			int currentDigitCost = chars[i] - zero;

			if (i % 2 != value.length() % 2)
			{
				currentDigitCost *= 2;
				currentDigitCost = currentDigitCost % 10 + (currentDigitCost < 10 ? 0 : 1);
			}

			checksum += currentDigitCost;
		}

		checksum %= 10;

		checksum = checksum == 0 ? 0 : 10 - checksum;
		checksum = zero + checksum;
		return (char) checksum;
	}

	public static boolean isEmpty(String value)
	{
		return value==null || value.length()==0;
	}

	public static boolean isNotEmpty(String value)
	{
		return !isEmpty(value);
	}

	public static String replaceQuotes(String value)
	{
		if (value == null || value.length() == 0)
			return value;
		else
			return value.replace("«", "\"").replace("»", "\"");
	}

	/**
	 * Удаляет все неаполненные строки
	 *  (null, empty, содержащие только пробельные символы)
	 * из коллекции
	 * @param col - коллекция
	 */
	public static void removeBlankStrings(Collection<String> col) {
		List<String> removing = new LinkedList<String>();
		for (String string : col) {
			if (isBlank(string))
				removing.add(string);
		}
		col.removeAll(removing);
	}

	/**
	 * Разбивает текст на строки,
	 * каждая из которых по длине не превышает указанный порог.
	 * Если слово не помещается в строке,
	 * по возможности переносит его на следующую строку
	 * @param text - текст
	 * @param lineMaxLength - максимально допустимая длина строки
	 * @param skipEmptyLines - исключать из результата строки,
	 *  содержащие исключительно пробельные символы
	 * @return массив строк
	 */
	public static String[] splitToLines(String text, int lineMaxLength, boolean skipEmptyLines)
	{
		if (text == null)
			throw new NullPointerException("Argument 'text' cannot be null");
		if (lineMaxLength < 1)
			throw new IllegalArgumentException("Argument 'lineMaxLength' must be positive");

		if (isBlank(text))
			return emptyStringArray();

		final int textLength = text.length();
		if (textLength <= lineMaxLength)
			return new String[] { text };

		List<String> lines = new LinkedList<String>();
		StringBuilder lineBuilder = new StringBuilder(textLength);
		int lastSpaceIndex = -1;
		for (int i=0; i<textLength; i++) {
			char letter = text.charAt(i);

			if (Character.isWhitespace(letter))
				lastSpaceIndex = i;

			lineBuilder.append(letter);
			if (lineBuilder.length() == lineMaxLength) {
				// последнее слово переносим на следующую строку
				if (lastSpaceIndex >= 0) {
					lineBuilder.delete(
							lineBuilder.length() - (i-lastSpaceIndex) - 1,
							lineBuilder.length());
					i = lastSpaceIndex;
				}

				String line = lineBuilder.toString();
				if (skipEmptyLines)
					line = stripToNull(line);
				else line = stripStart(line, " ");
				if (line != null)
					lines.add(line);

				lineBuilder = new StringBuilder(textLength);
				lastSpaceIndex = -1;
			}
		}
		if (lineBuilder.length() > 0)
			lines.add(lineBuilder.toString());
		return lines.toArray(new String[lines.size()]);
	}

	/**
	 * Обрезка строки по максимально допустимой длине
	 * @param string - строка
	 * @param limit - ограничение на длину
	 * @return
	 */
	public static String truncate(String string, int limit)
	{
		if (string == null)
			return null;
		if (limit < 0)
			throw new IllegalArgumentException("Argument 'limit' cannot be negative");
		if (limit == 0)
			return EMPTY;
		if (limit >= string.length())
			return string;
		return string.substring(0, limit);
	}

	/**
	 * Маскирует часть текста, заменяя его звёздочками
	 * @param text - текст
	 * @return замаскированный текст
	 */
	public static String maskText(String text)
	{
		if (text == null)
			return null;
		return repeat("*", text.length());
	}

	/**
	 * Возвращает строку с разделителем групп разрядов
	 * @param text - исходная строка
	 * @return  строка нужного формата
	 */
	public static String getStringInNumberFormat(String text)
	{
		StringBuffer strBuffer = new StringBuffer(text);
		int point = strBuffer.indexOf(".");
		int startPos = point > 0? point : strBuffer.length();

		for (int i = startPos-3; i>0; i -= 3)
		{
			strBuffer.insert(i, GROUPING_SEPARATOR);
		}
		return strBuffer.toString();
	}

	/**
	 * Вставляет разделители групп разрядов и округляет до сотых
	 * @param text - строка с числом
	 * @return строка с числом, у которого вставлены разделители групп разрядов и округленным до сотых
	 */
	public static String roundNumberStringAndAddSeparator(String text)
	{
		double number = Double.parseDouble(text);
		number = Math.round(number*100)/100.0;

		return getStringInNumberFormat(Double.toString(number));
	}

	/**
	 * Добавляет лидирующие нули к строке
	 * @param string - строка
	 * @param length - минимальная длина результирующей строки
	 * @return строка длиной <length> с нулями спереди, если <string> короче <length>, иначе <string> 
	 */
	public static String addLeadingZeros(String string, int length)
	{
		if (string == null)
			throw new NullPointerException("Аргумент 'string' не может быть null");
		if (string.length() >= length)
			return string;

		StringBuilder sb = new StringBuilder(length-string.length());
		for (int i = length-string.length(); i>0; i--)
			sb.append('0');
		return sb.append(string).toString();
	}

	/**
	 * Добавляет лидирующие нули к каждой строке списка строк
	 * @param list - список строк
	 * @param length - минимальная длина результирующей строки
	 * @return список строк длиной <length> с нулями спереди, если <string> короче <length>, иначе <string>
	 */
	public static List<String> addLeadingZeros(List<String> list, int length)
	{
		if (CollectionUtils.isEmpty(list))
		{
			return list;
		}

		List<String> result = new ArrayList<String>(list.size());
		for (String s : list)
		{
			if (isNotEmpty(s))
			{
				result.add(addLeadingZeros(s, length));
			}
		}

		return result;
	}

	/**
	 * Дополнение справа к строке string символов appendix до length символов
	 * @param string исходная строка
	 * @param appendix добавляемый символ
	 * @param length требуемый размер строки
	 * @return дополненная справа строка string символом appendix до length символов
	 * Если length меньше длины строки string, то возвращается string
	 */
	public static String append(String string, char appendix, int length)
	{
		if (string == null)
			throw new NullPointerException("Аргумент 'string' не может быть null");
		if (string.length() >= length)
			return string;
		
		StringBuilder sb = new StringBuilder(length);
		sb.append(string);

		for(int i = length - string.length(); i > 0; i--)
			sb.append(appendix);

		return sb.toString();
	}

	/**
	 * Преобразует список строк в одну строку, через разделитель.
	 * @param stringList входной список
	 * @param delimiter разделитель
	 * @return
	 */
	public static String stringListToString(List<String> stringList, char delimiter)
	{
		StringBuilder sb = new StringBuilder();
		for(String element: stringList)
		{
			sb.append(element).append(delimiter);
		}
		return sb.toString();
	}

	/**
	 * Преобразует список строк в одну строку, через разделитель. После последнего элемента разделитель не добавляется.
	 * @param stringList входной список
	 * @param delimiter разделитель
	 * @return итоговая строка
	 */
	public static String stringListToStringWithoutFinalDelimiter(List<String> stringList, String delimiter)
	{
		if (CollectionUtils.isEmpty(stringList))
		{
			return EMPTY;
		}

		StringBuilder sb = new StringBuilder();
		for(String element: stringList)
		{
			if (sb.length() > 0)
				sb.append(delimiter);
			sb.append(element);
		}
		return sb.toString();
	}

	/**
	 * Сравнение строк без учета различия между пустой строкой и null(т.е. пустая строка и null равны)
	 * @param str1 первая строка
	 * @param str2 вторая строка
	 * @return true строки равны
	 */
	public static boolean equalsNullIgnore(String str1, String str2)
	{
		return getEmptyIfNull(str1).equals(getEmptyIfNull(str2));
	}

	/**
	 * Разбивает строку на массив подстрок заданной длины.
	 * Последняя подстрока может быть меньшей длины
	 * @param source строка
	 * @param chunkLength длина
	 * @return массив строк
	 */
	public static String[] chunk(String source, int chunkLength)
	{
		char[] chars = source.toCharArray();
		String[] result = new String[chars.length / chunkLength + (chars.length % chunkLength == 0 ? 0 : 1)];
		int i;
		for (i = 0; i < result.length - 1; i++)
			result[i] = new String(chars, i * chunkLength, chunkLength);
		result[result.length - 1] = new String(chars, i * chunkLength, chars.length - i * chunkLength);
		return result;
	}

	/**
	 * Преобразует дату в виде строки формата xsd:datetime к виду 'dd.mm.yyyy HH:MM'
	 * @param date строка формата xsd:datetime
	 * @return  строка вида   'dd.mm.yyyy HH:MM'
	 */
	public static String formatDateStringAirlinePayment(String date)
	{
		String regex = "^(\\d{4})-(\\d{2})-(\\d{2})T*(\\d{2}):(\\d{2}):\\d{2}(\\.\\d{1,6})?(Z|((-|\\+)\\d{2}:\\d{2}))?$";
		String replace = "$3.$2.$1 $4:$5";
		return date.replaceAll(regex, replace);
	}


	private static void appendChars(StringBuilder sb, char appendChar, int appendCount)
	{
		for(int i=0;i<appendCount;i++)
		{
			sb.append(appendChar);
		}
	}

	/**
	 * Заменяет пробелы на nbsp. Полезно если опираться на то, что пробелы nbsp непереносимые в html
	 * @param str - входная строка
	 * @return
	 */
	public static String changeWhiteSpaces(String str)
	{
		return str.replaceAll(" ","&nbsp;");
	}

	/**
	 * Максимальное количество подряд идущих символов
	 * @param value строка по которой ищем повторения
	 * @param isCaseSensitive true - проверять с учетом регистра, false -  проверять без учета регистра
	 * @return количество количество подряд идущих символов
	 */
	public static int maxCountRepeateChar(String value, boolean isCaseSensitive)
	{
		int maxSize = 1;
		int counter = 1;
		char currentChar = value.charAt(0);

		// начинаем со второго символа, т.к. для первого символа уже все проиниализировали
		for(int i = 1; i < value.length(); i++)
		{
			char ch = value.charAt(i);
			if((isCaseSensitive && currentChar == ch) 
					|| (!isCaseSensitive && Character.toUpperCase(currentChar) == Character.toUpperCase(ch)))
			{
				counter++;
				if(counter > maxSize)
					maxSize = counter;
			}
			else
			{
				counter = 1;
				currentChar = ch;
			}
		}

		return maxSize;
	}

	/**
	 * Сравнение двух строк без учета регистра и пробелов (как по краям, так и внутри)
	 * @param str1 строка1
	 * @param str2 строка2
	 * @return true - строки равны без учета регистра и пробелов (как по краям, так и внутри)
	 */
	public static boolean equalsIgnoreCaseStrip(String str1, String str2)
	{
		return equalsIgnoreCase(deleteWhitespace(str1), deleteWhitespace(str2));
	}

	/**
	 * Возвращает имя персоны в "нормализованном" виде:
	 * без пробелов и дефисов (внутри и снаружи) и в верхнем регистре.
	 * @param name - ненормализованное имя / фамилия / отчество (can be null)
	 * @return нормализованное имя / фамилия / отчество
	 */
	public static String normalizePersonName(String name)
	{
		return replaceChars(name, " -", "").toUpperCase();
	}

	/**
	 * Сравнение имён персоны.
	 * Подходит для сравнения имени, фамилии и отчества.
	 * При сравнении не учитывается регистр, пробелы и дефисы (как по краям, так и внутри).
	 * Имена не равны, если одно из них - null или пусто.
	 * @param name1 - имя раз (can be null)
	 * @param name2 - имя два (can be null)
	 * @return true, если name1 и name2 - это одно и тоже имя
	 */
	public static boolean equalsAsPersonName(String name1, String name2)
	{
		if (isBlank(name1) || isBlank(name2))
			return false;

		return normalizePersonName(name1).equals(normalizePersonName(name2));
	}

	public static String convertCp1251ToUtf8(String str) throws UnsupportedEncodingException
	{
		if (isEmpty(str))
			return str;

		return new String(str.getBytes("Cp1251"), "UTF-8");
	}

	/**
	 * @param string - строка
	 * @return true, если на входе строка, состоящая только из цифр
	 */
	public static boolean isNumericString(String string)
	{
		if (isEmpty(string))
			return false;
		return NUMBERS_PATTERN.matcher(string).matches();
	}
	/**
	 * Tests if this string starts with the specified prefix
	 * @param str строка для проверки
	 * @param with префикс
	 * @return true - если строка начинается с префикса, иначе false
	 * @throws UnsupportedEncodingException
	 */
	public static boolean startsWith(String str, String with) throws UnsupportedEncodingException
	{
		return str.startsWith(with);
	}

	/**
	 * Заменяет символы по регулярному выражению
	 * @param s исходная строка
	 * @param r на что заменяем
	 * @param p паттерн
	 * @return результирующая строка
	 */
	public static String replace(String s, String r, String p)
	{
		Pattern pattern = Pattern.compile(p);
		return pattern.matcher(s).replaceAll(r);
	}

	private static String replace(String s, String r, Pattern p)
	{
		return p.matcher(s).replaceAll(r);
	}

	/**
	 * Убирает лишние проблеы
	 * @param s исходная строка
	 * @return результирующая строка
	 */
	public static String replaceSpaces(String s)
	{
		return replace(s, " ", SPASE_PATTERN);
	}

	/**
	 * Возвращает список со строками без пробелов.
	 * Переданная коллекция не меняется.
	 * @param strings коллекция
	 * @return список без пробелов
	 */
	public static List<String> deleteWhitespaces(Collection<String> strings)
	{
		if (strings == null)
			return Collections.emptyList();

		List<String> result = new ArrayList<String>(strings.size());
		for (String string : strings)
		{
			result.add(deleteWhitespace(string));
		}
		return result;
	}

	/**
	 * Возвращает все постфиксы слова относительно разделителей
	 * @param str исходная строка
	 * @param separators разделители
	 * @param minLength минимальный размер постфикса
	 * @return постфиксы (нет - пустой список)
	 */
	public static List<String> getPostfixes(String str, String separators, int minLength)
	{
		if (isEmpty(str))
			return Collections.emptyList();
		if (isEmpty(separators))
			throw new IllegalArgumentException("набор разделителей не может быть пуст");
		if (minLength < 1)
			throw new IllegalArgumentException("минимальная длина не положительна");

		int length = str.length();
		if (length <= minLength)
			return Collections.emptyList();

		List<String> postfixes = new ArrayList<String>();

		int i = length - 1;
		while (i >= 0)
		{
			//пропустить разделители
			while (i >= 0 && separators.indexOf(str.charAt(i)) >= 0)
				i--;

			//найти начало постфикса
			while (i >= 0 && separators.indexOf(str.charAt(i)) < 0)
				i--;

			int postfixStartIndex = i + 1;
			if (i != -1 && length - postfixStartIndex >= minLength)
			{
				postfixes.add(str.substring(postfixStartIndex));
			}
		}

		return postfixes;
	}

	/**
	 * Удаляет символы из строки, удовлетворяющие указаному предикату
	 * @param string - строка (can be null can be empty)
	 * @param predicate - предикат (never null)
	 * @return строка без удалённых символов
	 */
	public static String removeChars(String string, Predicate predicate)
	{
		if (isEmpty(string))
			return string;

		StringBuilder sb = new StringBuilder(string.length());
		for (int i=0; i<string.length(); i++)
		{
			char c = string.charAt(i);
			if (predicate.evaluate(c))
				sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * Выполняет замену каждого символа, указанного в charForReplace на символ replaceTo в строке src.
	 *
	 * @param src исходная строка.
	 * @param charForReplace список символов для замены.
	 * @param replaceTo заменить на этот символ.
	 * @return результат замены.
	 */
	public static String replace(String src, String charForReplace, char replaceTo)
	{
		Set<Character> chars = new HashSet<Character>();
		for (char ch : charForReplace.toCharArray())
		{
			chars.add(ch);
		}

		StringBuilder sb = new StringBuilder(src.length());
		for (char ch : src.toCharArray())
		{
			if (chars.contains(ch))
				sb.append(replaceTo);
			else
				sb.append(ch);
		}

		return sb.toString();
	}

	/**
	 * Выполняет замену каждого символа из интервала от firstSymbol до lastSymbol на символ replaceTo в строке src.
	 *
	 * @param src исходная строка.
	 * @param firstSymbol начало интервала замены.
	 * @param lastSymbol конец интервала замены.
	 * @param replaceTo заменить на этот символ.
	 * @return рузальтат замены.
	 */
	public static String replaceSomeSymbols(String src, char firstSymbol, char lastSymbol, char replaceTo)
	{
		StringBuilder sb = new StringBuilder(lastSymbol - firstSymbol + 1);
		for (char ch = firstSymbol; ch <= lastSymbol; ch++)
		{
			sb.append(ch);
		}
		return replace(src, sb.toString(), replaceTo);
	}

	/**
	 * Метод предназначен для замены не безопасных символов URL на "%" и два шестнадцатиричных числа.
	 * @param url URL, который требуется экранировать
	 * @return Экранированный URL
	 * @throws UnsupportedEncodingException Исключение бросается, если система не поддерживает кодировку Windows-1251
	 */
	public static String encodeURL(String url) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(url, DEFAULT_ENCODING);
	}
}
