package com.rssl.phizic.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang.StringUtils.join;

/**
 * @author Gulov
 * @ created 17.06.2010
 * @ $Authors$
 * @ $Revision$
 */
public class PhoneNumberUtil
{
	/**
	 * Маскирование номера телефона
	 * Используется при отображении профиля клиента и в мобильном банке
	 * @param fullPhoneNumber строка с номером телефона в неизвестном формате
	 * @return строка с номером телефона в заданном формате
	 */
	public static String getCutPhoneNumber(String fullPhoneNumber)
	{
		if (StringHelper.isEmpty(fullPhoneNumber))
			return "";

		try
		{
			return PhoneNumberFormat.IKFL.translateAsHidden(fullPhoneNumber);
		}
		catch(NumberFormatException nfe)
		{
			return fullPhoneNumber;
		}
	}
	/**
	 * Маскирование номера телефона
	 * Используется в завке на регистрацию в программе лояльности
	 * @param fullPhoneNumber строка с номером телефона в неизвестном формате
	 * @return строка с номером телефона в формате SIMPLE_NUMBER
	 */
	public static String getSimplePhoneNumber(String fullPhoneNumber)
	{
		if (StringHelper.isEmpty(fullPhoneNumber))
			return "";

		try
		{
			return PhoneNumberFormat.SIMPLE_NUMBER.translateAsHidden(fullPhoneNumber);
		}
		catch(NumberFormatException nfe)
		{
			return fullPhoneNumber;
		}
	}

	/**
	 * +7 (###) #######
	 * Преобразует номер телефона в неизвестном формате к формату IKFL
	 * @param phoneNumber номер телефона в неизвестном формате
	 * @return номер телефона в формате IKFL
	 */
	public static String getFullIKFLPhoneNumber(String phoneNumber)
	{
		return getFormattedPhoneNumber(phoneNumber, PhoneNumberFormat.IKFL);
	}

	/**
	 * Преобразует номер телефона в неизвестном формате к формату SIMPLE_NUMBER (10 цифр)
	 * @param phoneNumber номер телефона в неизвестном формате
	 * @return номер телефона в формате SIMPLE_NUMBER
	 */
	public static String getFullSimplePhoneNumber(String phoneNumber)
	{
		return getFormattedPhoneNumber(phoneNumber, PhoneNumberFormat.SIMPLE_NUMBER);
	}

	/**
	 * +7(###)###-##-##
	 * Преобразует номер телефона в неизвестном формате к формату ADMIN_NUMBER
	 * @param phoneNumber номер телефона в неизвестном формате
	 * @return номер телефона в формате ADMIN_NUMBER
	 */
	public static String getAdminPhoneNumber(String phoneNumber)
	{
		return getFormattedPhoneNumber(phoneNumber, PhoneNumberFormat.ADMIN_NUMBER);
	}

	/**
	 * 7########## (7+10 цифр)
	 * Преобразует номер телефона в неизвестном формате к формату MOBILE_INTERANTIONAL
	 * @param phoneNumber номер телефона в неизвестном формате
	 * @return номер телефона в формате MOBILE_INTERANTIONAL
	 */
	public static String getMobileInternationalPhoneNumber(String phoneNumber)
	{
		return getFormattedPhoneNumber(phoneNumber, PhoneNumberFormat.MOBILE_INTERANTIONAL);
	}

	private static String getFormattedPhoneNumber(String phoneNumber, PhoneNumberFormat format)
	{
		if (StringHelper.isEmpty(phoneNumber))
			return "";

		try
		{
			return format.translate(phoneNumber);
		}
		catch (NumberFormatException ignored)
		{
			return phoneNumber;
		}
	}

	/**
	 * Маскирование номеров телефонов
	 * Используется при отображении профиля клиента и в мобильном банке
	 * @param fullPhoneNumberList список строк с номерами телефонов в неизвестном формате
	 * @return строка с номером телефона в заданном формате
	 */
	public static String getCutPhoneNumbers(Collection<String> fullPhoneNumberList)
	{
		if (fullPhoneNumberList == null || fullPhoneNumberList.isEmpty())
			return "";

		List<String> cutPhoneNumbersList = new ArrayList<String>(fullPhoneNumberList.size());
		for (String fullPhoneNumber : fullPhoneNumberList)
			cutPhoneNumbersList.add(getCutPhoneNumber(fullPhoneNumber));
		return join(cutPhoneNumbersList, ", ");
	}

	/**
	 * Удалить все лишние символы (не цифры) и оставить последние 10 цифр номера
	 * @param phoneNumber исходный номер
	 * @return преобразованный номер
	 */
	public static String getNormalizePhoneNumberString(String phoneNumber)
	{
		if (StringHelper.isEmpty(phoneNumber))
			return "";

		String result = phoneNumber.replaceAll("\\D", "");
		if (result.length() > 10)
			result = result.substring(result.length()-10);

		return result;
	}

	/**
	 * Преобразование и маскирование номеров телефонов
	 * @param phones - список номеров
	 * @param format - формат номера телефона
	 * @return - строка номеров телефонов
	 */
	public static String translateAndHidePhoneNumbers(Collection<String> phones, PhoneNumberFormat format)
	{
		StringBuilder result = new StringBuilder();
		for (String phone : phones)
		{
			String phoneMB = format.translate(phone);
			result.append(getCutPhoneNumber(phoneMB));
			result.append(", ");
		}
		String temp = result.toString();
		return temp.substring(0, temp.length() - 2);
	}
}
