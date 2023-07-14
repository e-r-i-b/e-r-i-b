package com.rssl.phizic.payment;

import com.rssl.phizic.bankroll.BankrollProductLink;

/**
 * ��������� ������ "���������� �����"
 * @author Rtischeva
 * @ created 14.06.2013
 * @ $Author$
 * @ $Revision$
 */
public interface BlockingCardTask extends PaymentTask
{
	/**
	 * ������ �����.
	 * @param cardLink - ����������� ����� (never null)
	 */
	void setCardLink(BankrollProductLink cardLink);

	/**
	 * ������ ��� ����������.
	 * @param blockCode - ��� ���������� (never null)
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
