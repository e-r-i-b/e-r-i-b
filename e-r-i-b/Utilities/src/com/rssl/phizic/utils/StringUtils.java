package com.rssl.phizic.utils;

/**
 * @author Omeliyanchuk
 * @ created 31.07.2006
 * @ $Author$
 * @ $Revision$
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static org.apache.commons.lang.StringUtils.capitalize;
import static org.apache.commons.lang.StringUtils.join;

public class StringUtils
{
	private static final Log log = LogFactory.getLog(StringUtils.class);

	private static final int HEX_RADIX = 16;
	private static final int HI_MASK   = 0xF0;
	private static final int LO_MASK   = 0x0F;
	private static final String NOT_LETTER_OR_NUMBER = "^[^0-9a-zA-Zа-яА-ЯЁё]*";
	private static final String NOT_LETTER_OR_SPACE = "[^a-zA-Zа-яА-ЯЁё ]*";
	/**
	 * @param data массив байт
	 * @return строка в содержащая предстваление в hex виде
	 */
	public static String toHexString(byte[] data)
	{
		StringBuilder sb = new StringBuilder(data.length * 2);
		for (byte b : data)
		{
			sb.append(forDigit((b & HI_MASK) >> 4));
			sb.append(forDigit( b & LO_MASK)      );
		}
		return sb.toString();
	}

	/**
	 * @param hex строка в содержащая предстваление в hex виде (upper case)
	 * @return массив байт
	 */
	public static byte[] fromHexString(String hex)
	{
		byte[] res = new byte[hex.length() / 2];

		for (int i = 0; i < hex.length() / 2; i++)
		{
			char hi = hex.charAt(i * 2);
			res[i] = (byte) (Character.digit(hi, HEX_RADIX) << 4 | Character.digit(hex.charAt(i * 2 + 1), HEX_RADIX));
		}
		return res;
	}

	/**
	 * @param hex массив символов содержащая бинарные данные в hex виде (upper case) {0F,FF,02,45}
	 * @return массив байт
	 */
	public static byte[] fromHexArray(char[] hex)
	{
		byte[] res = new byte[hex.length / 2];

		for (int i = 0; i < hex.length / 2; i++)
		{
			char hi = hex[i * 2];
			res[i] = (byte) (Character.digit(hi, HEX_RADIX) << 4 | Character.digit(hex[i * 2 + 1], HEX_RADIX));
		}
		return res;
	}

	/**
	 * Функция замены символов переноса строки на заданный.
	 * jstl не умеет заменять символы переноса строки
	 * @param str обрабатываемая строка
	 * @param newChar символ, на который заменяется перенос
	 * @return обработанная строка
	 */
	public static String replaceNewLine(String str, String newChar)
	{
		return str.replace("\r\n", newChar).replace("\r", newChar).replace("\n", newChar);
	}
	/**
	 * Функция удаляет из начала строки не буквенно-цифровые символы
	 * @param str обрабатываемая строка
	 * @return обработанная строка
	 */
	public static String replaceSymbol(String str)       
	{
		return str.replaceAll(NOT_LETTER_OR_NUMBER, "");
	}

	/**
	 * Заменяет символы '"' и '\' для корректной работы при передачи их через ajax или iframe(для ie6)
	 * @param str строка для замены.
	 * @return измененная строка.
	 */
	public static String escapeSymbols(String str)
	{
		return str.replaceAll("([\\n\\r])", " ").replace("\"","&quot;").replace("\\","&#92;").replace("=","&#61;");
	}

	/**
	 * Функция удаляет из строки все НЕ буквы и пробелы
	 * @param str строка для замены
	 * @return измененная строка
	 */
	public static String removeNotLetter(String str)
	{
		return str.replaceAll(NOT_LETTER_OR_SPACE, "");
	}

	/**
	 * Функция удаляет из строки указанные символы
	 * @param str обрабатываемая строка
	 * @return обработанная строка
	 */
	public static String replaceString(String str, String regexp)
	{
		return str.replaceAll(regexp, "");
	}

	/**
	 * @param str - входящая строка для преобразования
	 * @return входящую строку из кодировки Windows-1251 в Windows-1252. Применяется при преобразовании имени файла при выгрузке.
	 */
	public static String from1251To1252(String str)
	{
		try
		{
			return new String(str.getBytes("Cp1251"),"Cp1252");
		}
		catch (UnsupportedEncodingException ignore)
		{
			return translit(str);
		}
	}

	/**
	 * @param data массив байт
	 * @return массив символов содержащий предстваление в hex виде
	 */
	public static char[] toHexArray(byte[] data)
	{
		char[] hex = new char[data.length * 2];
		int i = 0;
		for (byte b : data)
		{
			hex[i++] = forDigit((b & HI_MASK) >> 4);
			hex[i++] = forDigit(b & LO_MASK); 
		}
		return hex;
	}

	private static char forDigit(int digit)
	{
		if (digit < 10)
		{
			return (char) ('0' + digit);
		}
		return (char) ('A' - 10 + digit);
	}

	/**
	 * Поиск строки в массиве
	 * @param strings массив
	 * @param string строка
	 * @return true == найдено
	 */
	public static boolean contains(String[] strings, String string)
	{
// TODO Бесмыссленно и даже вредно сортировать массив для единичного поиска, в смысле сложности алгоритма, не говоря уже о
// недопустимости подобных побоочных эффектов
		Arrays.sort(strings);
		if (Arrays.binarySearch(strings, string) >= 0)
			return true;
		return false;
	}
	/**
	 * @param amount сумма
	 * @param isoCode код валюты (RUB, USD)
	 * @return сумма прописью
	 */
	public static String sumInWords(double amount, String isoCode)
	{
		return sumInWords(String.format("%1$.2f", amount), isoCode);
	}

	/**
	 * @param s сумма строкой "12121.23"
	 * @param isoCode код валюты (RUB, USD)
	 * @return сумма прописью
	 */
	public static String sumInWords(String s, String isoCode)
	{
		try
		{
			if (s.length() == 0) return "";
			if (isoCode.equals("RUB") || isoCode.equals("RUR"))
			{
				return new jAmount(new String[]{"рубль", "рубля", "рублей", "копейка", "копейки", "копеек", "M"}, true, s).toString();
			}
			else if (isoCode.equals("USD"))
			{
				return new jAmount(new String[]{"доллар", "доллара", "долларов", "цент", "цента", "центов", "M"}, true, s).toString();
			}
			else if (isoCode.equals("EUR"))
			{
				return new jAmount(new String[]{"евро", "евро", "евро", "цент", "цента", "центов", "M"}, true, s).toString();
			}
			else
			{
				// вывод суммы прописью без обозначения валюты
				return new jAmount(new String[]{"", "", "", "", "", "", "M"}, false, s).toString().trim();
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка  замены числовой суммы на буквенную",e);
			return "";
		}
	}

	/**
	 * Безопасное обрезание строки.
	 * @param str - строка
	 * @param length - длина до которой необходимо обрезать
	 * @return подстроку с 0 по length символ в данной строке или всю строку, если её длина меньше length
	 */
	public static String safeTrunc(String str, int length)
	{
		if(str.length() > length)
		    return str.substring(0,length);
		else
			return str;
	}

	private static class jAmount
	{

		private BigInteger summ;
		private String[] suff;
		private boolean showcurr;
		private static final BigInteger zero = new BigInteger("0");
		private static final BigInteger hundred = new BigInteger("100");
		private static final BigInteger thousand = new BigInteger("1000");

		// Конструктор класса. Конструктор в качестве параметров получает:
		// String suff []   массив наименований (настроечная информация) и
		// String sum       сумма для преобразования
		public jAmount(String[] suff, boolean showcurr, String s)
		{
			this.suff = suff;
			this.showcurr = showcurr;
			BigDecimal decimal = new BigDecimal(s);
			// Преобразуем в копейки (центы, пфенниги и т.д.),
			// одним словом - убираем дробную часть
			decimal = decimal.multiply(new BigDecimal(100.00));
			summ = decimal.toBigInteger();
		}

		// Получить правую (дробную) часть суммы
		public String getRightPart()
		{
			return alignSumm(summ.remainder(hundred).abs().toString());
		}

		// Если сумма меньше 10, то выровнять ее дописыванием "0"
		String alignSumm(String s)
		{
			switch (s.length())
			{
				case 0:
					s = "00";
					break;
				case 1:
					s = "0" + s;
					break;
			}
			return s;
		}

		public String toString()
		{
			StringBuffer result = new StringBuffer();
			BigInteger[] divrem = summ.divideAndRemainder(hundred);
			if (divrem[0].signum() == 0)
			{
				result.append("Ноль ");
			}
			divrem = divrem[0].divideAndRemainder(thousand);
			BigInteger quotient = divrem[0];
			BigInteger remainder = divrem[1];
			int group = 0;
			do
			{
				int value = remainder.intValue();
				result.insert(0, groupToString(value, group));
				// Для нулевой группы добавим в конец соответствующую валюту
				if (group == 0)
				{
					int rank10 = (value % 100) / 10;
					int rank = value % 10;
					if (rank10 == 1)
					{
						result = result.append(suff[2]);
					}
					else
					{
						switch (rank)
						{
							case 1:
								result = result.append(suff[0]);
								break;
							case 2:
							case 3:
							case 4:
								result = result.append(suff[1]);
								break;
							default:
								result = result.append(suff[2]);
								break;
						}
					}
				}
				divrem = quotient.divideAndRemainder(thousand);
				quotient = divrem[0];
				remainder = divrem[1];
				group++;
			}
			while (!quotient.equals(zero) || !remainder.equals(zero));

			if (showcurr)
			{
				// Дробная часть суммы
				String s = getRightPart();
				result = result.append(" ").append(s);
				result = result.append(" ");
				if (s.charAt(0) == '1')
				{
					result = result.append(suff[5]);
				}
				else
				{
					switch (s.charAt(1))
					{
						case '1':
							result = result.append(suff[3]);
							break;
						case '2':
						case '3':
						case '4':
							result = result.append(suff[4]);
							break;
						default:
							result = result.append(suff[5]);
							break;
					}
				}
			}
			// По правилам бухгалтерского учета первая буква строкового
			// представления должна быть в верхнем регистре
			result.setCharAt(0, Character.toUpperCase(result.charAt(0)));

			// Вот ради этой строки все и затевалось: результат получен !!!

			return result.toString();
		}

		// Преобразование группы цифр в строку
		String groupToString(int value, int group)
		{
			if (value < 0 || value > 999)
			{
				throw new IllegalArgumentException(
						"value must be between 0 an 999 inclusively");
			}
			if (group < 0)
			{
				throw new IllegalArgumentException("group must be more than 0");
			}
			StringBuffer result = new StringBuffer(32);
			if (value == 0)
			{
				return result.toString();
			}
			// Разбор числа по разрядам, начиная с сотен
			int rank = value / 100;
			switch (rank)
			{
				default:
					break;
				case 1:
					result = result.append("сто ");
					break;
				case 2:
					result = result.append("двести ");
					break;
				case 3:
					result = result.append("триста ");
					break;
				case 4:
					result = result.append("четыреста ");
					break;
				case 5:
					result = result.append("пятьсот ");
					break;
				case 6:
					result = result.append("шестьсот ");
					break;
				case 7:
					result = result.append("семьсот ");
					break;
				case 8:
					result = result.append("восемьсот ");
					break;
				case 9:
					result = result.append("девятьсот ");
					break;
			}
			// Далее, десятки
			rank = (value % 100) / 10;
			switch (rank)
			{
				default:
					break;
				case 2:
					result = result.append("двадцать ");
					break;
				case 3:
					result = result.append("тридцать ");
					break;
				case 4:
					result = result.append("сорок ");
					break;
				case 5:
					result = result.append("пятьдесят ");
					break;
				case 6:
					result = result.append("шестьдесят ");
					break;
				case 7:
					result = result.append("семьдесят ");
					break;
				case 8:
					result = result.append("восемьдесят ");
					break;
				case 9:
					result = result.append("девяносто ");
					break;
			}
			// Если десятки = 1, добавить соответствующее значение. Иначе - единицы
			int rank10 = rank;
			rank = value % 10;
			if (rank10 == 1)
			{
				switch (rank)
				{
					case 0:
						result = result.append("десять ");
						break;
					case 1:
						result = result.append("одиннадцать ");
						break;
					case 2:
						result = result.append("двенадцать ");
						break;
					case 3:
						result = result.append("тринадцать ");
						break;
					case 4:
						result = result.append("четырнадцать ");
						break;
					case 5:
						result = result.append("пятнадцать ");
						break;
					case 6:
						result = result.append("шестнадцать ");
						break;
					case 7:
						result = result.append("семнадцать ");
						break;
					case 8:
						result = result.append("восемнадцать ");
						break;
					case 9:
						result = result.append("девятнадцать ");
						break;
				}
			}
			else
			{
				switch (rank)
				{
					default:
						break;
					case 1:
						if (group == 1) // Тысячи
						{
							result = result.append("одна ");
						}
						else
							// Учесть род валюты (поле "Sex" настроечной информации)
							if (suff[6].equals("M"))
							{
								result = result.append("один ");
							}
						if (suff[6].equals("F"))
						{
							result = result.append("одна ");
						}
						break;
					case 2:
						if (group == 1) // Тысячи
						{
							result = result.append("две ");
						}
						else
							// Учесть род валюты (поле "Sex" настроечной информации)
							if (suff[6].equals("M"))
							{
								result = result.append("два ");
							}
						if (suff[6].equals("F"))
						{
							result = result.append("две ");
						}
						break;
					case 3:
						result = result.append("три ");
						break;
					case 4:
						result = result.append("четыре ");
						break;
					case 5:
						result = result.append("пять ");
						break;
					case 6:
						result = result.append("шесть ");
						break;
					case 7:
						result = result.append("семь ");
						break;
					case 8:
						result = result.append("восемь ");
						break;
					case 9:
						result = result.append("девять ");
						break;
				}
			}
			// Значение группы: тысячи, миллионы и т.д.
			switch (group)
			{
				default:
					break;
				case 1:
					if (rank10 == 1)
					{
						result = result.append("тысяч ");
					}
					else
					{
						switch (rank)
						{
							default:
								result = result.append("тысяч ");
								break;
							case 1:
								result = result.append("тысяча ");
								break;
							case 2:
							case 3:
							case 4:
								result = result.append("тысячи ");
								break;
						}
					}
					break;
				case 2:
					if (rank10 == 1)
					{
						result = result.append("миллионов ");
					}
					else
					{
						switch (rank)
						{
							default:
								result = result.append("миллионов ");
								break;
							case 1:
								result = result.append("миллион ");
								break;
							case 2:
							case 3:
							case 4:
								result = result.append("миллиона ");
								break;
						}
					}
					break;
				case 3:
					if (rank10 == 1)
					{
						result = result.append("миллиардов ");
					}
					else
					{
						switch (rank)
						{
							default:
								result = result.append("миллиардов ");
								break;
							case 1:
								result = result.append("миллиард ");
								break;
							case 2:
							case 3:
							case 4:
								result = result.append("миллиарда ");
								break;
						}
					}
					break;
				case 4:
					if (rank10 == 1)
					{
						result = result.append("триллионов ");
					}
					else
					{
						switch (rank)
						{
							default:
								result = result.append("триллионов ");
								break;
							case 1:
								result = result.append("триллион ");
								break;
							case 2:
							case 3:
							case 4:
								result = result.append("триллиона ");
								break;
						}
					}
					break;
			}
			return result.toString();
		}
	}

	/**
	 * Перевод суммы в формат вида 250-76 или 250= (для печатных форм)
	 * @param amount сумма
	 * @return cтрока нужного формата
	 */
	public static String cvtSum(String amount)
	{
		int pos = amount.indexOf(".");
		if (pos > 0)
		{
		   if (amount.substring(pos + 1).equals("00"))
		      return amount.replace(".00", "=");
		   else
		      return amount.replace(".", "-");
		}
		else
			return amount + "=";
	}

	/**
	 * Возвращает код ссылки с  переданными значениями onClick и тескстом для отображения пользователю
	 * @param onClick  - значения onClick
	 * @param text     - навзание ссылки
	 * @return  код ссылки
	 */
	public static String getLink(String onClick, String text)
	{
		if (!StringHelper.isEmpty(onClick))
			return "<a href=\"#\"  onclick=\"" + onClick + "\">" + text + "</a>";
		return text;
	}

	/**
	 * Транслитерирует входную строку
	 * @param str - строка для транслитерации
	 * @return
	 */
	public static String translit(String str)
	{
	    if (str == null)
	        return "";

		// 1. Пословная транслитерация (спец.слова обрабатываем отдельным образом)
		String delims = " \t\n\r";
		StringBuilder sb = new StringBuilder(str.length());
		StringTokenizer tokenizer = new StringTokenizer(str, delims, true);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (token.length() == 1 && org.apache.commons.lang.StringUtils.contains(delims, token))
				sb.append(token);
			else sb.append(org.apache.commons.lang.StringUtils.replaceEach(token, TRANSLIT_SEARCH_WORDS, TRANSLIT_REPLACE_WORDS));
		}
		//noinspection AssignmentToMethodParameter
		str = sb.toString();

		// 2. Посимвольная транслитерация
		//noinspection ReuseOfLocalVariable
		sb = new StringBuilder(str.length());
	    for (int i = 0; i < str.length(); i++) {
	        char c = str.charAt(i);
	        if (latinChars.contains(c) || Character.isDigit(c)) {
	            sb.append(c);
	            continue;
	        }
	        if (charmap.containsKey(c)) {
	            String cS = charmap.get(c);
	            sb.append(cS);
	            continue;
	        }
	        // TODO: what we must do with another chars?
	        sb.append(c);
	    }
	    return sb.toString();
	}

	//Транслит анг. -> рус. или рус. -> анг.
	public static String translitToAnother(String str)
	{
		if (StringHelper.isEmpty(str))
			return str;
        String newStr = new String(str);

		//пытаемся найти транслит букв, состоящих из нескольких символов (ya = я, zh = ж и др.)
		for (String multiChar : multiCharList)
		{
		   if (newStr.contains(multiChar))
		   {
			   newStr = newStr.replace(multiChar, charmapEngToRus.get(multiChar));
		   }
		}

		//Посимвольная транслитерация
		StringBuilder sb = new StringBuilder(newStr.length());
		for (int i = 0; i < newStr.length(); i++)
		{
			char c = newStr.charAt(i);
			if (!latinChars.contains(c) || Character.isDigit(c)) {
	            sb.append(c);
	            continue;
	        }

			String cS =  charmapEngToRus.get(Character.toString(c));
			if (StringHelper.isNotEmpty(cS))
			{
				sb.append(cS);
				continue;
			}
			sb.append(c);
		}

		return str.equalsIgnoreCase(sb.toString()) ? translit(str) : sb.toString();
	}

	/**
	 * Объединяет строки в одну заменяя первую букву каждого слова входящего в строки на прописную
	 * @param strings список строк
	 * @return
	 */
	public static String capitalizeStrings(String... strings)
	{
		List<String> collection = new ArrayList<String>();
		for (String string : strings)
		{
			if (StringHelper.isEmpty(string))
				continue;

			for (String str : string.split("\\s+"))
				collection.add(capitalize(str));
		}

		return join(collection, ' ');
	}

	private static final Set<Character> latinChars;
	private static final Map<Character, String> charmap;
	private static final LinkedList<String> multiCharList;
	private static final Map<String, String> charmapEngToRus;
	private static final Map<Character, String> switchFromRusToEngCharMap;
	private static final Map<Character, String> switchFromEngToRusCharMap;
	private static final Set<String> counts;

	static {
	    latinChars = new HashSet<Character>();
	    charmap = new HashMap<Character, String>();
		multiCharList = new LinkedList<String>();
		charmapEngToRus = new HashMap<String, String>(); 
		switchFromRusToEngCharMap = new HashMap<Character, String>();
		switchFromEngToRusCharMap = new HashMap<Character, String>();
		counts = new HashSet<String>();
	    for (char i = 'a'; i <= 'z'; i++) {
	        latinChars.add(i);
	    }
	    for (char i = 'A'; i <= 'Z'; i++) {
	        latinChars.add(i);
	    }
	    latinChars.add('_');
	    latinChars.add(' ');
		//Транслит из рус. в англ.
	    charmap.put('а', "a");
	    charmap.put('б', "b");
	    charmap.put('в', "v");
	    charmap.put('г', "g");
	    charmap.put('д', "d");
	    charmap.put('е', "e");
	    charmap.put('ё', "e");
	    charmap.put('ж', "zh");
	    charmap.put('з', "z");
	    charmap.put('и', "i");
	    charmap.put('й', "y");
	    charmap.put('к', "k");
	    charmap.put('л', "l");
	    charmap.put('м', "m");
	    charmap.put('н', "n");
	    charmap.put('о', "o");
	    charmap.put('п', "p");
	    charmap.put('р', "r");
	    charmap.put('с', "s");
	    charmap.put('т', "t");
	    charmap.put('у', "u");
	    charmap.put('ф', "f");
	    charmap.put('х', "kh");
	    charmap.put('ц', "ts");
	    charmap.put('ч', "ch");
	    charmap.put('ш', "sh");
	    charmap.put('щ', "sch");
	    charmap.put('ъ', "");
	    charmap.put('ы', "y");
	    charmap.put('ь', "");
	    charmap.put('э', "e");
	    charmap.put('ю', "yu");
	    charmap.put('я', "ya");

	    charmap.put('А', "A");
	    charmap.put('Б', "B");
	    charmap.put('В', "V");
	    charmap.put('Г', "G");
	    charmap.put('Д', "D");
	    charmap.put('Е', "E");
	    charmap.put('Ё', "E");
	    charmap.put('Ж', "ZH");
	    charmap.put('З', "Z");
	    charmap.put('И', "I");
	    charmap.put('Й', "Y");
	    charmap.put('К', "K");
	    charmap.put('Л', "L");
	    charmap.put('М', "M");
	    charmap.put('Н', "N");
	    charmap.put('О', "O");
	    charmap.put('П', "P");
	    charmap.put('Р', "R");
	    charmap.put('С', "S");
	    charmap.put('Т', "T");
	    charmap.put('У', "U");
	    charmap.put('Ф', "F");
	    charmap.put('Х', "KH");
	    charmap.put('Ц', "TS");
	    charmap.put('Ч', "CH");
	    charmap.put('Ш', "SH");
	    charmap.put('Щ', "SCH");
	    charmap.put('Ъ', "");
	    charmap.put('Ы', "Y");
	    charmap.put('Ь', "");
	    charmap.put('Э', "E");
	    charmap.put('Ю', "YU");
	    charmap.put('Я', "YA");
		charmap.put('–',"-");   //тире на минус

		//Транслит из англ. в рус.
		multiCharList.add("sch");
		multiCharList.add("sh");
		multiCharList.add("kh");
		multiCharList.add("ts");
		multiCharList.add("ch");
		multiCharList.add("zh");
		multiCharList.add("yu");
		multiCharList.add("ya");

		charmapEngToRus.put("a", "а");
	    charmapEngToRus.put("b", "б");
	    charmapEngToRus.put("v", "в");
	    charmapEngToRus.put("g", "г");
	    charmapEngToRus.put("d", "д");
	    charmapEngToRus.put("e", "е");
	    charmapEngToRus.put("zh", "ж");
	    charmapEngToRus.put("z", "з");
	    charmapEngToRus.put("i", "и");
	    charmapEngToRus.put("y", "й");
	    charmapEngToRus.put("k", "к");
	    charmapEngToRus.put("l", "л");
	    charmapEngToRus.put("m", "м");
	    charmapEngToRus.put("n", "н");
	    charmapEngToRus.put("o", "о");
	    charmapEngToRus.put("p", "п");
	    charmapEngToRus.put("r", "р");
	    charmapEngToRus.put("s", "с");
	    charmapEngToRus.put("t", "т");
	    charmapEngToRus.put("u", "у");
	    charmapEngToRus.put("f", "ф");
	    charmapEngToRus.put("kh", "х");
	    charmapEngToRus.put("ts", "ц");
	    charmapEngToRus.put("ch", "ч");
	    charmapEngToRus.put("sh", "ш");
	    charmapEngToRus.put("sch", "щ");
	    charmapEngToRus.put("y", "ы");
	    charmapEngToRus.put("yu", "ю");
	    charmapEngToRus.put("ya", "я");

	    charmapEngToRus.put("A", "А");
	    charmapEngToRus.put("B", "Б");
	    charmapEngToRus.put("V", "В");
	    charmapEngToRus.put("G", "Г");
	    charmapEngToRus.put("D", "Д");
	    charmapEngToRus.put("E", "Е");
	    charmapEngToRus.put("ZH", "Ж");
	    charmapEngToRus.put("Z", "З");
	    charmapEngToRus.put("I", "И");
	    charmapEngToRus.put("Y", "Й");
	    charmapEngToRus.put("K", "К");
	    charmapEngToRus.put("L", "Л");
	    charmapEngToRus.put("M", "М");
	    charmapEngToRus.put("N", "Н");
	    charmapEngToRus.put("O", "О");
	    charmapEngToRus.put("P", "П");
	    charmapEngToRus.put("R", "Р");
	    charmapEngToRus.put("S", "С");
	    charmapEngToRus.put("T", "Т");
	    charmapEngToRus.put("U", "У");
	    charmapEngToRus.put("F", "Ф");
	    charmapEngToRus.put("KH", "Х");
	    charmapEngToRus.put("TS", "Ц");
	    charmapEngToRus.put("CH", "Ч");
	    charmapEngToRus.put("SH", "Ш");
	    charmapEngToRus.put("SCH", "Щ");
	    charmapEngToRus.put("Y", "Ы");
	    charmapEngToRus.put("YU", "Ю");
	    charmapEngToRus.put("YA", "Я");
		charmapEngToRus.put("-", "–");

		//Мапа для перевода рус. раскладки в англ. (для исправления ошибочной раскладки клав-ры)
		switchFromRusToEngCharMap.put('й',"q");
		switchFromRusToEngCharMap.put('ц',"w");
		switchFromRusToEngCharMap.put('у',"e");
		switchFromRusToEngCharMap.put('к',"r");
		switchFromRusToEngCharMap.put('е',"t");
		switchFromRusToEngCharMap.put('н',"y");
		switchFromRusToEngCharMap.put('г',"u");
		switchFromRusToEngCharMap.put('ш',"i");
		switchFromRusToEngCharMap.put('щ',"o");
		switchFromRusToEngCharMap.put('з',"p");
		switchFromRusToEngCharMap.put('х',"[");
		switchFromRusToEngCharMap.put('ъ',"]");
		switchFromRusToEngCharMap.put('ф',"a");
		switchFromRusToEngCharMap.put('ы',"s");
		switchFromRusToEngCharMap.put('в',"d");
		switchFromRusToEngCharMap.put('а',"f");
		switchFromRusToEngCharMap.put('п',"g");
		switchFromRusToEngCharMap.put('р',"h");
		switchFromRusToEngCharMap.put('о',"j");
		switchFromRusToEngCharMap.put('л',"k");
		switchFromRusToEngCharMap.put('д',"l");
		switchFromRusToEngCharMap.put('ж',";");
		switchFromRusToEngCharMap.put('э',"\\");
		switchFromRusToEngCharMap.put('я',"z");
		switchFromRusToEngCharMap.put('ч',"x");
		switchFromRusToEngCharMap.put('с',"c");
		switchFromRusToEngCharMap.put('м',"v");
		switchFromRusToEngCharMap.put('и',"b");
		switchFromRusToEngCharMap.put('т',"n");
		switchFromRusToEngCharMap.put('ь',"m");
		switchFromRusToEngCharMap.put('б',",");
		switchFromRusToEngCharMap.put('ю',".");
		switchFromRusToEngCharMap.put('Й',"Q");
		switchFromRusToEngCharMap.put('Ц',"W");
		switchFromRusToEngCharMap.put('У',"E");
		switchFromRusToEngCharMap.put('К',"R");
		switchFromRusToEngCharMap.put('Е',"T");
		switchFromRusToEngCharMap.put('Н',"Y");
		switchFromRusToEngCharMap.put('Г',"U");
		switchFromRusToEngCharMap.put('Ш',"I");
		switchFromRusToEngCharMap.put('Щ',"O");
		switchFromRusToEngCharMap.put('З',"P");
		switchFromRusToEngCharMap.put('Х',"[");
		switchFromRusToEngCharMap.put('Ъ',"]");
		switchFromRusToEngCharMap.put('Ф',"A");
		switchFromRusToEngCharMap.put('Ы',"S");
		switchFromRusToEngCharMap.put('В',"D");
		switchFromRusToEngCharMap.put('А',"F");
		switchFromRusToEngCharMap.put('П',"G");
		switchFromRusToEngCharMap.put('Р',"H");
		switchFromRusToEngCharMap.put('О',"J");
		switchFromRusToEngCharMap.put('Л',"K");
		switchFromRusToEngCharMap.put('Д',"L");
		switchFromRusToEngCharMap.put('Ж',";");
		switchFromRusToEngCharMap.put('Э',"\\");
		switchFromRusToEngCharMap.put('?',"Z");
		switchFromRusToEngCharMap.put('ч',"X");
		switchFromRusToEngCharMap.put('С',"C");
		switchFromRusToEngCharMap.put('М',"V");
		switchFromRusToEngCharMap.put('И',"B");
		switchFromRusToEngCharMap.put('Т',"N");
		switchFromRusToEngCharMap.put('Ь',"M");
		switchFromRusToEngCharMap.put('Б',",");
		switchFromRusToEngCharMap.put('Ю',".");
		switchFromRusToEngCharMap.put(' '," ");

		//Мапа для перевода англ. раскладки в рус.
		switchFromEngToRusCharMap.put('q',"й");
		switchFromEngToRusCharMap.put('w',"ц");
		switchFromEngToRusCharMap.put('e',"у");
		switchFromEngToRusCharMap.put('r',"к");
		switchFromEngToRusCharMap.put('t',"е");
		switchFromEngToRusCharMap.put('y',"н");
		switchFromEngToRusCharMap.put('u',"г");
		switchFromEngToRusCharMap.put('i',"ш");
		switchFromEngToRusCharMap.put('o',"щ");
		switchFromEngToRusCharMap.put('p',"з");
		switchFromEngToRusCharMap.put('[',"х");
		switchFromEngToRusCharMap.put(']',"ъ");
		switchFromEngToRusCharMap.put('a',"ф");
		switchFromEngToRusCharMap.put('s',"ы");
		switchFromEngToRusCharMap.put('d',"в");
		switchFromEngToRusCharMap.put('f',"а");
		switchFromEngToRusCharMap.put('g',"п");
		switchFromEngToRusCharMap.put('h',"р");
		switchFromEngToRusCharMap.put('j',"о");
		switchFromEngToRusCharMap.put('k',"л");
		switchFromEngToRusCharMap.put('l',"д");
		switchFromEngToRusCharMap.put(';',"ж");
		switchFromEngToRusCharMap.put('\\',"э");
		switchFromEngToRusCharMap.put('z',"я");
		switchFromEngToRusCharMap.put('x',"ч");
		switchFromEngToRusCharMap.put('c',"с");
		switchFromEngToRusCharMap.put('v',"м");
		switchFromEngToRusCharMap.put('b',"и");
		switchFromEngToRusCharMap.put('n',"т");
		switchFromEngToRusCharMap.put('m',"ь");
		switchFromEngToRusCharMap.put(',',"б");
		switchFromEngToRusCharMap.put('.',"ю");
		switchFromEngToRusCharMap.put('Q',"Й");
		switchFromEngToRusCharMap.put('W',"Ц");
		switchFromEngToRusCharMap.put('E',"У");
		switchFromEngToRusCharMap.put('R',"К");
		switchFromEngToRusCharMap.put('T',"Е");
		switchFromEngToRusCharMap.put('Y',"Н");
		switchFromEngToRusCharMap.put('U',"Г");
		switchFromEngToRusCharMap.put('I',"Ш");
		switchFromEngToRusCharMap.put('O',"Щ");
		switchFromEngToRusCharMap.put('P',"З");
		switchFromEngToRusCharMap.put('[',"Х");
		switchFromEngToRusCharMap.put(']',"Ъ");
		switchFromEngToRusCharMap.put('A',"Ф");
		switchFromEngToRusCharMap.put('S',"Ы");
		switchFromEngToRusCharMap.put('D',"В");
		switchFromEngToRusCharMap.put('F',"А");
		switchFromEngToRusCharMap.put('G',"П");
		switchFromEngToRusCharMap.put('H',"Р");
		switchFromEngToRusCharMap.put('J',"О");
		switchFromEngToRusCharMap.put('K',"Л");
		switchFromEngToRusCharMap.put('L',"Д");
		switchFromEngToRusCharMap.put(';',"Ж");
		switchFromEngToRusCharMap.put('\\',"Э");
		switchFromEngToRusCharMap.put('Z',"?");
		switchFromEngToRusCharMap.put('X',"ч");
		switchFromEngToRusCharMap.put('C',"С");
		switchFromEngToRusCharMap.put('V',"М");
		switchFromEngToRusCharMap.put('B',"И");
		switchFromEngToRusCharMap.put('N',"Т");
		switchFromEngToRusCharMap.put('M',"Ь");
		switchFromEngToRusCharMap.put(',',"Б");
		switchFromEngToRusCharMap.put('.',"Ю");
		switchFromEngToRusCharMap.put(' '," ");

		counts.add("2");
		counts.add("3");
		counts.add("4");
	}

	/**
	 * Слова, которые транслитерируются особым образом
	 */
	private static final String[] TRANSLIT_SEARCH_WORDS = new String[] { "Онлайн", "ОнЛайн", "ОнЛ@йн" };

	/**
	 * Транслитерированные эквиваленты для <TRANSLIT_SEARCH_WORDS>
	 */
	private static final String[] TRANSLIT_REPLACE_WORDS = new String[] { "Online", "OnLine", "OnLine" };

	/**
	 * Возвращает значение преобразованное в другую раскладку
	 * @param str - String первоначальное значение
	 * @param flag - 0 - все в англ. раскладку, 1- все в рус. раскладку, null- и в англ. и в рус.
	 * @return - String преобразованное значение
	 */
	public static String getSwitchToAnotherLayout(String str, Integer flag)
	{
		if (flag == null)
		{
			String newStr = getAnotherLayoutValue(str, switchFromRusToEngCharMap);
			return getAnotherLayoutValue(newStr, switchFromEngToRusCharMap);
		}
		switch(flag) {
			case 0:
				return getAnotherLayoutValue(str, switchFromRusToEngCharMap);
			case 1:
				return getAnotherLayoutValue(str, switchFromEngToRusCharMap);
			default:
				String newStr = getAnotherLayoutValue(str, switchFromRusToEngCharMap);
				return getAnotherLayoutValue(newStr, switchFromEngToRusCharMap);
		}
	}

	private static String getAnotherLayoutValue(String str, Map<Character, String> layoutMam)
	{
		if (StringHelper.isNotEmpty(str) && layoutMam != null)
		{
			StringBuilder sb = new StringBuilder(str.length());

			for (int i = 0; i < str.length(); i++)
			{
				char c = str.charAt(i);
				String newChar = layoutMam.get(c);
				sb.append(StringHelper.isNotEmpty(newChar) ? newChar : c);
			}
			return StringHelper.isNotEmpty(sb.toString()) ? sb.toString() : str;
		}
		return str;
	}

	/**
	 * Возвращает слово "раз"/"раза" в зависимости от передаваемого числа
	 * @param count
	 * @return
	 */
	public static String calculateOccasions(Long count)
	{
		if (count > 10 && count < 20)
			return "раз";

		String countStr = Long.toString(count);
		String lastChar = countStr.substring(countStr.length()-1);
		if (counts.contains(lastChar))
			return "раза";
		return "раз";
	}


}
