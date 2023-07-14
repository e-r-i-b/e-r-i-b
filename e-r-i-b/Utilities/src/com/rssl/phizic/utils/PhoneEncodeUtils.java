package com.rssl.phizic.utils;

import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * Работа с кодированием телефонов для отображения во фронте
 * Код - порядковый номер телефона в отсортированном списке телефонов (начиная с 0)
 * @author Puzikov
 * @ created 28.03.14
 * @ $Author$
 * @ $Revision$
 */

public class PhoneEncodeUtils
{
	/**
	 * Вернуть закодированные телефоны c маскированным номером
	 * Код - порядковый номер телефона в отсортированном списке (начиная с 0)
	 * @param phoneNumbers номера телефонов
	 * @return мапа код на маскированный номер телефона
	 * @see #decodePhoneNumbers(java.util.Set, java.util.Set, boolean)
	 */
	public static Map<String, String> encodePhoneNumbers(Set<String> phoneNumbers)
	{
		Map<String, String> result = new TreeMap<String, String>();

		int code = 0;
		for (String phoneNumber : getSortedPhoneNumbers(phoneNumbers))
		{
			String maskedPhoneNumber = PhoneNumberUtil.getCutPhoneNumber(phoneNumber);
			result.put(String.valueOf(code), maskedPhoneNumber);
			code++;
		}

		return result;
	}

	/**
	 * Раскодировать номера телефонов
	 * Код - порядковый номер телефона в отсортированном списке (начиная с 0)
	 * @param phonesToDecode коды телефонов
	 * @param initialPhoneNumbers исходный список номеров телефонов
	 * @param unencoded допускается ли наличие незакодированных телефонов
	 * @return восстановленные номера телефонов
	 * @see #encodePhoneNumbers(java.util.Set)
	 */
	public static Set<String> decodePhoneNumbers(Set<String> phonesToDecode, Set<String> initialPhoneNumbers, boolean unencoded)
	{
		Set<String> result = new HashSet<String>();

		String[] sortedPhoneNumbers = getSortedPhoneNumbers(initialPhoneNumbers);
		for (String phoneCode : phonesToDecode)
		{
			result.add(decodePhoneNumber(sortedPhoneNumbers, phoneCode, unencoded));
		}

		return result;
	}

	/**
	 * Раскодировать номера телефонов
	 * Код - порядковый номер телефона в отсортированном списке (начиная с 0)
	 * @param phonesToDecode коды телефонов
	 * @param initialPhoneNumbers исходный список номеров телефонов
	 * @param unencoded допускается ли наличие незакодированных телефонов
	 * @return мап: key-закодированный номер, value - восстановленный номер телефона
	 * @see #encodePhoneNumbers(java.util.Set)
	 */
	public static Map<String, String> decodePhoneNumbersForRestoreRemoved(Set<String> phonesToDecode, Set<String> initialPhoneNumbers, boolean unencoded)
	{
		Map<String, String> result = new HashMap<String, String>(phonesToDecode.size());

		String[] sortedPhoneNumbers = getSortedPhoneNumbers(initialPhoneNumbers);
		for (String phoneCode : phonesToDecode)
		{
			result.put(phoneCode, decodePhoneNumber(sortedPhoneNumbers, phoneCode, unencoded));
		}

		return result;
	}

	/**
	 * Закодировать номер телефона
	 * Код - порядковый номер телефона в отсортированном списке (начиная с 0)
	 * @param phoneNumbers номера телефонов
	 * @param phoneToEncode телефон, который нужно закодировать
	 * @return код телефона в контексте данного набора телефонов
	 * @see #decodePhoneNumber(java.util.Set, String, boolean)
	 */
	public static String encodePhoneNumber(Set<String> phoneNumbers, String phoneToEncode)
	{
		if (!phoneNumbers.contains(phoneToEncode))
			throw new IllegalArgumentException("Телефон не найден в списке телефонов");

		String[] sortedPhoneNumbers = getSortedPhoneNumbers(phoneNumbers);
		return String.valueOf(ArrayUtils.indexOf(sortedPhoneNumbers, phoneToEncode));
	}

	/**
	 * Раскодировать номер телефона
	 * Код - порядковый номер телефона в отсортированном списке (начиная с 0)
	 * @param phoneNumbers номера телефонов
	 * @param phoneToDecode телефон, который нужно раскодировать
	 * @param unencoded допускается ли наличие незакодированных телефонов
	 * @return телефон в контексте данного набора телефонов
	 * @see #encodePhoneNumber(java.util.Set, String)
	 */
	public static String decodePhoneNumber(Set<String> phoneNumbers, String phoneToDecode, boolean unencoded)
	{
		return decodePhoneNumber(getSortedPhoneNumbers(phoneNumbers), phoneToDecode, unencoded);
	}

	private static String[] getSortedPhoneNumbers(Set<String> phoneNumbers)
	{
		String[] sortedPhoneNumbers = phoneNumbers.toArray(new String[phoneNumbers.size()]);
		Arrays.sort(sortedPhoneNumbers);
		return sortedPhoneNumbers;
	}

	private static String decodePhoneNumber(String[] sortedPhoneNumbers, String phoneToDecode, boolean unencoded)
	{
		//если телефон в незакодированном виде и допускается их наличие - добавить
		if (unencoded && PhoneNumberFormat.check(phoneToDecode))
		{
			return PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phoneToDecode);
		}
		//иначе - раскодировать
		else
		{
			int code = 0;
			try
			{
				code = Integer.parseInt(phoneToDecode);
			}
			catch (NumberFormatException e)
			{
				throw new IllegalArgumentException("Неверный код телефона", e);
			}
			if (code > sortedPhoneNumbers.length)
				throw new IllegalArgumentException("Неверный код телефона");

			return sortedPhoneNumbers[code];
		}
	}
}
