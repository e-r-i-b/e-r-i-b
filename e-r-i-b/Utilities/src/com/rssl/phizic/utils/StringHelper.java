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
	 * @return ������ ������ �����
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
	 * @param value ������ ��� �������������� � ������.
	 * @return � ������ null, ������ ������, ����� toString
	 */
	public static String getEmptyIfNull(Object value)
	{
	   return value == null ? EMPTY : getEmptyIfNull(value.toString());
	}

	/**
	 * @param value ������ ��� �������������� � ������.
	 * @return � ������ null - null, ����� toString
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
	 * �������� ������� �����
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
	 * ��������� ������ ����� ������ �� ��������� �����
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
			// TODO ������ �� ��������� �� ������ null ����� ����������� ����� � ������?
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
	 * ��������� ������ ������ ������ �� ��������� �����
	 * @param s
	 * @param needLength
	 * @return
	 */
	public static String appendEndingZeros ( String s, int needLength )
	{
		if (s == null)
		{
			// TODO ������ �� ��������� �� ������ null ����� ����������� ����� � ������?
			s = EMPTY;
		}
		while (s.length()<needLength)
		{
			s += "0";
		}
		return s;
	}

	/**
	 * ������� ������� �� ����� ������.
	 * @param fix ������ �� ������� ����� ������� ������ �������
	 * @return ��������� ����� �������� �������� �������� ������
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
	 * �������������� ������� �������� ������ �� ������ <br/>, ��������� � html ��� �� ������.
	 * ��������� � Windows ������� ������ ��� ������� \r\n �� ������������� ���� \r(\u000D)
	 * � Unix ������� ������ ������ \n(\u000A)
	 * ���������� ���� � ���� </script>
	 * @param str - ������� ������
	 * @param usedInJS - ������������ � JavaScript (true) ��� html (false)
	 * @return ��������� ����� ��������� ��������
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
			log.error("������ ������ ������� ��������� �����", e);
			return EMPTY;
		}
	}

	/**
	 * ������������� ��������� ������� ��� ����������� ����������� ����� � js-����
	 * @param str - ������ ��� ���������
	 * @return ������ � ��������������� ���������
	 */
	public static String escapeStringForJavaScript(String str)
	{
		if (isEmpty(str))
			return str;

		// ������������� �������
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
	 * ���������� ���� ����� (����������� �������� null ��� �����)
	 * @param str1 ������ ������
	 * @param str2 ������ ������
	 * @return ��������� ��������� �����
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
	 * ��������� ���� �����
	 * @param str1 ������ ������
	 * @param str2 ������ ������
	 * @return ��������� ��������� �����
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
	 * ������ ������������ ������� ( ��� ������� ������������ ������� ���������� ����� � ����� ������ )
	 * @param value ����� ���������� ������ ������� �� '0' �� '9' ��� ������������ �������
	 * @return ����������� ���������� ������ ('0' �� '9')
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
			return value.replace("�", "\"").replace("�", "\"");
	}

	/**
	 * ������� ��� ������������ ������
	 *  (null, empty, ���������� ������ ���������� �������)
	 * �� ���������
	 * @param col - ���������
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
	 * ��������� ����� �� ������,
	 * ������ �� ������� �� ����� �� ��������� ��������� �����.
	 * ���� ����� �� ���������� � ������,
	 * �� ����������� ��������� ��� �� ��������� ������
	 * @param text - �����
	 * @param lineMaxLength - ����������� ���������� ����� ������
	 * @param skipEmptyLines - ��������� �� ���������� ������,
	 *  ���������� ������������� ���������� �������
	 * @return ������ �����
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
				// ��������� ����� ��������� �� ��������� ������
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
	 * ������� ������ �� ����������� ���������� �����
	 * @param string - ������
	 * @param limit - ����������� �� �����
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
	 * ��������� ����� ������, ������� ��� ����������
	 * @param text - �����
	 * @return ��������������� �����
	 */
	public static String maskText(String text)
	{
		if (text == null)
			return null;
		return repeat("*", text.length());
	}

	/**
	 * ���������� ������ � ������������ ����� ��������
	 * @param text - �������� ������
	 * @return  ������ ������� �������
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
	 * ��������� ����������� ����� �������� � ��������� �� �����
	 * @param text - ������ � ������
	 * @return ������ � ������, � �������� ��������� ����������� ����� �������� � ����������� �� �����
	 */
	public static String roundNumberStringAndAddSeparator(String text)
	{
		double number = Double.parseDouble(text);
		number = Math.round(number*100)/100.0;

		return getStringInNumberFormat(Double.toString(number));
	}

	/**
	 * ��������� ���������� ���� � ������
	 * @param string - ������
	 * @param length - ����������� ����� �������������� ������
	 * @return ������ ������ <length> � ������ �������, ���� <string> ������ <length>, ����� <string> 
	 */
	public static String addLeadingZeros(String string, int length)
	{
		if (string == null)
			throw new NullPointerException("�������� 'string' �� ����� ���� null");
		if (string.length() >= length)
			return string;

		StringBuilder sb = new StringBuilder(length-string.length());
		for (int i = length-string.length(); i>0; i--)
			sb.append('0');
		return sb.append(string).toString();
	}

	/**
	 * ��������� ���������� ���� � ������ ������ ������ �����
	 * @param list - ������ �����
	 * @param length - ����������� ����� �������������� ������
	 * @return ������ ����� ������ <length> � ������ �������, ���� <string> ������ <length>, ����� <string>
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
	 * ���������� ������ � ������ string �������� appendix �� length ��������
	 * @param string �������� ������
	 * @param appendix ����������� ������
	 * @param length ��������� ������ ������
	 * @return ����������� ������ ������ string �������� appendix �� length ��������
	 * ���� length ������ ����� ������ string, �� ������������ string
	 */
	public static String append(String string, char appendix, int length)
	{
		if (string == null)
			throw new NullPointerException("�������� 'string' �� ����� ���� null");
		if (string.length() >= length)
			return string;
		
		StringBuilder sb = new StringBuilder(length);
		sb.append(string);

		for(int i = length - string.length(); i > 0; i--)
			sb.append(appendix);

		return sb.toString();
	}

	/**
	 * ����������� ������ ����� � ���� ������, ����� �����������.
	 * @param stringList ������� ������
	 * @param delimiter �����������
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
	 * ����������� ������ ����� � ���� ������, ����� �����������. ����� ���������� �������� ����������� �� �����������.
	 * @param stringList ������� ������
	 * @param delimiter �����������
	 * @return �������� ������
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
	 * ��������� ����� ��� ����� �������� ����� ������ ������� � null(�.�. ������ ������ � null �����)
	 * @param str1 ������ ������
	 * @param str2 ������ ������
	 * @return true ������ �����
	 */
	public static boolean equalsNullIgnore(String str1, String str2)
	{
		return getEmptyIfNull(str1).equals(getEmptyIfNull(str2));
	}

	/**
	 * ��������� ������ �� ������ �������� �������� �����.
	 * ��������� ��������� ����� ���� ������� �����
	 * @param source ������
	 * @param chunkLength �����
	 * @return ������ �����
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
	 * ����������� ���� � ���� ������ ������� xsd:datetime � ���� 'dd.mm.yyyy HH:MM'
	 * @param date ������ ������� xsd:datetime
	 * @return  ������ ����   'dd.mm.yyyy HH:MM'
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
	 * �������� ������� �� nbsp. ������� ���� ��������� �� ��, ��� ������� nbsp ������������� � html
	 * @param str - ������� ������
	 * @return
	 */
	public static String changeWhiteSpaces(String str)
	{
		return str.replaceAll(" ","&nbsp;");
	}

	/**
	 * ������������ ���������� ������ ������ ��������
	 * @param value ������ �� ������� ���� ����������
	 * @param isCaseSensitive true - ��������� � ������ ��������, false -  ��������� ��� ����� ��������
	 * @return ���������� ���������� ������ ������ ��������
	 */
	public static int maxCountRepeateChar(String value, boolean isCaseSensitive)
	{
		int maxSize = 1;
		int counter = 1;
		char currentChar = value.charAt(0);

		// �������� �� ������� �������, �.�. ��� ������� ������� ��� ��� �����������������
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
	 * ��������� ���� ����� ��� ����� �������� � �������� (��� �� �����, ��� � ������)
	 * @param str1 ������1
	 * @param str2 ������2
	 * @return true - ������ ����� ��� ����� �������� � �������� (��� �� �����, ��� � ������)
	 */
	public static boolean equalsIgnoreCaseStrip(String str1, String str2)
	{
		return equalsIgnoreCase(deleteWhitespace(str1), deleteWhitespace(str2));
	}

	/**
	 * ���������� ��� ������� � "���������������" ����:
	 * ��� �������� � ������� (������ � �������) � � ������� ��������.
	 * @param name - ����������������� ��� / ������� / �������� (can be null)
	 * @return ��������������� ��� / ������� / ��������
	 */
	public static String normalizePersonName(String name)
	{
		return replaceChars(name, " -", "").toUpperCase();
	}

	/**
	 * ��������� ��� �������.
	 * �������� ��� ��������� �����, ������� � ��������.
	 * ��� ��������� �� ����������� �������, ������� � ������ (��� �� �����, ��� � ������).
	 * ����� �� �����, ���� ���� �� ��� - null ��� �����.
	 * @param name1 - ��� ��� (can be null)
	 * @param name2 - ��� ��� (can be null)
	 * @return true, ���� name1 � name2 - ��� ���� � ���� ���
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
	 * @param string - ������
	 * @return true, ���� �� ����� ������, ��������� ������ �� ����
	 */
	public static boolean isNumericString(String string)
	{
		if (isEmpty(string))
			return false;
		return NUMBERS_PATTERN.matcher(string).matches();
	}
	/**
	 * Tests if this string starts with the specified prefix
	 * @param str ������ ��� ��������
	 * @param with �������
	 * @return true - ���� ������ ���������� � ��������, ����� false
	 * @throws UnsupportedEncodingException
	 */
	public static boolean startsWith(String str, String with) throws UnsupportedEncodingException
	{
		return str.startsWith(with);
	}

	/**
	 * �������� ������� �� ����������� ���������
	 * @param s �������� ������
	 * @param r �� ��� ��������
	 * @param p �������
	 * @return �������������� ������
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
	 * ������� ������ �������
	 * @param s �������� ������
	 * @return �������������� ������
	 */
	public static String replaceSpaces(String s)
	{
		return replace(s, " ", SPASE_PATTERN);
	}

	/**
	 * ���������� ������ �� �������� ��� ��������.
	 * ���������� ��������� �� ��������.
	 * @param strings ���������
	 * @return ������ ��� ��������
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
	 * ���������� ��� ��������� ����� ������������ ������������
	 * @param str �������� ������
	 * @param separators �����������
	 * @param minLength ����������� ������ ���������
	 * @return ��������� (��� - ������ ������)
	 */
	public static List<String> getPostfixes(String str, String separators, int minLength)
	{
		if (isEmpty(str))
			return Collections.emptyList();
		if (isEmpty(separators))
			throw new IllegalArgumentException("����� ������������ �� ����� ���� ����");
		if (minLength < 1)
			throw new IllegalArgumentException("����������� ����� �� ������������");

		int length = str.length();
		if (length <= minLength)
			return Collections.emptyList();

		List<String> postfixes = new ArrayList<String>();

		int i = length - 1;
		while (i >= 0)
		{
			//���������� �����������
			while (i >= 0 && separators.indexOf(str.charAt(i)) >= 0)
				i--;

			//����� ������ ���������
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
	 * ������� ������� �� ������, ��������������� ��������� ���������
	 * @param string - ������ (can be null can be empty)
	 * @param predicate - �������� (never null)
	 * @return ������ ��� �������� ��������
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
	 * ��������� ������ ������� �������, ���������� � charForReplace �� ������ replaceTo � ������ src.
	 *
	 * @param src �������� ������.
	 * @param charForReplace ������ �������� ��� ������.
	 * @param replaceTo �������� �� ���� ������.
	 * @return ��������� ������.
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
	 * ��������� ������ ������� ������� �� ��������� �� firstSymbol �� lastSymbol �� ������ replaceTo � ������ src.
	 *
	 * @param src �������� ������.
	 * @param firstSymbol ������ ��������� ������.
	 * @param lastSymbol ����� ��������� ������.
	 * @param replaceTo �������� �� ���� ������.
	 * @return ��������� ������.
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
	 * ����� ������������ ��� ������ �� ���������� �������� URL �� "%" � ��� ����������������� �����.
	 * @param url URL, ������� ��������� ������������
	 * @return �������������� URL
	 * @throws UnsupportedEncodingException ���������� ���������, ���� ������� �� ������������ ��������� Windows-1251
	 */
	public static String encodeURL(String url) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(url, DEFAULT_ENCODING);
	}
}
