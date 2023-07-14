package com.rssl.phizic.utils;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Evgrafov
 * @ created 14.02.2007
 * @ $Author: barinov $
 * @ $Revision: 39907 $
 */

@SuppressWarnings({"JavaDoc"})
public class StringUtilsTest extends TestCase
{
	private static final String HEX = "0F037584C99E7FD4F4F8C59550F8F507";

	@SuppressWarnings({"MagicNumber"})
	public void testHex()
	{
		byte[] dataBytes = StringUtils.fromHexString(HEX);
		assertEquals(HEX, StringUtils.toHexString(dataBytes));

		byte[] dataBytes1 = StringUtils.fromHexArray(HEX.toCharArray());
		char[] hex1 = StringUtils.toHexArray(dataBytes1);
		assertEquals(HEX, String.valueOf(hex1));
	}

	public void testSplitToLines()
	{
		final int lineWidth = 140;
		System.out.println(" *** �������� ������ �� ������ �� " + lineWidth);

		String text = "��������� ���� ������� � 1802 ���� � ����� �������� ����-���������� ����  " +
				"� �����-����� ������, ����������� � ��������� ������ �����-�����. " +
				"��� �������, ���������� � ������ ���� ����� � ������ ������, " +
				"� ����� ���������� � �����. �������� �� ��������, ����� �� �� " +
				"����� ������� ��������� � ����������������� �����, ������� ������� " +
				"��������������� ���������� �������� ��������� � ����-����� (�����) � " +
				"���������� ��� ������� ����������." +
				"� ���� 1830 ���� �� ������� ��������� �������� ���������, ����������� " +
				"����� X � ����������� ���������� �����������. �� ������� ������� ������ " +
				"���������� ��� ������ ���-�������. ��������� ���� ��� ����� �����������, " +
				"������������ ����������� ������ �������. ������������ � ����� ���������� " +
				"�� �����:" +
				"\n" +
				"� ����� ���, ������� ��������� ��������� 1830 ����, " +
				"� ��� ������ ���� � ����� ������ " +
				"����, ����������� ��������� 1830 ����, " +
				"������������ ����� ������ ������ ������������ ������������; " +
				"��� �� ������ ��������� �����, �� � ������ ����� ����� ������.";

		Pattern spacePattern = Pattern.compile("\\s");

		int i = 0;
		String[] lines = StringHelper.splitToLines(text, lineWidth, true);
		for (String line : lines) {
			System.out.println(String.format("[%02d] %s", i++ + 1, line));
			Matcher spaceMatcher = spacePattern.matcher(line);
			Assert.assertTrue(line.length()<lineWidth || !spaceMatcher.find());
		}
	}
}