package com.rssl.phizic.payment;

/**
 * ��������� ������ "����������� ��������� ���������� ������� �� ���������"
 * @author Puzikov
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 */

public interface LoyaltyRegistrationPaymentTask extends PaymentTask
{
	/**
	 * ������ ����� ��������
	 * @param phoneNumber ������� ����� ��������
	 */
	public void setPhone(String phoneNumber);
}
