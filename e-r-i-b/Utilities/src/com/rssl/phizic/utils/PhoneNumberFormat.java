package com.rssl.phizic.utils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Форматы телефонных номеров
 * (только для РФ)
 * @author Erkin
 * @ created 27.04.2010
 * @ $Author$
 * @ $Revision$
 *
 * ВАЖНО! Регулярное выражение номера должно состоять ровно из трёх групп
 * См. реализацию internalParse
 */
public enum PhoneNumberFormat implements Serializable
{
	/**
	 * Стандартный формат номера телефона для IKFL
	 * Шаблон "+7 (###) #######"
	 */
	IKFL(Pattern.compile("" +
			// код РФ, т.е. "+7"
			"\\+(7)" +
			// код оператора с пробелами или без, например, "(495)"
			"\\s?" + "\\((\\d{3,5})\\)" + "\\s?" +
			// номер абонента, например, "8138707"
			"(\\d{5,7})"
	)) {
		public String format(PhoneNumber number)
		{
			return "+7" + " (" + number.operator() + ") " + number.abonent();
		}
	},

	/**
	 * Шаблон "7##########" ("7" + 10 цифр)
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
	 * Шаблон "(###) ###-##-##" (10 цифр)
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
	 * Шаблон "(###) ### ## ##" (10 цифр)
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
	 * Шаблон "8##########" ("8" + 10 цифр, допускается отсутствие "8") 
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
	 * Только номер (с кодом оператора), 10 цифр.
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
	 * Шаблон "+7(###)###-##-##"
	 */
	ADMIN_NUMBER(Pattern.compile("" +
			// код РФ, т.е. "+7"
			"\\+(7)" +
			// код оператора без пробелов, например, "(495)"
			"\\((\\d{3})\\)" +
			// номер абонента, например, "813-87-07"
			"(\\d{3}-\\d{2}-\\d{2})"
	)) {
		public String format(PhoneNumber number)
		{
			return "+7" + "(" + number.operator() + ")" + number.abonent().substring(0, 3) + "-" + number.abonent().substring(3, 5) + "-" + number.abonent().substring(5);
		}
	},

	/**
	 * Шаблон " +(необязательно)7 (###) ###-##-##" (10 цифр)
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
	 * @return формат 
	 */
	public String pattern()
	{
		return pattern.pattern();
	}

	/**
	 * Проверяет, валиден ли номер телефона
	 * @param numberAsString строка с номером телефона
	 * @return true, если телефон валиден
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
	 * Форматирование номера в строку
	 * @param number номер телефона
	 * @return строка с номером телефона
	 */
	public abstract String format(PhoneNumber number);

	/**
	 * Форматирование идентификатора номера телефона
	 * @param numberAsString номер телефона
	 * @return "идентификатор" номера телефона
	 */
	public static String formatPhoneId(String numberAsString)
	{
		PhoneNumber number = internalParse(numberAsString, values());
		return "-" + number.operator() + number.getAbonentVisiblePart();
	}

	/**
	 * Маскирование номера телефона
	 * @param number номер телефона
	 * @return строка с маскированным номером телефона в заданном формате
	 */
	public String formatAsHidden(PhoneNumber number)
	{
		PhoneNumber hiddenNumber = number.hideAbonent();
		return format(hiddenNumber);
	}

	/**
	 * Преобразование номера телефона
	 * из некоторого формата в заданный
	 * (который задаётся текущим инстансом формата)
	 * @param numberAsString строка с номером телефона в неизвестном формате
	 * @return строка с номером телефона в заданном формате
	 */
	public String translate(String numberAsString) throws NumberFormatException
	{
		PhoneNumber number = internalParse(numberAsString, values());
		return format(number);
	}

	/**
	 * Маскирование номера телефона
	 * @param numberAsString строка с номером телефона в неизвестном формате
	 * @return строка с номером телефона в заданном формате
	 */
	public String translateAsHidden(String numberAsString) throws NumberFormatException
	{
		PhoneNumber number = internalParse(numberAsString, values());
		return formatAsHidden(number);
	}

	/**
	 * Разбор строки, содержащий телефонный номер
	 * Общее число цифр в строке (без учёта кода страны) должно быть равно 10
	 * @param numberAsString строка
	 * @return номер телефона
	 * @throws NumberFormatException
	 */
	public PhoneNumber parse(String numberAsString) throws NumberFormatException
	{
		return internalParse(numberAsString, this);
	}

	static PhoneNumber internalParse(String numberAsString, PhoneNumberFormat... formats) throws NumberFormatException
	{
		if (StringHelper.isEmpty(numberAsString))
			throw new IllegalArgumentException("Аргумент 'numberAsString' не может быть пустым");
		if (ArrayUtils.isEmpty(formats))
			throw new IllegalArgumentException("Аргумент 'formats' не может быть пустым");

		log.trace("Разбор строки с номером телефона: " + numberAsString);

		String operator = null;
		String abonent = null;

		log.trace("Определение формата номера");
		boolean parsed = false;
		for (PhoneNumberFormat format : formats) {
			Matcher matcher = format.pattern.matcher(numberAsString);
			if (matcher.matches()) {
				operator = matcher.group(2);
				abonent = matcher.group(3);
				if (abonent.indexOf("-")!= -1) abonent = abonent.replace("-","");
				parsed = true;
				log.trace("Формат номера определён: " + format + " (" + format.pattern + ")");
				break;
			}
		}

		if (!parsed) {
			log.trace("Формат номера не определён: " + numberAsString);
			throw new NumberFormatException("Не правильно набран номер: " + numberAsString);
		}

		if (operator.length() + abonent.length() != 10) {
			throw new NumberFormatException("Не правильно набран номер " +
					"(число цифр не считая кода страны должно быть равно 10): " + numberAsString);
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
