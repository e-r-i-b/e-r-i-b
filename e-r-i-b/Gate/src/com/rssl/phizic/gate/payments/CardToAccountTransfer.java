package com.rssl.phizic.gate.payments;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.documents.AbstractCardTransfer;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author krenev
 * @ created 12.07.2010
 * @ $Author$
 * @ $Revision$
 * ������� � ����� �� ���� ������ ������� ��� ���������
 */
public interface CardToAccountTransfer extends AbstractCardTransfer
{
	/**
	 * ���� ����������.
	 * ������ ����� ������ ��������� � ������� getAmount � ������� getPayerAccount
	 * ���� ������ ��������� � ��� �� ����� � ������������ �������.
	 */
	String getReceiverAccount();

	/**
	 * @return ������ ����� ����������
	 */
	Currency getDestinationCurrency() throws GateException;
}
