package com.rssl.phizic.auth.modes;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 14.01.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� ���������
 * ���������� �������� ������ ������������ � �������
 * �������� �� ������: "��� ���� ������ ������ � �������?"
 */
public enum UserVisitingMode implements Serializable
{
	/**
	 * �������� �����
	 */
	BASIC,

	/**
	 * ������������ ����� � ������� ��� ������ �������� ��������� (���/OZON)
	 */
	PAYORDER_PAYMENT,

	/**
	 * ������ ������ � �������������� ������ (Einvoicing)
	 */
	MC_PAYORDER_PAYMENT,

	/**                                      -
	 * ���� ����������� ��� � �������� �������
	 * �� ���������, ��������������� ��������
	 */
	EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT,

	/**
	 * ���� ����������� ��� � �������� �������
	 * �� ���������� �����
	 */
	EMPLOYEE_INPUT_BY_CARD,

	/**
	 * ���� ����������� ��� � �������� �������
	 * �� ������ ��������
	 */
	EMPLOYEE_INPUT_BY_PHONE,

	/**
	 * ���� ����������� ��� � �������� ������� (��� ���������� ���)
	 * �� ���������, ��������������� ��������
	 */
	EMPLOYEE_INPUT_FOR_PFP,

	/**
	 * �������� ����
	 */
	GUEST;

	/**
	 * @param userVisitingMode ��� �����
	 * @return true - ������ � ��� ��� ������ �������
	 */
	public static boolean isEmployeeInputMode(UserVisitingMode userVisitingMode)
	{
		return UserVisitingMode.EMPLOYEE_INPUT_BY_CARD == userVisitingMode
				|| UserVisitingMode.EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT == userVisitingMode
				|| UserVisitingMode.EMPLOYEE_INPUT_BY_PHONE == userVisitingMode
				|| UserVisitingMode.EMPLOYEE_INPUT_FOR_PFP == userVisitingMode;
	}
}
