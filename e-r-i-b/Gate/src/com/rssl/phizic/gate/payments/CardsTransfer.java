package com.rssl.phizic.gate.payments;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.documents.AbstractCardTransfer;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 19.04.2010
 * @ $Author$
 * @ $Revision$
 * ������� � ����� �� �����.
 * �� ������� ������ ������������ ���� �� ��������� � ��� ������������� ��������.
 */
public interface CardsTransfer extends AbstractCardTransfer
{
	/**
	 * ����� ����������.
	 * ����� ����� ������������ ������� �������.
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

	/**
	 * ��� ���������� ��������
	 * @return ��� ����� ����������
	 */
	ReceiverCardType getReceiverCardType();
}
