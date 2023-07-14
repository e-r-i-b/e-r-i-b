package com.rssl.phizic.payment;

import com.rssl.phizic.bankroll.BankrollProductLink;

/**
 * @author Gulov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ������ "���������� �����"
 */
public interface CardIssueTask extends PaymentTask
{
	/**
	 * ������ �����.
	 * @param cardLink - ����������� ����� (never null)
	 */
	void setCardLink(BankrollProductLink cardLink);

	/**
	 * ����� ����������� �����
	 * @return �����
	 */
	String getBlockingProductAlias();

	/**
	 * ������ ��� ����������.
	 * @param blockCode - ��� ���������� (never null)
	 */
	void setIssueCode(Integer blockCode);

	/**
	 * ���������� ����� ������������ ��������
	 * @param blockingProductAlias ����� ����������� �����
	 */
	void setBlockingProductAlias(String blockingProductAlias);
}
