package com.rssl.phizic.common.types.basket;

/**
 * @author osminin
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 *
 * ��� ������� �����
 */
public enum AccountingEntityType
{
	HOUSE("��� ���"),                    //���
	FLAT("��� ��������"),                //��������
	GARAGE("��� �����"),                 //�����
	CAR("��� ������");                   //����������

	private String defaultName;

	AccountingEntityType(String defaultName)
	{
		this.defaultName = defaultName;
	}

	/**
	 * @return ������������ ������� ����� �� ���������
	 */
	public String getDefaultName()
	{
		return defaultName;
	}

	/**
	 * @return ���������� ��� ���������. ����� ��� ��������� � jsp
	 */
	public String getCode()
	{
		return this.name();
	}

	public static AccountingEntityType toValue(String name)
	{
		if (name == null)
		{
			return null;
		}

		for (AccountingEntityType type : AccountingEntityType.values())
		{
			if (type.name().equals(name))
			{
				return type;
			}
		}

		return null;
	}
}
