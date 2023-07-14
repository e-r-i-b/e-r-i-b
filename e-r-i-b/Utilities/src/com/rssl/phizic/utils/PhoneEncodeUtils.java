package com.rssl.phizic.utils;

import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * ������ � ������������ ��������� ��� ����������� �� ������
 * ��� - ���������� ����� �������� � ��������������� ������ ��������� (������� � 0)
 * @author Puzikov
 * @ created 28.03.14
 * @ $Author$
 * @ $Revision$
 */

public class PhoneEncodeUtils
{
	/**
	 * ������� �������������� �������� c ������������� �������
	 * ��� - ���������� ����� �������� � ��������������� ������ (������� � 0)
	 * @param phoneNumbers ������ ���������
	 * @return ���� ��� �� ������������� ����� ��������
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
	 * ������������� ������ ���������
	 * ��� - ���������� ����� �������� � ��������������� ������ (������� � 0)
	 * @param phonesToDecode ���� ���������
	 * @param initialPhoneNumbers �������� ������ ������� ���������
	 * @param unencoded ����������� �� ������� ���������������� ���������
	 * @return ��������������� ������ ���������
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
	 * ������������� ������ ���������
	 * ��� - ���������� ����� �������� � ��������������� ������ (������� � 0)
	 * @param phonesToDecode ���� ���������
	 * @param initialPhoneNumbers �������� ������ ������� ���������
	 * @param unencoded ����������� �� ������� ���������������� ���������
	 * @return ���: key-�������������� �����, value - ��������������� ����� ��������
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
	 * ������������ ����� ��������
	 * ��� - ���������� ����� �������� � ��������������� ������ (������� � 0)
	 * @param phoneNumbers ������ ���������
	 * @param phoneToEncode �������, ������� ����� ������������
	 * @return ��� �������� � ��������� ������� ������ ���������
	 * @see #decodePhoneNumber(java.util.Set, String, boolean)
	 */
	public static String encodePhoneNumber(Set<String> phoneNumbers, String phoneToEncode)
	{
		if (!phoneNumbers.contains(phoneToEncode))
			throw new IllegalArgumentException("������� �� ������ � ������ ���������");

		String[] sortedPhoneNumbers = getSortedPhoneNumbers(phoneNumbers);
		return String.valueOf(ArrayUtils.indexOf(sortedPhoneNumbers, phoneToEncode));
	}

	/**
	 * ������������� ����� ��������
	 * ��� - ���������� ����� �������� � ��������������� ������ (������� � 0)
	 * @param phoneNumbers ������ ���������
	 * @param phoneToDecode �������, ������� ����� �������������
	 * @param unencoded ����������� �� ������� ���������������� ���������
	 * @return ������� � ��������� ������� ������ ���������
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
		//���� ������� � ���������������� ���� � ����������� �� ������� - ��������
		if (unencoded && PhoneNumberFormat.check(phoneToDecode))
		{
			return PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phoneToDecode);
		}
		//����� - �������������
		else
		{
			int code = 0;
			try
			{
				code = Integer.parseInt(phoneToDecode);
			}
			catch (NumberFormatException e)
			{
				throw new IllegalArgumentException("�������� ��� ��������", e);
			}
			if (code > sortedPhoneNumbers.length)
				throw new IllegalArgumentException("�������� ��� ��������");

			return sortedPhoneNumbers[code];
		}
	}
}
