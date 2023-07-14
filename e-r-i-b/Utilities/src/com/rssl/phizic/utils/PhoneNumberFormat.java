package com.rssl.phizic.utils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ������� ���������� �������
 * (������ ��� ��)
 * @author Erkin
 * @ created 27.04.2010
 * @ $Author$
 * @ $Revision$
 *
 * �����! ���������� ��������� ������ ������ �������� ����� �� ��� �����
 * ��. ���������� internalParse
 */
public enum PhoneNumberFormat implements Serializable
{
	/**
	 * ����������� ������ ������ �������� ��� IKFL
	 * ������ "+7 (###) #######"
	 */
	IKFL(Pattern.compile("" +
			// ��� ��, �.�. "+7"
			"\\+(7)" +
			// ��� ��������� � ��������� ��� ���, ��������, "(495)"
			"\\s?" + "\\((\\d{3,5})\\)" + "\\s?" +
			// ����� ��������, ��������, "8138707"
			"(\\d{5,7})"
	)) {
		public String format(PhoneNumber number)
		{
			return "+7" + " (" + number.operator() + ") " + number.abonent();
		}
	},

	/**
	 * ������ "7##########" ("7" + 10 ����)
	 */
	MOBILE_INTERANTIONAL(Pattern.compile("" +
			"(7)(\\d{3})(\\d{7})"
	)) {
		public String format(PhoneNumber number)
		{
			return "7" + number.operator() + number.abonent();
		}
	},

	/**
	 * ������ "(###) ###-##-##" (10 ����)
	 */
	MOBILE__P2P(Pattern.compile("" +
			"(.{0})\\((\\d{3})\\) (\\d{3}-\\d{2}-\\d{2})"
	)) {
		public String format(PhoneNumber number)
		{
			return number.operator() + number.abonent();
		}
	},

	/**
	 * ������ "(###) ### ## ##" (10 ����)
	 */
	ADDRESS_BOOK(Pattern.compile("" +
			"(.{0})\\((\\d{3})\\) (\\d{3}-\\d{2}-\\d{2})"
	)) {
		public String format(PhoneNumber number)
		{
			return "(" + number.operator() + ") " + number.abonent().substring(0, 3) + " " + number.abonent().substring(3, 5) + " " + number.abonent().substring(5);
		}
	},

	/**
	 * ������ "8##########" ("8" + 10 ����, ����������� ���������� "8") 
	 */
	MOBILE_RU(Pattern.compile("" +
			"(8)?(\\d{3})(\\d{7})"
	)) {
		public String format(PhoneNumber number)
		{
			return "8" + number.operator() + number.abonent();
		}
	},

	/**
	 * ������ ����� (� ����� ���������), 10 ����.
	 */
	SIMPLE_NUMBER(Pattern.compile("" +
			"(.{0})(\\d{3})(\\d{7})"
	)) {
		public String format(PhoneNumber number)
		{
			return number.operator() + number.abonent();
		}
	},

	/**
	 * ������ "+7(###)###-##-##"
	 */
	ADMIN_NUMBER(Pattern.compile("" +
			// ��� ��, �.�. "+7"
			"\\+(7)" +
			// ��� ��������� ��� ��������, ��������, "(495)"
			"\\((\\d{3})\\)" +
			// ����� ��������, ��������, "813-87-07"
			"(\\d{3}-\\d{2}-\\d{2})"
	)) {
		public String format(PhoneNumber number)
		{
			return "+7" + "(" + number.operator() + ")" + number.abonent().substring(0, 3) + "-" + number.abonent().substring(3, 5) + "-" + number.abonent().substring(5);
		}
	},

	/**
	 * ������ " +(�������������)7 (###) ###-##-##" (10 ����)
	 */
	P2P_NEW(Pattern.compile("" +
			"\\+?" + "(7)" + "\\s?" + "\\((\\d{3})\\)" + "\\s?" + "(\\d{3}-\\d{2}-\\d{2})"
	)) {
		public String format(PhoneNumber number)
		{
			return "7" + number.operator() + number.abonent();
		}
	};

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return ������ 
	 */
	public String pattern()
	{
		return pattern.pattern();
	}

	/**
	 * ���������, ������� �� ����� ��������
	 * @param numberAsString ������ � ������� ��������
	 * @return true, ���� ������� �������
	 */
	public static boolean check(String numberAsString)
	{
		try
		{
			PhoneNumberFormat.internalParse(numberAsString, PhoneNumberFormat.values());
			return true;
		}
		catch (NumberFormatException ignored)
		{
			return false;
		}
		catch (IllegalArgumentException ignored)
		{
			return false;
		}
	}

	/**
	 * �������������� ������ � ������
	 * @param number ����� ��������
	 * @return ������ � ������� ��������
	 */
	public abstract String format(PhoneNumber number);

	/**
	 * �������������� �������������� ������ ��������
	 * @param numberAsString ����� ��������
	 * @return "�������������" ������ ��������
	 */
	public static String formatPhoneId(String numberAsString)
	{
		PhoneNumber number = internalParse(numberAsString, values());
		return "-" + number.operator() + number.getAbonentVisiblePart();
	}

	/**
	 * ������������ ������ ��������
	 * @param number ����� ��������
	 * @return ������ � ������������� ������� �������� � �������� �������
	 */
	public String formatAsHidden(PhoneNumber number)
	{
		PhoneNumber hiddenNumber = number.hideAbonent();
		return format(hiddenNumber);
	}

	/**
	 * �������������� ������ ��������
	 * �� ���������� ������� � ��������
	 * (������� ������� ������� ��������� �������)
	 * @param numberAsString ������ � ������� �������� � ����������� �������
	 * @return ������ � ������� �������� � �������� �������
	 */
	public String translate(String numberAsString) throws NumberFormatException
	{
		PhoneNumber number = internalParse(numberAsString, values());
		return format(number);
	}

	/**
	 * ������������ ������ ��������
	 * @param numberAsString ������ � ������� �������� � ����������� �������
	 * @return ������ � ������� �������� � �������� �������
	 */
	public String translateAsHidden(String numberAsString) throws NumberFormatException
	{
		PhoneNumber number = internalParse(numberAsString, values());
		return formatAsHidden(number);
	}

	/**
	 * ������ ������, ���������� ���������� �����
	 * ����� ����� ���� � ������ (��� ����� ���� ������) ������ ���� ����� 10
	 * @param numberAsString ������
	 * @return ����� ��������
	 * @throws NumberFormatException
	 */
	public PhoneNumber parse(String numberAsString) throws NumberFormatException
	{
		return internalParse(numberAsString, this);
	}

	static PhoneNumber internalParse(String numberAsString, PhoneNumberFormat... formats) throws NumberFormatException
	{
		if (StringHelper.isEmpty(numberAsString))
			throw new IllegalArgumentException("�������� 'numberAsString' �� ����� ���� ������");
		if (ArrayUtils.isEmpty(formats))
			throw new IllegalArgumentException("�������� 'formats' �� ����� ���� ������");

		log.trace("������ ������ � ������� ��������: " + numberAsString);

		String operator = null;
		String abonent = null;

		log.trace("����������� ������� ������");
		boolean parsed = false;
		for (PhoneNumberFormat format : formats) {
			Matcher matcher = format.pattern.matcher(numberAsString);
			if (matcher.matches()) {
				operator = matcher.group(2);
				abonent = matcher.group(3);
				if (abonent.indexOf("-")!= -1) abonent = abonent.replace("-","");
				parsed = true;
				log.trace("������ ������ ��������: " + format + " (" + format.pattern + ")");
				break;
			}
		}

		if (!parsed) {
			log.trace("������ ������ �� ��������: " + numberAsString);
			throw new NumberFormatException("�� ��������� ������ �����: " + numberAsString);
		}

		if (operator.length() + abonent.length() != 10) {
			throw new NumberFormatException("�� ��������� ������ ����� " +
					"(����� ���� �� ������ ���� ������ ������ ���� ����� 10): " + numberAsString);
		}

		return new PhoneNumber("7", operator, abonent);
	}

	///////////////////////////////////////////////////////////////////////////

	private final Pattern pattern;

	private PhoneNumberFormat(Pattern pattern)
	{
		this.pattern = pattern;
	}

	private static final Log log = LogFactory.getLog(PhoneNumberFormat.class);
}
