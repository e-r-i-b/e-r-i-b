package com.rssl.phizic.business.dictionaries.pfp.personData;

/**
 * @author lepihina
 * @ created 24.04.2012
 * @ $Author$
 * @ $Revision$
 */

/** ���������� ��������. �������� � ���� ��� �������� "����� 3-�" */
public enum LoanCount
{
	NONE(0),
	ONE(1),
	TWO(2),
	THREE(3),
	moreThanTHREE(900); // "����������" �����, ���������� "������ 3-�"

	private Integer value;

	LoanCount(Integer value)
	{
		this.value = value;
	}

	public int toValue()
	{
		return value;
	}

	public static LoanCount fromValue(int value)
	{
		if (value < 0)
			throw new IllegalArgumentException("������������ ���������� ��������: " + value);

		switch (value)
		{
			case 0:
				return NONE;
			case 1:
				return ONE;
			case 2:
				return TWO;
			case 3:
				return THREE;
			default:
				return moreThanTHREE;
		}
	}
}
