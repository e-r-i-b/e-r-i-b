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
	 * Получение случайной строки длиной n символов.
	 * @param n длинна
	 * @return случайная строка
	 */
	public static String rand(int n)
	{
		SecureRandom rand = new SecureRandom();
		byte[] value = new byte[n];
		rand.nextBytes(value);
		return StringUtils.toHexString(value);
	}

	/**
	 * Получение случайной строки длиной n символов.
	 * Для составления строки используются символы из параметра chars
	 * @param n длинна
	 * @param chars определяет из каких символов состоит результат
	 * @return случайная строка
	 */
	public static String rand(int n, String chars)
	{
		Random rand = new Random();
		StringBuffer buffer = new StringBuffer(n);
		int lenght = chars.length();

		if (lenght == 0)
			throw new RuntimeException("Количество символов в переданной строке должно быть больше 0");

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
