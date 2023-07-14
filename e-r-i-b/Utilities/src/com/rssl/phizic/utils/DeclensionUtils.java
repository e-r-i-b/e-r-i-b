package com.rssl.phizic.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @author gulov
 * @ created 24.11.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * ��������� ����
 */
public class DeclensionUtils
{
	/**
	 * ��������� ������������.
	 * ����������� ��� �������� (������ �� ������):
	 *   1 ������� - ������������ ������������� �� �����, ������� ��������� � ��������� �� 10 �� 20.
	 *   2 ������� - ������������ ������������� �� 1.
	 *   3 ������� - ������������ ������������� �� 2, 3, 4.
	 * � ����������� �� �������� ������������� ������ ��������� (endings) � ����� word.
	 * @param number - ������������
	 * @param word - ���������� �����
	 * @param endings1 - ������ ���������
	 * @param endings2 - ������ ���������
	 * @param endings3 - ������ ���������
	 * @return - ���������� �����
	 */
	public static String numeral(long number, String word, String endings1, String endings2, String endings3)
	{
		int variant = variant(number);
		if (variant == 0)
			return word.concat(endings1);
		if (variant == 1)
			return word.concat(endings2);
		return word.concat(endings3);
	}

	private static int variant(long number)
	{
		long twoLast = number % 100;
		if (twoLast >= 10 && 20 >= twoLast)
			return 2;
		String last = StringUtils.right(String.valueOf(number), 1);
		if ("1".equals(last))
			return 0;
		if ("2".equals(last) || "3".equals(last) || "4".equals(last))
			return 1;
		return 2;
	}
}
