package com.rssl.phizic.utils.counter;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Puzikov
 * @ created 19.05.15
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �������� � ��� ������
 * PREFIX_1
 * PREFIX_2
 * PREFIX_3
 * ...
 *
 */

public class SmsChannelTemplateNameStrategy implements NamingStrategy
{
	private static final String REPLACEMENT = "_";

	public String transform(String name)
	{
		if (StringUtils.isEmpty(name))
			return name;

		//������� ������������ � ��� ������ �������
		String s = name.replaceAll("[^a-zA-Z�-��-߸�_0-9]", REPLACEMENT);
		//������ ������ �� ������ ���� ������
		if (Character.isDigit(s.charAt(0)))
		{
			s = REPLACEMENT + s.substring(1);
		}
		//��������� ��� ��� ����� �������� �� ���������� c �������� ��� ������ � �����������.
		//�� ������ ���� *_� ����� ���, � ���������� � ����� ������ ����� ������������, ������� ��������� ���.
		return s;
	}

	public String unify(Set<String> names, String standardName)
	{
		BigDecimal counter = getExistingCounter(names, standardName);
		return "_" + counter.add(BigDecimal.ONE).toString();
	}

	private BigDecimal getExistingCounter(Set<String> names, String standardName)
	{
		BigDecimal result = BigDecimal.ZERO;
		for (String name : names)
		{
			if (name.length() == standardName.length())         //��� ������, ��� ������ ���������,
			{                                                   //�.� ���������� ������ ������, ���������� standardName
				continue;
			}
			name = name.substring(standardName.length() + 1);   //������ ����� ��� ��� �� ����� (������ � ����� �������).
			if (!name.matches("\\d+"))                          //� ������� ������ ���� ������ �����.
			{
				continue;
			}
			BigDecimal iNum = new BigDecimal(name);             //�������� �������� (������� � �������) �� �� ������.
			if (iNum.compareTo(result) > 0)
			{
				result = iNum;
			}
		}
		return result;
	}
}
