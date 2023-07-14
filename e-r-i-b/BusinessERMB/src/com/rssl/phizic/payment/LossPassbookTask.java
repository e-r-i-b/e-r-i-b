package com.rssl.phizic.payment;

import com.rssl.phizic.bankroll.BankrollProductLink;

/**
 * @author Gulov
 * @ created 11.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ������ "���������� �����"
 */
public interface LossPassbookTask extends PaymentTask
{
	/**
	 * ������ ����.
	 * @param accountLink - ����������� ���� (never null)
	 */
	void setLink(BankrollProductLink accountLink);

	/**
	 * ������ ��� ����������.
	 * @param blockCode - ��� ���������� (can be null)
	 */
	void setBlockCode(Integer blockCode);

	/**
	 * ������� ����� ������������ ��������
	 * @return
	 */
	String getBlockingProductAlias();

	/**
	 * ���������� ����� ������������ ��������
	 * @param blockingProductAlias
	 */
	void setBlockingProductAlias(String blockingProductAlias);
}
