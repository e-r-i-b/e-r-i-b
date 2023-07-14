package com.rssl.phizic.payment;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.annotation.MandatoryParameter;

/**
 * @author Erkin
 * @ created 20.04.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ������ "������� �� ������ �����"
 */
public interface CardTransferTask extends PaymentTask
{
	/**
	 * ������ �������� ��������
	 * @param senderProduct - ���� �����/����� �������� (never null)
	 */
	@MandatoryParameter
	void setSenderProduct(BankrollProductLink senderProduct);

	/**
	 * ������ ������� ����������
	 * @param receiverCardNumber - ����� ����� ���������� (never null nor empty)
	 */
	@MandatoryParameter
	void setReceiverCardNumber(String receiverCardNumber);

	/**
	 * ������ ����� ��������.
	 * @param amount - ����� �������� � ������ (never null)
	 */
	@MandatoryParameter
	void setAmount(Money amount);
}
