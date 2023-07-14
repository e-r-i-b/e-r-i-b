package com.rssl.phizic.utils.counter;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author osminin
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��� ������������ ������������ �� ��������� �������
 *
 * PREFIX
 * PREFIX(1)
 * PREFIX(2)
 * ...
 *
 */
public class DefaultNamingStrategy implements NamingStrategy
{
	public String transform(String name)
	{
		return name;
	}

	/**
	 * ���������� ��������� ����� ����: (�����) ��� ������ ������, ���� ��� ��� �� �������������
	 * @param names ������ ��������, �� ������� ������������ �������
	 * @param standardName ������������, ��� �������� �������� �����
	 * @return �����
	 */
	public String unify(Set<String> names, String standardName)
	{
		BigDecimal counter = new BigDecimal(-1);
		String number;      //�������� (������� � �������) �� ����� �� ������.
		for (String name : names)
		{
			if (name.length() == standardName.length())         //��� ������, ��� ������ ���������,
			{                                                   //�.� ���������� ������ ������, ���������� standardName
				if (counter.compareTo(BigDecimal.ZERO) < 0)
					counter = BigDecimal.ZERO;

				continue;
			}
			name = name.substring(standardName.length() + 1);   //������ ����� ��� ��� �� ����� (������ � ����� �������).
			int rBkt = name.indexOf(")");                       //������� ������ ������.
			if (rBkt < 0)
			{
				continue;
			}
			number = name.substring(0, rBkt);                   //����������� �������� ����� ������,
			if (!number.matches("\\d+"))                        //� ������� ������ ���� ������ �����.
			{
				continue;
			}
			BigDecimal iNum = new BigDecimal(number);          //�������� �������� (������� � �������) �� �� ������.
			if (iNum.compareTo(counter) > 0)
			{
				counter = iNum;
			}
		}

		number = (counter.compareTo(BigDecimal.ZERO) < 0) ? "" : "(" + counter.add(BigDecimal.ONE).toString() + ")";
		return number;
	}
}
