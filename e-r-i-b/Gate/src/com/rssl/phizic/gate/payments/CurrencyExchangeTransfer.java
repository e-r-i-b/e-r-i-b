package com.rssl.phizic.gate.payments;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;

/**
 * �������\�������\����������� ������
 *
 * ��� �������� ������ ���� ����������:
 *
 *  ����� ������� getChargeOffAmount. ��� ���� �� ������ ������ ��������� � ������� getChargeOffAccount.
 *  ����� ������� getDestinationAmount. ��� ���� �� ������ ������ ��������� � ������� getDestinationAccount.
 *
 *
 * @author Krenev
 * @ created 26.06.2007
 * @ $Author$
 * @ $Revision$
 */
@Deprecated
public interface CurrencyExchangeTransfer extends AbstractAccountTransfer
{
    /**
    * ���� ����������
    * Domain: AccountNumber
    *
    * @return ���� ��������
    */
    String getDestinationAccount();

    /**
    * ����� �������. (��� ��������)
    *
    * @return ����� �������.
    */
    Money getDestinationAmount();

	/**
	 * ���������� ����� ����������(� ��� ������, ���� ��� ���������� � �������� ���������� �������)
	 * @param amount ����� ����������� �� ����
	 */
	void setDestinationAmount(Money amount);

	/**
	 * ���������� ����� ��������(� ��� ������, ���� ��� ���������� � �������� ���������� �������)
	 * @param amount ����� ��������� �� �����
	 */
    void setChargeOffAmount(Money amount);
}