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
	 * ������� ������������ �������� � ������������ � �������� ������.
	 * ���������� �������� � ����� ���������� '#'.
	 * ������������(����������� �������) � ����� �������� �������� '*'.
	 * ������ ������� ����� �������� ��� ����. �������� ����� #(###)***#### ��������� �� �������� 79115869241 ���� 7(911)***9241.
	 * @param mask - �����
	 * @param value - ��������
	 * @return ������������ ��������.
	 */
	public static String getMaskedValue(String mask, String value)
	{
		//���� ����� ��� ���� �� ������, ��� �������� ������ ���� ��� ����������� - ������ �� ������.
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
	 * �������� �� �� ��� ���������� ���������� � ������������ ��������
	 * ��������� � ������ ����������� ������.
	 * @param mask - �����
	 * @param value - ����������� ��������
	 * @return true - ���������� ���������.
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
	 * �������� ��������� ����� ����� �� ������� ��������(� ������� �� *). ������������ ��� �����, sms, push, email ���������.
	 * @param fullCardNumber ������ ����� �����
	 * @return ��������� � ����������� �� �������� ����� �����, ����� ���������� �������.
	 */
	public static String getCutCardNumberForLog(String fullCardNumber)
	{
		return getCutCardNumberBase(fullCardNumber, MaskHelper.getOldCardNumberMask());
	}

	/**
	 * �������� ���������� ������� ���������� ������ �����
	 * @return �������
	 */
	private static Pattern getActualCardNumberMaskPattern()
	{
		CardsConfig cardsConfig = ConfigFactory.getConfig(CardsConfig.class);
		if (ApplicationUtil.isMobileApi()||ApplicationUtil.isATMApi())
			return cardsConfig.getCardNumberAtmRegexp();
		return cardsConfig.getCardNumberRegexp();
	}

	/**
	 * �������� ��������� ����� ����� � ����������� �� �������� ����������.
	 * @param fullCardNumber ������ ����� �����
	 * @return ��������� � ����������� �� �������� ����� �����, ����� ���������� �������.
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
			log.error("������ ������������ ������ �����", e);
			return "";
		}
	}

	/**
	 * ����������� ������ ��������� ����
	 * @param fullCardNumbers - �������� ������� ���� (never null can be empty)
	 * @return �������� ������������� ������� ���� (never null)
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
			log.error("������ ������������ ������ �����", e);
			return "";
		}
	}

	/**
	 * ��������� ������ ����� ��� ������ �� ����
	 * @param fullCardNumber ������ ����� �����
	 * @return ����� �����, ��������� � ����������� �� �������� ������ �� ����
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
			log.error("������ ������������ ������ �����", e);
			return "";
		}
	}

	/**
	 * ��������� �� ���������� ������ ��������� 7 ��������
	 * @param forCut -  ������ ��� ��������������
	 * @return - ��������� ��������� 7 �������� �� ���������� ������
	 * ������ ������ ������, ���� ���������� ���� �������� ������ ������
	 * ������ ������������ ������, ���� �� ����� ������ ����������� ���-�� ��������
	 */
	public static String cutStringByLastSevenSymbols(String forCut)
	{
		return cutString(forCut, SEVEN);
	}
	/**
	 * ��������� �� ���������� ������ ��������� n ��������
	 * @param forCut -  ������ ��� ��������������
	 * @param countCut - ���-�� ��������, ������� ����� �������� (� �����)
	 * @return - ��������� ��������� n �������� �� ���������� ������
	 * ������ ������ ������, ���� ���������� ���� �������� ������ ������
	 * ������ ������������ ������, ���� �� ����� ������ ����������� ���-�� ��������
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
	 * ��������� "�������" ������� � ������.
	 *
	 * @param input ������� ������.
	 * @param maskStartPosition ����� �������, � ������� ���������� ������ ������������.
	 * @param maskEndPosition ������ �������, � �������  ���������� ������ ������������.
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
	 * ��������� ����� ��������
	 * @param number - ����� �������� (never null)
	 * @return ������������� ����� �������� � ���� ������
	 */
	public static String maskPhoneNumber(PhoneNumber number)
	{
		return PhoneNumberFormat.MOBILE_INTERANTIONAL.format(number.hideAbonent());
	}

	/**
	 * ��������� ����� ��������
	 * @param number - ����� �������� (never null)
	 * @return ������������� ����� �������� � ���� ������
	 */
	public static String maskPhoneNumber(String number)
	{
		return PhoneNumberFormat.MOBILE_INTERANTIONAL.format(PhoneNumber.fromString(number).hideAbonent());
	}

	/**
	 * ������������ ������ �������� ������.
	 * ���� ������ ������, ��� ���������� �������� ��� ������������, ������ ����. �������, ������ ����� ������.
	 * @param input - ������ ��� ������������
	 * @param maskedCharsNumber - ���������� ��������, ������� ���� �������������
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
	 * ������������ ������������ ������ (�������� ������ 3 �������)
	 * @param input
	 * @return
	 */
	public static String maskAbonent(String input)
	{
		return maskFirstChars(input, 3);
	}

	/**
	 * ��������� ����������� ������ (����� �� ����� 255)
	 * @param input
	 * @return
	 */
	public static String maskAll(String input)
	{
		return maskFirstChars(input, 255);
	}

	/**
	 * ������������ ����� (�������� ������ 4 �������)
	 * @param input
	 * @return
	 */
	public static String maskStreet(String input)
	{
		return maskFirstChars(input, 4);
	}

	/**
	 * ��������� ������������� ���� ������� ��.��.����
	 * @return **.**.***
	 */
	public static String getMaskedDate()
	{
		return "**.**.****";
	}

	/**
	 * ���������� ������������� e-mail � ���� ko****@gmail.com
	 * @param eMail ����� ����������� �����
	 * @return ������������� �����
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
	 * ���������� �.�.�. � ������� ���� �������� �.
	 * @param oif �.�.�.
	 * @return �.�.�.
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
	 * ���������� ��� � ������� ���� �������� �.
	 * @param firstName - ���
	 * @param patrName - ��������
	 * @param lastName - �������
	 * @return ��� � ��������� �������
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
	 * ��������� ��� ����� � ������
	 * @param in �������� ������
	 * @return
	 */
	public static String maskAllNumericChars(String in)
	{
		if(StringHelper.isEmpty(in))
			return "";
		return in.replaceAll("\\d", String.valueOf(MaskHelper.getActualMaskSymbol()));
	}

	/**
	 * ��������� ������ �� ��������� "����� � �����" ������ �����
	 * @param str �������� ������
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
		number = number.replaceFirst(".{"+ String.valueOf(4) + "}", "����");
		return series + " " + number;
	}
}
