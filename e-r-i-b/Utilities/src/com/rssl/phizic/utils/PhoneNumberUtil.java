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
	 * ������������ ������ ��������
	 * ������������ ��� ����������� ������� ������� � � ��������� �����
	 * @param fullPhoneNumber ������ � ������� �������� � ����������� �������
	 * @return ������ � ������� �������� � �������� �������
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
	 * ������������ ������ ��������
	 * ������������ � ����� �� ����������� � ��������� ����������
	 * @param fullPhoneNumber ������ � ������� �������� � ����������� �������
	 * @return ������ � ������� �������� � ������� SIMPLE_NUMBER
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
	 * ����������� ����� �������� � ����������� ������� � ������� IKFL
	 * @param phoneNumber ����� �������� � ����������� �������
	 * @return ����� �������� � ������� IKFL
	 */
	public static String getFullIKFLPhoneNumber(String phoneNumber)
	{
		return getFormattedPhoneNumber(phoneNumber, PhoneNumberFormat.IKFL);
	}

	/**
	 * ����������� ����� �������� � ����������� ������� � ������� SIMPLE_NUMBER (10 ����)
	 * @param phoneNumber ����� �������� � ����������� �������
	 * @return ����� �������� � ������� SIMPLE_NUMBER
	 */
	public static String getFullSimplePhoneNumber(String phoneNumber)
	{
		return getFormattedPhoneNumber(phoneNumber, PhoneNumberFormat.SIMPLE_NUMBER);
	}

	/**
	 * +7(###)###-##-##
	 * ����������� ����� �������� � ����������� ������� � ������� ADMIN_NUMBER
	 * @param phoneNumber ����� �������� � ����������� �������
	 * @return ����� �������� � ������� ADMIN_NUMBER
	 */
	public static String getAdminPhoneNumber(String phoneNumber)
	{
		return getFormattedPhoneNumber(phoneNumber, PhoneNumberFormat.ADMIN_NUMBER);
	}

	/**
	 * 7########## (7+10 ����)
	 * ����������� ����� �������� � ����������� ������� � ������� MOBILE_INTERANTIONAL
	 * @param phoneNumber ����� �������� � ����������� �������
	 * @return ����� �������� � ������� MOBILE_INTERANTIONAL
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
	 * ������������ ������� ���������
	 * ������������ ��� ����������� ������� ������� � � ��������� �����
	 * @param fullPhoneNumberList ������ ����� � �������� ��������� � ����������� �������
	 * @return ������ � ������� �������� � �������� �������
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
	 * ������� ��� ������ ������� (�� �����) � �������� ��������� 10 ���� ������
	 * @param phoneNumber �������� �����
	 * @return ��������������� �����
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
	 * �������������� � ������������ ������� ���������
	 * @param phones - ������ �������
	 * @param format - ������ ������ ��������
	 * @return - ������ ������� ���������
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
