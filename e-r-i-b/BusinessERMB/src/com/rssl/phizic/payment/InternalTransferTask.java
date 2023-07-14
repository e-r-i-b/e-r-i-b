package com.rssl.phizic.payment;

import com.rssl.phizic.bankroll.BankrollProductLink;

import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ������ "������� ����� ������ �������"
 */
public interface InternalTransferTask extends PaymentTask
{
	/**
	 * ������ �������� ��������.
	 */
	void setFromResourceCode(String fromResourceCode);

	/**
	 * ��� ������ ��������� ��������
	 * @param fromResourceCurrencyCode
	 */
	void setFromResourceCurrencyCode(String fromResourceCurrencyCode);

	/**
	 * ������ ������� ����������
	 */
	void setToResourceCode(String toResourceCode);

	/**
	 * ��� ������ ��������� ����������
	 * @param toResourceCurrencyCode
	 */
	void setToResourceCurrencyCode(String toResourceCurrencyCode);

	/**
	 * ������ ����� ��������.
	 **/
	void setAmount(BigDecimal amount);

	/**
	 * �������� �� ������� ��������� � ����� �� ����
	 * @param isCardToAccountTransfer true, ���� ��������
	 */
	void setCardToAccountTransfer(boolean isCardToAccountTransfer);

	/**
	 * �������� �� ������� ��������� � ����� �� ����
	 * @return true, ���� ��������
	 */
	boolean isCardToAccountTransfer();

	/**
	 * ��������� �� ���� ����� ����� �������� ���� �������������
	 * @return true, ���� ���������
	 */
	boolean isCurrencyRatesChanged();

	/**
	 * ���������� ���� ������� ��������
	 * @param chargeOffLink - ���� ������� ��������
	 */
	public void setChargeOffLink(BankrollProductLink chargeOffLink);
}
