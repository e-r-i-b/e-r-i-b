package com.rssl.phizic.common.types;

/**
 * @author osminin
 * @ created 30.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������������ ������-������� ����������
 */
public enum RequisiteType
{
	IS_LIST_OF_CHARGES("IsListOfCharges"),        // �������� �������� ������� ����������
	IS_PERIOD("IsPeriod"),                        // �������� �������� �������� �������
	IS_RISK_REQUISITE("IsRiskRequisite");         // �������� ������� �� ������������ �������������� ��������
	private String description;

	RequisiteType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public static RequisiteType fromValue(String value)
	{
		if (IS_LIST_OF_CHARGES.description.equals(value))
			return IS_LIST_OF_CHARGES;
		if (IS_PERIOD.description.equals(value))
			return IS_PERIOD;
		if (IS_RISK_REQUISITE.description.equals(value))
			return IS_RISK_REQUISITE;

		throw new IllegalArgumentException("����������� ��� ���������: " + value);
	}
	
	public static boolean validate(String value)
	{
		if (IS_LIST_OF_CHARGES.description.equals(value))
			return true;
		if (IS_PERIOD.description.equals(value))
			return true;
		if (IS_RISK_REQUISITE.description.equals(value))
			return true;
		return false;
	}
}
