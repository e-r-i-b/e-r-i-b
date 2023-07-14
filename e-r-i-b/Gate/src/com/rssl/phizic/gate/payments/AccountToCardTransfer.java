package com.rssl.phizic.gate.payments;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 12.07.2010
 * @ $Author$
 * @ $Revision$
 * ������� � ����� �� ���� ������ ������� ��� ���������
 */
public interface AccountToCardTransfer extends AbstractAccountTransfer
{
	/**
	 * ����� ����������.
	 * ����� ������ ������������ ������ �������.
	 *
	 * @return ����� ����������
	 */
	String getReceiverCard();

	/**
	 * @return ������ ����� ����������
	 */
	Currency getDestinationCurrency() throws GateException;

	/**
	 * ���� ��������� ����� �������� ����� ����������
	 *
	 * @return ���� ��������� ����� �������� ����� ����������
	 */
	Calendar getReceiverCardExpireDate();
}

