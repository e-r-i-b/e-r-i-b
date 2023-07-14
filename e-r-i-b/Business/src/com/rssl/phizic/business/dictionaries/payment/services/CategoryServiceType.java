package com.rssl.phizic.business.dictionaries.payment.services;

/**
 * @author lukina
 * @ created 17.03.2011
 * @ $Author$
 * @ $Revision$
 */

public enum CategoryServiceType
{
	//�����, �������� � �����������
	COMMUNICATION("�����, �������� � �����������"),
	//�������� � ����� �����
	TRANSFER("�������� � ����� �����"),
	//�������� �� ������� � ��������
	DEPOSITS_AND_LOANS("�������� �� �������, ������, �������� � ���"),
	//������ � ������, �����
	TAX_PAYMENT("������, ������, �����"),
	//�����������
	EDUCATION("�����������"),
	//���������� �����
	PFR("���������� �����"),
	//������ ������� � �����
	SERVICE_PAYMENT("������ ������� � �����"),
	//������
	OTHER("������...");

	private String value;

	CategoryServiceType(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

	public String getValue()
	{
		return value;
	}

	public String getName()
	{
		return name();
	}

    public static CategoryServiceType fromValue(String value)
    {
        for(CategoryServiceType category : values())
            if(category.name().equals(value))
                return category;

        throw new IllegalArgumentException("����������� ��������� �������� [" + value + "]");
    }
}
