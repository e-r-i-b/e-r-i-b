package com.rssl.phizic.business.limits;

/**
 * @author basharin
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 */

public enum RestrictionType
{
	DESCENDING("�� �������� ������"),                                               //  �� �������� ������ (��� ���������� ��������)
	AMOUNT_IN_DAY("�� ����� �������� � �����"),						                //	�� ����� �������� � �����
	CARD_ALL_AMOUNT_IN_DAY("�� ����� �������� � ����� ���� ����������� ������� �� ������ �����"),   //	�� ����� �������� � ����� ���� ����������� ������� �� ������ �����
	PHONE_ALL_AMOUNT_IN_DAY("�� ����� �������� � ����� ���� ����������� ������� �� ������ ��������"),  //	�� ����� �������� � ����� ���� ����������� ������� �� ������ ��������
	MIN_AMOUNT("�� ����������� ����� ��������"),					                //	�� ����������� ����� ��������
	OPERATION_COUNT_IN_DAY("�� ���������� �������� � �����"),		                //	�� ���������� �������� � �����
	OPERATION_COUNT_IN_HOUR("�� ���������� �������� � ���"),                        //	�� ���������� �������� � ���
	IMSI("����� SIM-�����"),                                                        //  ����� sim-�����
	MAX_AMOUNT_BY_TEMPLATE("��������� ����� ������ �� �������"),                    //  ��������� ����� ������ �� �������
	OVERALL_AMOUNT_PER_DAY("��������� ����� ������� ��������� ������������� ������");

	private String description;

	RestrictionType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	/**
	 * ����������� �� ���������� ��������
	 * @param limit �����
	 * @return true - ��
	 */
	public static boolean isByCount(Limit limit)
	{
		RestrictionType restrictionType = limit.getRestrictionType();
		return OPERATION_COUNT_IN_DAY == restrictionType || OPERATION_COUNT_IN_HOUR == restrictionType;
	}

	/**
	 * ����������� �� �����
	 * @param limit �����
	 * @return true - ��
	 */
	public static boolean isByAmount(Limit limit)
	{
		RestrictionType restrictionType = limit.getRestrictionType();
		return AMOUNT_IN_DAY == restrictionType;
	}
}
