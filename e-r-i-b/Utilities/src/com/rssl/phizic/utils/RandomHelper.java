package com.rssl.phizic.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Krenev
 * @ created 17.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class RandomHelper 
{
	public static final String DIGITS = "0123456789";
	public static final String UPPER_ENGLISH_LETTERS = "ABCDEFJHIGKLMNOPQRSTUVWXYZ";
	public static final String LOWER_ENGLISH_LETTERS = "abcdefjhigklmnopqrstuvwxyz";
	public static final String ENGLISH_LETTERS = "abcdefjhigklmnopqrstuvwxyzABCDEFJHIGKLMNOPQRSTUVWXYZ";

	/**
	 * ��������� ��������� ������ ������ n ��������.
	 * @param n ������
	 * @return ��������� ������
	 */
	public static String rand(int n)
	{
		SecureRandom rand = new SecureRandom();
		byte[] value = new byte[n];
		rand.nextBytes(value);
		return StringUtils.toHexString(value);
	}

	/**
	 * ��������� ��������� ������ ������ n ��������.
	 * ��� ����������� ������ ������������ ������� �� ��������� chars
	 * @param n ������
	 * @param chars ���������� �� ����� �������� ������� ���������
	 * @return ��������� ������
	 */
	public static String rand(int n, String chars)
	{
		Random rand = new Random();
		StringBuffer buffer = new StringBuffer(n);
		int lenght = chars.length();

		if (lenght == 0)
			throw new RuntimeException("���������� �������� � ���������� ������ ������ ���� ������ 0");

		char[] charsArray = new char[lenght];
		chars.getChars(0, lenght, charsArray, 0);

		for (int i = 0; i < n; i++)
		{
			int index = rand.nextInt(lenght);
			buffer.append(charsArray[index]);
		}
		return buffer.toString();
	}
}
