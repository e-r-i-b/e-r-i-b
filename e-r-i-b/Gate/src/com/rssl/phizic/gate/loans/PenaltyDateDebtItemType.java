package com.rssl.phizic.gate.loans;

import com.rssl.phizic.utils.StringHelper;

/**
 * ���� ������ ������������� �� ���� (�������� � ������������ �������)
 * @author gladishev
 * @ created 22.11.2011
 * @ $Author$
 * @ $Revision$
 */
public enum PenaltyDateDebtItemType
{
	/**
	* ����� �������� � ���� ��������
	* �� ��������� �������������
	*/
	otherCostsAmount("����� �������� � ���� �������� �� ��������� �������������"),

	/**
	* ����� �� ��������� �������
	*/
	earlyReturnAmount("����� �� ��������� �������"),

	/**
	* ����� �� �������� �� �������� �����
	*/
	accountOperationsAmount("����� �� �������� �� �������� �����"),

	/**
	* ��������� �� ��������� �����
	* �� �������� �� �������� �����
	*/
	penaltyAccountOperationsAmount("��������� �� ��������� ����� �� �������� �� �������� �����"),

	/**
	* ��������� �� ��������� ���������
	*/
	penaltyDelayPercentAmount("��������� �� ��������� ���������"),

	/**
	* ��������� �� ��������� ��������� �����
	*/
	penaltyDelayDebtAmount("��������� �� ��������� ��������� �����"),

	/**
	* ��������� �� ��������������� �������������
	* ����������� �������� ������
	*/
	penaltyUntimelyInsurance("��������� �� ��������������� ������������� ����������� �������� ������"),

	/**
	* ����� ������������ ���������
	* �� ����������� ��������
	*/
	delayedPercentsAmount("����� ������������ ��������� �� ����������� ��������"),

	/**
	* ����� ������������� ��������� �����
	*/
	delayedDebtAmount("����� ������������� ��������� �����"),

	/**
	* ��������, � ���� ��������� �����
	*/
	earlyBaseDebtAmount("��������, � ���� ��������� �����");


	private final String description;

	PenaltyDateDebtItemType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public static PenaltyDateDebtItemType fromValue(String value)
	{
		if (StringHelper.isEmpty(value))
			return null;

		for (PenaltyDateDebtItemType type : values()) {
			if (value.equals(type.description))
				return type;
		}
		throw new IllegalArgumentException("������������ ����� ������ [" + value + "]");
	}
}
