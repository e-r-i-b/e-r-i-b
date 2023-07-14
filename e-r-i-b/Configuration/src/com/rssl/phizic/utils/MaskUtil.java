package com.rssl.phizic.utils;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.util.ApplicationUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang.StringUtils.join;
import static org.apache.commons.lang.StringUtils.repeat;

/**
 * @author Barinov
 * @ created 23.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class MaskUtil
{
	private static final Log log = LogFactory.getLog(MaskUtil.class);
	private static final int SEVEN = 7;
	private static final char CHAR_FOR_MODIFY = '#';
	private static final char CHAR_FOR_MASK = '*';

	/**
	 * Вывести маскированое значение в соответствии с заданной маской.
	 * Заменяемые значения в маске обохначены '#'.
	 * Игнорируемые(маскируемые символы) в маске задаются симовлом '*'.
	 * Другие символы маски остаются как есть. Например маска #(###)***#### наложеная на значение 79115869241 даст 7(911)***9241.
	 * @param mask - маска
	 * @param value - значение
	 * @return маскированое значение.
	 */
	public static String getMaskedValue(String mask, String value)
	{
		//если маска для поля не задана, или значение пустое либо уже маскировано - ничего не делаем.
		if(StringHelper.isEmpty(mask) || StringHelper.isEmpty(value) || value.contains(String.valueOf(CHAR_FOR_MASK)))
			return StringHelper.getEmptyIfNull(value);

		if(checkValueLengthForMask(mask, value))
		{
			StringBuilder sb = new StringBuilder();
			char[] maskArray = mask.toCharArray();
			char[] valueArray = value.toCharArray();
			int i = 0;
			for(char ch: maskArray)
			{
				if(CHAR_FOR_MODIFY == ch)
				{
					sb.append(valueArray[i]);
					i++;
				}
				else if(CHAR_FOR_MASK == ch)
				{
					sb.append(ch);
					i++;
				}
				else
					sb.append(ch);
			}
			return sb.toString();
		}
		return value;
	}

	/**
	 * Проверка на то что количество заменяемых и игнорируемых симовлов
	 * совпадает с длиной маскируемой строки.
	 * @param mask - маска
	 * @param value - маскируемое значение
	 * @return true - количество совпадает.
	 */
	private static boolean checkValueLengthForMask(String mask, String value)
	{
		int i = 0;
		for (char maskChar : mask.toCharArray())
			if (maskChar == CHAR_FOR_MODIFY || maskChar == CHAR_FOR_MASK)
				i++;
		return value.length() == i;
	}


	private static String getCutCardNumber(Pattern regex, String fullCardNumber, String mask)
	{
		if (regex == null)
			return fullCardNumber;
		Matcher matcher = regex.matcher(fullCardNumber);
		if (!matcher.matches())
			return fullCardNumber;
		String a = fullCardNumber;
		if (matcher.groupCount() == 2)
		{
			String masked = repeat(mask, 4);
			a = masked + matcher.group(2);
		}
		else if (matcher.groupCount() == 3)
		{
			String masked = repeat(mask, matcher.group(2).length());
			a = matcher.group(1) + masked + matcher.group(3);
		}
		return join(StringHelper.chunk(a, 4), " ");
	}

	/**
	 * получить урезанный номер карты по старому принципу(с заменой на *). используется для логов, sms, push, email сообщений.
	 * @param fullCardNumber полный номер карты
	 * @return урезанный в зависимости от настроек номер карты, чтобы отобразить клиенту.
	 */
	public static String getCutCardNumberForLog(String fullCardNumber)
	{
		return getCutCardNumberBase(fullCardNumber, MaskHelper.getOldCardNumberMask());
	}

	/**
	 * Получить актуальный паттерн маскировки номера карты
	 * @return паттерн
	 */
	private static Pattern getActualCardNumberMaskPattern()
	{
		CardsConfig cardsConfig = ConfigFactory.getConfig(CardsConfig.class);
		if (ApplicationUtil.isMobileApi()||ApplicationUtil.isATMApi())
			return cardsConfig.getCardNumberAtmRegexp();
		return cardsConfig.getCardNumberRegexp();
	}

	/**
	 * получить урезанный номер карты в зависимости от настроек приложения.
	 * @param fullCardNumber полный номер карты
	 * @return урезанный в зависимости от настроек номер карты, чтобы отобразить клиенту.
	 */
	public static String getCutCardNumber(String fullCardNumber)
	{
		if (StringHelper.isEmpty(fullCardNumber))
			return "";
		try
		{
			return getCutCardNumber(getActualCardNumberMaskPattern(), fullCardNumber, MaskHelper.getActualMaskSymbol());
		}
		catch (Exception e)
		{
			log.error("Ошибка маскирования номера карты", e);
			return "";
		}
	}

	/**
	 * Маскировать номера указанных карт
	 * @param fullCardNumbers - перечень номеров карт (never null can be empty)
	 * @return перечень маскированных номеров карт (never null)
	 */
	public static Collection<String> getCutCardNumber(Collection<String> fullCardNumbers)
	{
		if (fullCardNumbers.isEmpty())
			return Collections.emptyList();

		Collection<String> resultList = new ArrayList<String>(fullCardNumbers.size());
		for (String cardNumber : fullCardNumbers)
			resultList.add(getCutCardNumber(cardNumber));
		return resultList;
	}

	private static String getCutCardNumberBase(String fullCardNumber, String mask)
	{
		if (fullCardNumber == null)
			return "";
		try
		{
			CardsConfig cardsConfig = ConfigFactory.getConfig(CardsConfig.class);
			return getCutCardNumber(cardsConfig.getCardNumberRegexp(), fullCardNumber, mask);
		}
		catch (Exception e)
		{
			log.error("Ошибка маскирования номера карты", e);
			return "";
		}
	}

	/**
	 * Получение номера карты для печати на чеке
	 * @param fullCardNumber полный номер карты
	 * @return номер карты, урезанный в зависимости от настроек печати на чеке
	 */
	public static String getCutCardNumberPrint(String fullCardNumber)
	{
		if (fullCardNumber == null)
			return "";
		try
		{
			CardsConfig cardsConfig = ConfigFactory.getConfig(CardsConfig.class);
			return getCutCardNumber(cardsConfig.getCardNumberPrintRegexp(), fullCardNumber, MaskHelper.getOldCardNumberMask());
		}
		catch (Exception e)
		{
			log.error("Ошибка маскирования номера карты", e);
			return "";
		}
	}

	/**
	 * Получение от переданной строки последние 7 символов
	 * @param forCut -  строка для препарирования
	 * @return - возвращет последние 7 символов от переданной строки
	 * вернет пустую строку, если изначально была передана пустая строка
	 * вернет необрезанную строку, если ее длина короче переданного кол-ва символов
	 */
	public static String cutStringByLastSevenSymbols(String forCut)
	{
		return cutString(forCut, SEVEN);
	}
	/**
	 * Получение от переданной строки последние n символов
	 * @param forCut -  строка для препарирования
	 * @param countCut - кол-во символов, которые нужно оставить (с конца)
	 * @return - возвращет последние n символов от переданной строки
	 * вернет пустую строку, если изначально была передана пустая строка
	 * вернет необрезанную строку, если ее длина короче переданного кол-ва символов
	 */
	private static String cutString(String forCut, int countCut)
	{
		if (StringHelper.isEmpty(forCut))
			return "";
		
		int length = forCut.length();
		if (length < countCut)
			return forCut;

		return forCut.substring(forCut.length() - countCut);
	}

	/**
	 * Маскирует "средние" символы в строке.
	 *
	 * @param input входная строка.
	 * @param maskStartPosition левая позиция, с которой необходимо начать маскирование.
	 * @param maskEndPosition правая позиция, с которой  необходимо начать маскирование.
	 * @return
	 */
	public static String maskString(String input, int maskStartPosition, int maskEndPosition)
	{
		return maskString(input, maskStartPosition, maskEndPosition, "*");
	}

	public static String maskString(String input, int maskStartPosition, int maskEndPosition, String charForMask)
	{
		if (StringHelper.isEmpty(input))
			return input;

		int x0 = maskStartPosition;
		int x1 = input.length() - maskEndPosition;
		if (x0 >= x1)
			return input;

		StringBuilder sb = new StringBuilder(input.length());
		sb.append(input.substring(0, x0));
		for (int i = x0; i < x1; i++)
			sb.append(charForMask);

		sb.append(input.substring(x1));
		return sb.toString();
	}

	/**
	 * Маскирует номер телефона
	 * @param number - номер телефона (never null)
	 * @return маскированный номер телефона в виде строки
	 */
	public static String maskPhoneNumber(PhoneNumber number)
	{
		return PhoneNumberFormat.MOBILE_INTERANTIONAL.format(number.hideAbonent());
	}

	/**
	 * Маскирует номер телефона
	 * @param number - номер телефона (never null)
	 * @return маскированный номер телефона в виде строки
	 */
	public static String maskPhoneNumber(String number)
	{
		return PhoneNumberFormat.MOBILE_INTERANTIONAL.format(PhoneNumber.fromString(number).hideAbonent());
	}

	/**
	 * Маскирование первых символов строки.
	 * Если строка короче, чем количество символов для маскирования, вернет маск. символы, равные длине строки.
	 * @param input - строка для маскирования
	 * @param maskedCharsNumber - количество символов, которые надо замаскировать
	 * @return
	 */
	public static String maskFirstChars(String input, int maskedCharsNumber)
	{
		int strLength = input.length();
		if (strLength > maskedCharsNumber)
			return maskString(input, 0, input.length() - maskedCharsNumber);
		else
			return input.replaceAll(".", "*");
	}

	/**
	 * маскирование абонентского номера (прячутся первые 3 символа)
	 * @param input
	 * @return
	 */
	public static String maskAbonent(String input)
	{
		return maskFirstChars(input, 3);
	}

	/**
	 * Полностью маскировать строку (длина не более 255)
	 * @param input
	 * @return
	 */
	public static String maskAll(String input)
	{
		return maskFirstChars(input, 255);
	}

	/**
	 * маскирование улицы (прячутся первые 4 символа)
	 * @param input
	 * @return
	 */
	public static String maskStreet(String input)
	{
		return maskFirstChars(input, 4);
	}

	/**
	 * Полностью маскированная дата формата ДД.ММ.ГГГГ
	 * @return **.**.***
	 */
	public static String getMaskedDate()
	{
		return "**.**.****";
	}

	/**
	 * Возвращает маскированный e-mail в виде ko****@gmail.com
	 * @param eMail адрес электронной почты
	 * @return маскированный адрес
	 */
	public static String getMaskedEMail(String eMail)
	{
		if(StringHelper.isEmpty(eMail))
			return "";

		int start_pos = eMail.indexOf('@');
		if(start_pos <= 2)
			return eMail;

		int end_pos = eMail.length();
		StringBuilder builder = new StringBuilder();
		builder.append(eMail.substring(0, 2));
		builder.append("****");
		builder.append(eMail.substring(start_pos, end_pos));
		return builder.toString();
	}

	/**
	 * Возвращает О.И.Ф. в формате Иван Иванович И.
	 * @param oif О.И.Ф.
	 * @return И.Ф.О.
	 */
	public static String getMaskedFIO(String oif)
	{
		if(StringHelper.isEmpty(oif))
			return " ";

		String fio[] = oif.split(" ");
		String firstName = fio[1];
		String patrName = "";

		if(fio.length > 2)
			patrName = fio[2];

		String surName = fio[0];

		return getMaskedFIO(firstName, patrName, surName);
	}

	/**
	 * Возвращает ФИО в формате Иван Иванович И.
	 * @param firstName - имя
	 * @param patrName - отчество
	 * @param lastName - фамилия
	 * @return ФИО в указанном формате
	 */
	public static String getMaskedFIO(String firstName, String patrName, String lastName)
	{
		if (StringHelper.isEmpty(firstName) || StringHelper.isEmpty(lastName))
		{
			return " ";
		}

		StringBuilder builder = new StringBuilder();

		builder.append(Character.toUpperCase(firstName.charAt(0)));
		builder.append(firstName.substring(1).toLowerCase());

		if (StringHelper.isNotEmpty(patrName))
		{
			builder.append(" ").append(Character.toUpperCase(patrName.charAt(0)));
			builder.append(patrName.substring(1).toLowerCase());
		}

		builder.append(" ").append(lastName.toUpperCase().charAt(0)).append(".");
		return builder.toString();
	}

	/**
	 * Маскирует все цифры в строке
	 * @param in исходная строка
	 * @return
	 */
	public static String maskAllNumericChars(String in)
	{
		if(StringHelper.isEmpty(in))
			return "";
		return in.replaceAll("\\d", String.valueOf(MaskHelper.getActualMaskSymbol()));
	}

	/**
	 * Маскирует строку со значением "Серия и номер" заявки СБНКД
	 * @param str исходная строка
	 * @return
	 */
	public static String maskSeriesAndNumber(String str)
	{
		if(StringHelper.isEmpty(str))
		{
			return "";
		}
		String series = str.lastIndexOf(" ") > 0 ? str.substring(0, str.lastIndexOf(" ")) : "";
		String number = str.lastIndexOf(" ") > 0 ? str.substring(str.lastIndexOf(" ") + 1, str.length()) : str;
		number = number.replaceFirst(".{"+ String.valueOf(4) + "}", "••••");
		return series + " " + number;
	}
}
