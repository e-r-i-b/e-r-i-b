package com.rssl.phizic.utils.pattern;

import java.util.regex.Pattern;

/**
 * @author komarov
 * @ created 16.04.2013 
 * @ $Author$
 * @ $Revision$
 */

public class PatternUtils
{
	/**
	 * ����� �� wildCard ������, �� ��� � ����������� ����� �������, ��������� ���������� ���������
	 * ������ ������� �� ��������� ����� ����������� �������.
	 * ������ �?� ����� ������������� ������ �������
	 * ������ �*� - ������ ���������� ����� ��������.
	 * @param codes ������ �� �����
	 * @return ���������� ���������.
	 */
	public static Pattern compileDepartmentsPatten(String codes)
	{
		StringBuffer regexp = new StringBuffer();
		regexp.append("^("); // ������ ������ � ������ ������ ������ ����������
		for (int i = 0; i < codes.length(); i++)
		{
			char c = codes.charAt(i);
			switch (c)
			{
				//���������� ���� �������
				case '*':
					regexp.append(".*");
					break;
				case '?':
					regexp.append(".");
					break;
				case ',':
					regexp.append(")|("); // ��������� ���������� ����� ��������� �������� ���������� � ��������� �� ������
					break;
				//�������� ��������� ���� �������
				case '(':
				case ')':
				case '[':
				case ']':
				case '$':
				case '^':
				case '.':
				case '{':
				case '}':
				case '|':
				case '\\':
					regexp.append("\\");
				default:
					regexp.append(c);
			}
		}
		regexp.append(")$");//����� ��������� ����� � ���� ������
		return Pattern.compile(regexp.toString());
	}
}
