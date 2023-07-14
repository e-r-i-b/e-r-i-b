package com.rssl.phizicgate.mobilebank;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;

/**
 * @author osminin
 * @ created 16.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class Registrations3ListParser extends RegistrationsListParser
{
	/**
	 * –егул€рное выражение дл€ разбора строки, содержащей регистрацию
	 */
	private static final Pattern REGISTRATION_PATTERN = Pattern.compile(
			"CN=(\\d{15,16}|\\d{18}|\\d{19});" +
			"RST=([a-zA-Z0-9]+);" + // статус - люба€ комбинаци€ букв и цифр
			"TF=(F|S);" +
			"PHN=(7\\d{1,10});" +
			"CODE=([^;]+);" +
			"DATE=(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}|\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3});" +
			"SRC=(0|1|2|3|4|5|6);" +
			"CARDLIST=([^;]*);"
	);

	protected Matcher getMatcher(String regString)
	{
		return REGISTRATION_PATTERN.matcher(regString);
	}

	protected RegistrationInfo fillRegistrationInfo(Matcher matcher) throws ParseException
	{
		Registration3Info regInfo = new Registration3Info();
		regInfo.setCardNumber(matcher.group(1));
		regInfo.setStatus(matcher.group(2));
		regInfo.setTariff(matcher.group(3));
		regInfo.setPhoneNumber(matcher.group(4));
		regInfo.setMobileOperator(matcher.group(5));
		regInfo.setDate(matcher.group(6));
		regInfo.setSrc(matcher.group(7));
		regInfo.setCardList(parseCardList(matcher.group(8)));

		return regInfo;
	}
}
