package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.utils.StringHelper;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Парсер для разбора строки,
 * содержащей список регистраций
 * @author Erkin
 * @ created 20.04.2010
 * @ $Author$
 * @ $Revision$
 */
class RegistrationsListParser implements Parser<List<RegistrationInfo>>
{
	private static final String PARSE_ERROR_MESSAGE             = "Выходная строка с описанием регистрации не соответствует установленному формату: ";
	private static final String PARSE_CARD_LIST_ERROR_MESSAGE   = "Выходная строка со списком записей 'активная_карта-статус' не соответствует установленному формату: ";

	/**
	 * Регулярное выражение для разбора строки,
	 * содержащей регистрацию
	 */
	private static final Pattern REGISTRATION_PATTERN = Pattern.compile(
			"CN=(\\d{15,16}|\\d{18}|\\d{19});" +
			"RST=([a-zA-Z0-9]+);" + // статус - любая комбинация букв и цифр
			"TF=(F|S);" +
			"PHN=(7\\d{1,10});" +
			"CODE=([^;]+);" +
			"CARDLIST=([^;]*);"
	);

	/**
	 * Регулярное выражение для разбора строки,
	 * содержащей запись 'активная_карта-статус'
	 */
	private static final Pattern CARDLIST_PATTERN = Pattern.compile(
			"(\\d{15,16}|\\d{18}|\\d{19})\\-(A|I)");

	public List<RegistrationInfo> parse(String string) throws ParseException
	{
		if (StringHelper.isEmpty(string))
			return Collections.emptyList();

		String[] registrations = string.split("#");
		List<RegistrationInfo> result = new ArrayList<RegistrationInfo>(registrations.length);

		for (String regString : registrations)
		{
			if (StringHelper.isEmpty(regString))
				continue;

			Matcher matcher = getMatcher(regString);

			if (!matcher.matches())
				throw new ParseException(PARSE_ERROR_MESSAGE + string, 0);

			result.add(fillRegistrationInfo(matcher));
		}

		return result;
	}

	protected Matcher getMatcher(String regString)
	{
		return REGISTRATION_PATTERN.matcher(regString);
	}

	protected RegistrationInfo fillRegistrationInfo(Matcher matcher) throws ParseException
	{
		RegistrationInfo regInfo = new RegistrationInfo();
		regInfo.setCardNumber(matcher.group(1));
		regInfo.setStatus(matcher.group(2));
		regInfo.setTariff(matcher.group(3));
		regInfo.setPhoneNumber(matcher.group(4));
		regInfo.setMobileOperator(matcher.group(5));
		regInfo.setCardList(parseCardList(matcher.group(6)));

		return regInfo;
	}

	/**
	 * разбирает строку со списком пар
	 * 'номер_карты-статус_карты', разделённых запятой
	 * @param string - строка
	 * @return меп пар "номер_карты -> статус_карты"
	 * @throws ParseException
	 */
	protected Map<String, String> parseCardList(String string) throws ParseException
	{
		if (StringHelper.isEmpty(string))
			return Collections.emptyMap();

		String[] cards = string.split(",");
		Map<String, String> result = new LinkedHashMap<String, String>(cards.length);

		for (String cardString : cards)
		{
			if (StringHelper.isEmpty(cardString))
				continue;

			Matcher matcher = CARDLIST_PATTERN.matcher(cardString);

			if (!matcher.matches())
				throw new ParseException(PARSE_CARD_LIST_ERROR_MESSAGE + string, 0);

			result.put(matcher.group(1), matcher.group(2));
		}

		return result;
	}
}
