package com.rssl.phizic.gate.payments;

/**
 * User: Balovtsev
 * Date: 05.10.2011
 * Time: 16:28:38
 */
public interface AccountClosingPayment extends ClientAccountsTransfer
{
	/**
	 * ���������� � ������� ����������� ���������
	 * (null = ��� ����������� ���������) 
	 *
	 * @return String
	 */
	String getLongOffertFormalized();

	void setLongOffertFormalized(String longOffertFormalized);
	
	/**
	 * ���������� � ��������� ����������� �������� �� ������
	 * (null = ��������� ���)
	 *
	 * @return String
	 */
	String getAgreementViolation();

	void setAgreementViolation(String violationText);

	/**
	 * ���������� � ������� ����������� ������������ ������ �� ��������� �����
	 * @return
	 */
	boolean getTariffClosingMsg();

	void setTariffClosingMsg(boolean isTariff);
}
