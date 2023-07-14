package com.rssl.phizic.gate.payments.systems.recipients;

import com.rssl.phizic.common.types.Money;

/**
 * @author krenev
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 * ��������� ���������� �� �������������
 */
public interface DebtRow
{
	/**
	 * �������� ��� ��������� ���������� �� �������������
	 * @return ��� ��������� ���������� �� �������������
	 */
	String getCode();

	/**
	 * �������� �������� ���������� �� �������������
	 * @return �������� ���������� �� �������������
	 */
	String getDescription();

	/**
	 * �������� ����� �����.
	 *
	 * @return �������� ����� �����
	 */
	Money getDebt();

	/**
	 * ����� ������
	 *
	 * @return ����� ������ ��� Money(0), ���� ���
	 */
	Money getFine();

	/**
	 * �������� �������� �� ������ �����
	 * @return ����� �������� ��� Money(0), ���� ���
	 */
	Money getCommission();
}
