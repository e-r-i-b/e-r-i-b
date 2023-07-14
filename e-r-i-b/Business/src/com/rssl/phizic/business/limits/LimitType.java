package com.rssl.phizic.business.limits;

/**
 * @author basharin
 * @ created 03.08.2012
 * @ $Author$
 * @ $Revision$
 * ��� ������
 */

public enum LimitType
{
	GROUP_RISK,                         //����� �� ������� �����
	OBSTRUCTION_FOR_AMOUNT_OPERATION,   //�������������� ����� �� ����� ��������
	OBSTRUCTION_FOR_AMOUNT_OPERATIONS,  //�������������� ����� �� ����� ��������
	IMSI,                               //IMSI �����
	USER_POUCH,                         //���������������� �������
	EXTERNAL_CARD,                      //����� �� ���������� ��� ��������� �� ����� �����
	EXTERNAL_PHONE,                     //����� �� ���������� ��� ������ ������ ��������
	OVERALL_AMOUNT_PER_DAY;             //�������� ����� ��� ���� �������

	/**
	 * �������� �� ������ ��� ������ ��������������.
	 * �����������: ����� �� ������ ����� �� ���������������
	 *
	 * @param limitType ��� ������
	 * @return true - ��������������
	 */
	public static boolean isObstruction(LimitType limitType)
	{
		return limitType == OBSTRUCTION_FOR_AMOUNT_OPERATION || limitType == OBSTRUCTION_FOR_AMOUNT_OPERATIONS
			|| limitType == EXTERNAL_CARD || limitType == EXTERNAL_PHONE || limitType == USER_POUCH;
	}
}
