package com.rssl.phizic.business.loanclaim.questionnaire;

/**
 * @author Gulov
 * @ created 25.12.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ������ �� ������
 */
public enum Status
{
	/**
	 * �����������
	 */
	PUBLISHED(0),

	/**
	 * �� �����������
	 */
	UNPUBLISHED(1);

	private final int value;

	private Status(int value)
	{
		this.value = value;
	}

	public int toValue()
	{
		return value;
	}

	public static Status fromValue(int value)
	{
		switch (value)
		{
			case 0:
				return PUBLISHED;
			case 1:
				return UNPUBLISHED;
			default:
				throw new IllegalArgumentException("����������� ��� ������� ������� [" + value + "]");
		}
	}

	@Override
	public String toString()
	{
		return value == 0 ? "�����������" : "�� �����������";
	}
}
