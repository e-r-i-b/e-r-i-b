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
	private static final String NOT_LETTER_OR_NUMBER = "^[^0-9a-zA-Z�-��-ߨ�]*";
	private static final String NOT_LETTER_OR_SPACE = "[^a-zA-Z�-��-ߨ� ]*";
	/**
	 * @param data ������ ����
	 * @return ������ � ���������� ������������� � hex ����
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
	 * @param hex ������ � ���������� ������������� � hex ���� (upper case)
	 * @return ������ ����
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
	 * @param hex ������ �������� ���������� �������� ������ � hex ���� (upper case) {0F,FF,02,45}
	 * @return ������ ����
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
	 * ������� ������ �������� �������� ������ �� ��������.
	 * jstl �� ����� �������� ������� �������� ������
	 * @param str �������������� ������
	 * @param newChar ������, �� ������� ���������� �������
	 * @return ������������ ������
	 */
	public static String replaceNewLine(String str, String newChar)
	{
		return str.replace("\r\n", newChar).replace("\r", newChar).replace("\n", newChar);
	}
	/**
	 * ������� ������� �� ������ ������ �� ��������-�������� �������
	 * @param str �������������� ������
	 * @return ������������ ������
	 */
	public static String replaceSymbol(String str)       
	{
		return str.replaceAll(NOT_LETTER_OR_NUMBER, "");
	}

	/**
	 * �������� ������� '"' � '\' ��� ���������� ������ ��� �������� �� ����� ajax ��� iframe(��� ie6)
	 * @param str ������ ��� ������.
	 * @return ���������� ������.
	 */
	public static String escapeSymbols(String str)
	{
		return str.replaceAll("([\\n\\r])", " ").replace("\"","&quot;").replace("\\","&#92;").replace("=","&#61;");
	}

	/**
	 * ������� ������� �� ������ ��� �� ����� � �������
	 * @param str ������ ��� ������
	 * @return ���������� ������
	 */
	public static String removeNotLetter(String str)
	{
		return str.replaceAll(NOT_LETTER_OR_SPACE, "");
	}

	/**
	 * ������� ������� �� ������ ��������� �������
	 * @param str �������������� ������
	 * @return ������������ ������
	 */
	public static String replaceString(String str, String regexp)
	{
		return str.replaceAll(regexp, "");
	}

	/**
	 * @param str - �������� ������ ��� ��������������
	 * @return �������� ������ �� ��������� Windows-1251 � Windows-1252. ����������� ��� �������������� ����� ����� ��� ��������.
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
	 * @param data ������ ����
	 * @return ������ �������� ���������� ������������� � hex ����
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
	 * ����� ������ � �������
	 * @param strings ������
	 * @param string ������
	 * @return true == �������
	 */
	public static boolean contains(String[] strings, String string)
	{
// TODO ������������ � ���� ������ ����������� ������ ��� ���������� ������, � ������ ��������� ���������, �� ������ ��� �
// �������������� �������� ��������� ��������
		Arrays.sort(strings);
		if (Arrays.binarySearch(strings, string) >= 0)
			return true;
		return false;
	}
	/**
	 * @param amount �����
	 * @param isoCode ��� ������ (RUB, USD)
	 * @return ����� ��������
	 */
	public static String sumInWords(double amount, String isoCode)
	{
		return sumInWords(String.format("%1$.2f", amount), isoCode);
	}

	/**
	 * @param s ����� ������� "12121.23"
	 * @param isoCode ��� ������ (RUB, USD)
	 * @return ����� ��������
	 */
	public static String sumInWords(String s, String isoCode)
	{
		try
		{
			if (s.length() == 0) return "";
			if (isoCode.equals("RUB") || isoCode.equals("RUR"))
			{
				return new jAmount(new String[]{"�����", "�����", "������", "�������", "�������", "������", "M"}, true, s).toString();
			}
			else if (isoCode.equals("USD"))
			{
				return new jAmount(new String[]{"������", "�������", "��������", "����", "�����", "������", "M"}, true, s).toString();
			}
			else if (isoCode.equals("EUR"))
			{
				return new jAmount(new String[]{"����", "����", "����", "����", "�����", "������", "M"}, true, s).toString();
			}
			else
			{
				// ����� ����� �������� ��� ����������� ������
				return new jAmount(new String[]{"", "", "", "", "", "", "M"}, false, s).toString().trim();
			}
		}
		catch (Exception e)
		{
			log.error("������  ������ �������� ����� �� ���������",e);
			return "";
		}
	}

	/**
	 * ���������� ��������� ������.
	 * @param str - ������
	 * @param length - ����� �� ������� ���������� ��������
	 * @return ��������� � 0 �� length ������ � ������ ������ ��� ��� ������, ���� � ����� ������ length
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

		// ����������� ������. ����������� � �������� ���������� ��������:
		// String suff []   ������ ������������ (����������� ����������) �
		// String sum       ����� ��� ��������������
		public jAmount(String[] suff, boolean showcurr, String s)
		{
			this.suff = suff;
			this.showcurr = showcurr;
			BigDecimal decimal = new BigDecimal(s);
			// ����������� � ������� (�����, �������� � �.�.),
			// ����� ������ - ������� ������� �����
			decimal = decimal.multiply(new BigDecimal(100.00));
			summ = decimal.toBigInteger();
		}

		// �������� ������ (�������) ����� �����
		public String getRightPart()
		{
			return alignSumm(summ.remainder(hundred).abs().toString());
		}

		// ���� ����� ������ 10, �� ��������� �� ������������ "0"
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
				result.append("���� ");
			}
			divrem = divrem[0].divideAndRemainder(thousand);
			BigInteger quotient = divrem[0];
			BigInteger remainder = divrem[1];
			int group = 0;
			do
			{
				int value = remainder.intValue();
				result.insert(0, groupToString(value, group));
				// ��� ������� ������ ������� � ����� ��������������� ������
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
				// ������� ����� �����
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
			// �� �������� �������������� ����� ������ ����� ����������
			// ������������� ������ ���� � ������� ��������
			result.setCharAt(0, Character.toUpperCase(result.charAt(0)));

			// ��� ���� ���� ������ ��� � ����������: ��������� ������� !!!

			return result.toString();
		}

		// �������������� ������ ���� � ������
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
			// ������ ����� �� ��������, ������� � �����
			int rank = value / 100;
			switch (rank)
			{
				default:
					break;
				case 1:
					result = result.append("��� ");
					break;
				case 2:
					result = result.append("������ ");
					break;
				case 3:
					result = result.append("������ ");
					break;
				case 4:
					result = result.append("��������� ");
					break;
				case 5:
					result = result.append("������� ");
					break;
				case 6:
					result = result.append("�������� ");
					break;
				case 7:
					result = result.append("������� ");
					break;
				case 8:
					result = result.append("��������� ");
					break;
				case 9:
					result = result.append("��������� ");
					break;
			}
			// �����, �������
			rank = (value % 100) / 10;
			switch (rank)
			{
				default:
					break;
				case 2:
					result = result.append("�������� ");
					break;
				case 3:
					result = result.append("�������� ");
					break;
				case 4:
					result = result.append("����� ");
					break;
				case 5:
					result = result.append("��������� ");
					break;
				case 6:
					result = result.append("���������� ");
					break;
				case 7:
					result = result.append("��������� ");
					break;
				case 8:
					result = result.append("����������� ");
					break;
				case 9:
					result = result.append("��������� ");
					break;
			}
			// ���� ������� = 1, �������� ��������������� ��������. ����� - �������
			int rank10 = rank;
			rank = value % 10;
			if (rank10 == 1)
			{
				switch (rank)
				{
					case 0:
						result = result.append("������ ");
						break;
					case 1:
						result = result.append("����������� ");
						break;
					case 2:
						result = result.append("���������� ");
						break;
					case 3:
						result = result.append("���������� ");
						break;
					case 4:
						result = result.append("������������ ");
						break;
					case 5:
						result = result.append("���������� ");
						break;
					case 6:
						result = result.append("����������� ");
						break;
					case 7:
						result = result.append("���������� ");
						break;
					case 8:
						result = result.append("������������ ");
						break;
					case 9:
						result = result.append("������������ ");
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
						if (group == 1) // ������
						{
							result = result.append("���� ");
						}
						else
							// ������ ��� ������ (���� "Sex" ����������� ����������)
							if (suff[6].equals("M"))
							{
								result = result.append("���� ");
							}
						if (suff[6].equals("F"))
						{
							result = result.append("���� ");
						}
						break;
					case 2:
						if (group == 1) // ������
						{
							result = result.append("��� ");
						}
						else
							// ������ ��� ������ (���� "Sex" ����������� ����������)
							if (suff[6].equals("M"))
							{
								result = result.append("��� ");
							}
						if (suff[6].equals("F"))
						{
							result = result.append("��� ");
						}
						break;
					case 3:
						result = result.append("��� ");
						break;
					case 4:
						result = result.append("������ ");
						break;
					case 5:
						result = result.append("���� ");
						break;
					case 6:
						result = result.append("����� ");
						break;
					case 7:
						result = result.append("���� ");
						break;
					case 8:
						result = result.append("������ ");
						break;
					case 9:
						result = result.append("������ ");
						break;
				}
			}
			// �������� ������: ������, �������� � �.�.
			switch (group)
			{
				default:
					break;
				case 1:
					if (rank10 == 1)
					{
						result = result.append("����� ");
					}
					else
					{
						switch (rank)
						{
							default:
								result = result.append("����� ");
								break;
							case 1:
								result = result.append("������ ");
								break;
							case 2:
							case 3:
							case 4:
								result = result.append("������ ");
								break;
						}
					}
					break;
				case 2:
					if (rank10 == 1)
					{
						result = result.append("��������� ");
					}
					else
					{
						switch (rank)
						{
							default:
								result = result.append("��������� ");
								break;
							case 1:
								result = result.append("������� ");
								break;
							case 2:
							case 3:
							case 4:
								result = result.append("�������� ");
								break;
						}
					}
					break;
				case 3:
					if (rank10 == 1)
					{
						result = result.append("���������� ");
					}
					else
					{
						switch (rank)
						{
							default:
								result = result.append("���������� ");
								break;
							case 1:
								result = result.append("�������� ");
								break;
							case 2:
							case 3:
							case 4:
								result = result.append("��������� ");
								break;
						}
					}
					break;
				case 4:
					if (rank10 == 1)
					{
						result = result.append("���������� ");
					}
					else
					{
						switch (rank)
						{
							default:
								result = result.append("���������� ");
								break;
							case 1:
								result = result.append("�������� ");
								break;
							case 2:
							case 3:
							case 4:
								result = result.append("��������� ");
								break;
						}
					}
					break;
			}
			return result.toString();
		}
	}

	/**
	 * ������� ����� � ������ ���� 250-76 ��� 250= (��� �������� ����)
	 * @param amount �����
	 * @return c����� ������� �������
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
	 * ���������� ��� ������ �  ����������� ���������� onClick � �������� ��� ����������� ������������
	 * @param onClick  - �������� onClick
	 * @param text     - �������� ������
	 * @return  ��� ������
	 */
	public static String getLink(String onClick, String text)
	{
		if (!StringHelper.isEmpty(onClick))
			return "<a href=\"#\"  onclick=\"" + onClick + "\">" + text + "</a>";
		return text;
	}

	/**
	 * ��������������� ������� ������
	 * @param str - ������ ��� ��������������
	 * @return
	 */
	public static String translit(String str)
	{
	    if (str == null)
	        return "";

		// 1. ��������� �������������� (����.����� ������������ ��������� �������)
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

		// 2. ������������ ��������������
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

	//�������� ���. -> ���. ��� ���. -> ���.
	public static String translitToAnother(String str)
	{
		if (StringHelper.isEmpty(str))
			return str;
        String newStr = new String(str);

		//�������� ����� �������� ����, ��������� �� ���������� �������� (ya = �, zh = � � ��.)
		for (String multiChar : multiCharList)
		{
		   if (newStr.contains(multiChar))
		   {
			   newStr = newStr.replace(multiChar, charmapEngToRus.get(multiChar));
		   }
		}

		//������������ ��������������
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
	 * ���������� ������ � ���� ������� ������ ����� ������� ����� ��������� � ������ �� ���������
	 * @param strings ������ �����
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
		//�������� �� ���. � ����.
	    charmap.put('�', "a");
	    charmap.put('�', "b");
	    charmap.put('�', "v");
	    charmap.put('�', "g");
	    charmap.put('�', "d");
	    charmap.put('�', "e");
	    charmap.put('�', "e");
	    charmap.put('�', "zh");
	    charmap.put('�', "z");
	    charmap.put('�', "i");
	    charmap.put('�', "y");
	    charmap.put('�', "k");
	    charmap.put('�', "l");
	    charmap.put('�', "m");
	    charmap.put('�', "n");
	    charmap.put('�', "o");
	    charmap.put('�', "p");
	    charmap.put('�', "r");
	    charmap.put('�', "s");
	    charmap.put('�', "t");
	    charmap.put('�', "u");
	    charmap.put('�', "f");
	    charmap.put('�', "kh");
	    charmap.put('�', "ts");
	    charmap.put('�', "ch");
	    charmap.put('�', "sh");
	    charmap.put('�', "sch");
	    charmap.put('�', "");
	    charmap.put('�', "y");
	    charmap.put('�', "");
	    charmap.put('�', "e");
	    charmap.put('�', "yu");
	    charmap.put('�', "ya");

	    charmap.put('�', "A");
	    charmap.put('�', "B");
	    charmap.put('�', "V");
	    charmap.put('�', "G");
	    charmap.put('�', "D");
	    charmap.put('�', "E");
	    charmap.put('�', "E");
	    charmap.put('�', "ZH");
	    charmap.put('�', "Z");
	    charmap.put('�', "I");
	    charmap.put('�', "Y");
	    charmap.put('�', "K");
	    charmap.put('�', "L");
	    charmap.put('�', "M");
	    charmap.put('�', "N");
	    charmap.put('�', "O");
	    charmap.put('�', "P");
	    charmap.put('�', "R");
	    charmap.put('�', "S");
	    charmap.put('�', "T");
	    charmap.put('�', "U");
	    charmap.put('�', "F");
	    charmap.put('�', "KH");
	    charmap.put('�', "TS");
	    charmap.put('�', "CH");
	    charmap.put('�', "SH");
	    charmap.put('�', "SCH");
	    charmap.put('�', "");
	    charmap.put('�', "Y");
	    charmap.put('�', "");
	    charmap.put('�', "E");
	    charmap.put('�', "YU");
	    charmap.put('�', "YA");
		charmap.put('�',"-");   //���� �� �����

		//�������� �� ����. � ���.
		multiCharList.add("sch");
		multiCharList.add("sh");
		multiCharList.add("kh");
		multiCharList.add("ts");
		multiCharList.add("ch");
		multiCharList.add("zh");
		multiCharList.add("yu");
		multiCharList.add("ya");

		charmapEngToRus.put("a", "�");
	    charmapEngToRus.put("b", "�");
	    charmapEngToRus.put("v", "�");
	    charmapEngToRus.put("g", "�");
	    charmapEngToRus.put("d", "�");
	    charmapEngToRus.put("e", "�");
	    charmapEngToRus.put("zh", "�");
	    charmapEngToRus.put("z", "�");
	    charmapEngToRus.put("i", "�");
	    charmapEngToRus.put("y", "�");
	    charmapEngToRus.put("k", "�");
	    charmapEngToRus.put("l", "�");
	    charmapEngToRus.put("m", "�");
	    charmapEngToRus.put("n", "�");
	    charmapEngToRus.put("o", "�");
	    charmapEngToRus.put("p", "�");
	    charmapEngToRus.put("r", "�");
	    charmapEngToRus.put("s", "�");
	    charmapEngToRus.put("t", "�");
	    charmapEngToRus.put("u", "�");
	    charmapEngToRus.put("f", "�");
	    charmapEngToRus.put("kh", "�");
	    charmapEngToRus.put("ts", "�");
	    charmapEngToRus.put("ch", "�");
	    charmapEngToRus.put("sh", "�");
	    charmapEngToRus.put("sch", "�");
	    charmapEngToRus.put("y", "�");
	    charmapEngToRus.put("yu", "�");
	    charmapEngToRus.put("ya", "�");

	    charmapEngToRus.put("A", "�");
	    charmapEngToRus.put("B", "�");
	    charmapEngToRus.put("V", "�");
	    charmapEngToRus.put("G", "�");
	    charmapEngToRus.put("D", "�");
	    charmapEngToRus.put("E", "�");
	    charmapEngToRus.put("ZH", "�");
	    charmapEngToRus.put("Z", "�");
	    charmapEngToRus.put("I", "�");
	    charmapEngToRus.put("Y", "�");
	    charmapEngToRus.put("K", "�");
	    charmapEngToRus.put("L", "�");
	    charmapEngToRus.put("M", "�");
	    charmapEngToRus.put("N", "�");
	    charmapEngToRus.put("O", "�");
	    charmapEngToRus.put("P", "�");
	    charmapEngToRus.put("R", "�");
	    charmapEngToRus.put("S", "�");
	    charmapEngToRus.put("T", "�");
	    charmapEngToRus.put("U", "�");
	    charmapEngToRus.put("F", "�");
	    charmapEngToRus.put("KH", "�");
	    charmapEngToRus.put("TS", "�");
	    charmapEngToRus.put("CH", "�");
	    charmapEngToRus.put("SH", "�");
	    charmapEngToRus.put("SCH", "�");
	    charmapEngToRus.put("Y", "�");
	    charmapEngToRus.put("YU", "�");
	    charmapEngToRus.put("YA", "�");
		charmapEngToRus.put("-", "�");

		//���� ��� �������� ���. ��������� � ����. (��� ����������� ��������� ��������� ����-��)
		switchFromRusToEngCharMap.put('�',"q");
		switchFromRusToEngCharMap.put('�',"w");
		switchFromRusToEngCharMap.put('�',"e");
		switchFromRusToEngCharMap.put('�',"r");
		switchFromRusToEngCharMap.put('�',"t");
		switchFromRusToEngCharMap.put('�',"y");
		switchFromRusToEngCharMap.put('�',"u");
		switchFromRusToEngCharMap.put('�',"i");
		switchFromRusToEngCharMap.put('�',"o");
		switchFromRusToEngCharMap.put('�',"p");
		switchFromRusToEngCharMap.put('�',"[");
		switchFromRusToEngCharMap.put('�',"]");
		switchFromRusToEngCharMap.put('�',"a");
		switchFromRusToEngCharMap.put('�',"s");
		switchFromRusToEngCharMap.put('�',"d");
		switchFromRusToEngCharMap.put('�',"f");
		switchFromRusToEngCharMap.put('�',"g");
		switchFromRusToEngCharMap.put('�',"h");
		switchFromRusToEngCharMap.put('�',"j");
		switchFromRusToEngCharMap.put('�',"k");
		switchFromRusToEngCharMap.put('�',"l");
		switchFromRusToEngCharMap.put('�',";");
		switchFromRusToEngCharMap.put('�',"\\");
		switchFromRusToEngCharMap.put('�',"z");
		switchFromRusToEngCharMap.put('�',"x");
		switchFromRusToEngCharMap.put('�',"c");
		switchFromRusToEngCharMap.put('�',"v");
		switchFromRusToEngCharMap.put('�',"b");
		switchFromRusToEngCharMap.put('�',"n");
		switchFromRusToEngCharMap.put('�',"m");
		switchFromRusToEngCharMap.put('�',",");
		switchFromRusToEngCharMap.put('�',".");
		switchFromRusToEngCharMap.put('�',"Q");
		switchFromRusToEngCharMap.put('�',"W");
		switchFromRusToEngCharMap.put('�',"E");
		switchFromRusToEngCharMap.put('�',"R");
		switchFromRusToEngCharMap.put('�',"T");
		switchFromRusToEngCharMap.put('�',"Y");
		switchFromRusToEngCharMap.put('�',"U");
		switchFromRusToEngCharMap.put('�',"I");
		switchFromRusToEngCharMap.put('�',"O");
		switchFromRusToEngCharMap.put('�',"P");
		switchFromRusToEngCharMap.put('�',"[");
		switchFromRusToEngCharMap.put('�',"]");
		switchFromRusToEngCharMap.put('�',"A");
		switchFromRusToEngCharMap.put('�',"S");
		switchFromRusToEngCharMap.put('�',"D");
		switchFromRusToEngCharMap.put('�',"F");
		switchFromRusToEngCharMap.put('�',"G");
		switchFromRusToEngCharMap.put('�',"H");
		switchFromRusToEngCharMap.put('�',"J");
		switchFromRusToEngCharMap.put('�',"K");
		switchFromRusToEngCharMap.put('�',"L");
		switchFromRusToEngCharMap.put('�',";");
		switchFromRusToEngCharMap.put('�',"\\");
		switchFromRusToEngCharMap.put('?',"Z");
		switchFromRusToEngCharMap.put('�',"X");
		switchFromRusToEngCharMap.put('�',"C");
		switchFromRusToEngCharMap.put('�',"V");
		switchFromRusToEngCharMap.put('�',"B");
		switchFromRusToEngCharMap.put('�',"N");
		switchFromRusToEngCharMap.put('�',"M");
		switchFromRusToEngCharMap.put('�',",");
		switchFromRusToEngCharMap.put('�',".");
		switchFromRusToEngCharMap.put(' '," ");

		//���� ��� �������� ����. ��������� � ���.
		switchFromEngToRusCharMap.put('q',"�");
		switchFromEngToRusCharMap.put('w',"�");
		switchFromEngToRusCharMap.put('e',"�");
		switchFromEngToRusCharMap.put('r',"�");
		switchFromEngToRusCharMap.put('t',"�");
		switchFromEngToRusCharMap.put('y',"�");
		switchFromEngToRusCharMap.put('u',"�");
		switchFromEngToRusCharMap.put('i',"�");
		switchFromEngToRusCharMap.put('o',"�");
		switchFromEngToRusCharMap.put('p',"�");
		switchFromEngToRusCharMap.put('[',"�");
		switchFromEngToRusCharMap.put(']',"�");
		switchFromEngToRusCharMap.put('a',"�");
		switchFromEngToRusCharMap.put('s',"�");
		switchFromEngToRusCharMap.put('d',"�");
		switchFromEngToRusCharMap.put('f',"�");
		switchFromEngToRusCharMap.put('g',"�");
		switchFromEngToRusCharMap.put('h',"�");
		switchFromEngToRusCharMap.put('j',"�");
		switchFromEngToRusCharMap.put('k',"�");
		switchFromEngToRusCharMap.put('l',"�");
		switchFromEngToRusCharMap.put(';',"�");
		switchFromEngToRusCharMap.put('\\',"�");
		switchFromEngToRusCharMap.put('z',"�");
		switchFromEngToRusCharMap.put('x',"�");
		switchFromEngToRusCharMap.put('c',"�");
		switchFromEngToRusCharMap.put('v',"�");
		switchFromEngToRusCharMap.put('b',"�");
		switchFromEngToRusCharMap.put('n',"�");
		switchFromEngToRusCharMap.put('m',"�");
		switchFromEngToRusCharMap.put(',',"�");
		switchFromEngToRusCharMap.put('.',"�");
		switchFromEngToRusCharMap.put('Q',"�");
		switchFromEngToRusCharMap.put('W',"�");
		switchFromEngToRusCharMap.put('E',"�");
		switchFromEngToRusCharMap.put('R',"�");
		switchFromEngToRusCharMap.put('T',"�");
		switchFromEngToRusCharMap.put('Y',"�");
		switchFromEngToRusCharMap.put('U',"�");
		switchFromEngToRusCharMap.put('I',"�");
		switchFromEngToRusCharMap.put('O',"�");
		switchFromEngToRusCharMap.put('P',"�");
		switchFromEngToRusCharMap.put('[',"�");
		switchFromEngToRusCharMap.put(']',"�");
		switchFromEngToRusCharMap.put('A',"�");
		switchFromEngToRusCharMap.put('S',"�");
		switchFromEngToRusCharMap.put('D',"�");
		switchFromEngToRusCharMap.put('F',"�");
		switchFromEngToRusCharMap.put('G',"�");
		switchFromEngToRusCharMap.put('H',"�");
		switchFromEngToRusCharMap.put('J',"�");
		switchFromEngToRusCharMap.put('K',"�");
		switchFromEngToRusCharMap.put('L',"�");
		switchFromEngToRusCharMap.put(';',"�");
		switchFromEngToRusCharMap.put('\\',"�");
		switchFromEngToRusCharMap.put('Z',"?");
		switchFromEngToRusCharMap.put('X',"�");
		switchFromEngToRusCharMap.put('C',"�");
		switchFromEngToRusCharMap.put('V',"�");
		switchFromEngToRusCharMap.put('B',"�");
		switchFromEngToRusCharMap.put('N',"�");
		switchFromEngToRusCharMap.put('M',"�");
		switchFromEngToRusCharMap.put(',',"�");
		switchFromEngToRusCharMap.put('.',"�");
		switchFromEngToRusCharMap.put(' '," ");

		counts.add("2");
		counts.add("3");
		counts.add("4");
	}

	/**
	 * �����, ������� ����������������� ������ �������
	 */
	private static final String[] TRANSLIT_SEARCH_WORDS = new String[] { "������", "������", "���@��" };

	/**
	 * ������������������� ����������� ��� <TRANSLIT_SEARCH_WORDS>
	 */
	private static final String[] TRANSLIT_REPLACE_WORDS = new String[] { "Online", "OnLine", "OnLine" };

	/**
	 * ���������� �������� ��������������� � ������ ���������
	 * @param str - String �������������� ��������
	 * @param flag - 0 - ��� � ����. ���������, 1- ��� � ���. ���������, null- � � ����. � � ���.
	 * @return - String ��������������� ��������
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
	 * ���������� ����� "���"/"����" � ����������� �� ������������� �����
	 * @param count
	 * @return
	 */
	public static String calculateOccasions(Long count)
	{
		if (count > 10 && count < 20)
			return "���";

		String countStr = Long.toString(count);
		String lastChar = countStr.substring(countStr.length()-1);
		if (counts.contains(lastChar))
			return "����";
		return "���";
	}


}
