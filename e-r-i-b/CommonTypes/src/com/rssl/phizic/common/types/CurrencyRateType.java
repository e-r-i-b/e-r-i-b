package com.rssl.phizic.common.types;

/**
 * ��� ����� ������ (���� ��, ���� �������, ���� �������)
 *
 * @author Kosyakov
 * @ created 18.10.2006
 * @ $Author: rydvanskiy $
 * @ $Revision: 20783 $
 */
public enum CurrencyRateType
{
	// ���� ��
	CB(0),

	// ���� �������
	BUY(1),

	// ���� �������
	SALE(2),

	// ���� ��������� �������
	BUY_REMOTE(3),

	// ���� ��������� �������
	SALE_REMOTE(4);

	private int id;

	CurrencyRateType(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public static CurrencyRateType valueOf(int id)
	{
		switch (id)
		{
			case 0:
				return CB;
			case 1:
				return BUY;
			case 2:
				return SALE;
			case 3:
				return BUY_REMOTE;
			case 4:
				return SALE_REMOTE;
		}
		throw new IllegalArgumentException("����������� ��� ����� ������ [" + id + "]");
	}

	public static CurrencyRateType invert(CurrencyRateType type)
	{
		switch (type)
		{
			case CB:
				return CB;
			case BUY:
				return SALE;
			case SALE:
				return BUY;
			case BUY_REMOTE:
				return SALE_REMOTE;
			case SALE_REMOTE:
				return BUY_REMOTE;
		}
		throw new IllegalArgumentException("����������� ��� ����� ������ [" + type + "]");
	}
}
