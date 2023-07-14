package com.rssl.phizic.gate.payments;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * ����������� ������� �� ��������� ����������
 *
 * @author Krenev
 * @ created 26.06.2007
 * @ $Author$
 * @ $Revision$
 */
public interface AbstractRUSPayment extends AbstractTransfer
{
	/**
	 * @return ��� ���������� �������- ����� �������������, � ���� ������ == null
	 */
	String getReceiverINN();

    /**
     * ���� ����������.
     * @return ����� �����
     */
	String getReceiverAccount();

	/**
	 * @return ������ ����� ����������
	 */
	Currency getDestinationCurrency() throws GateException;

	/**
	 * ����� ������ � ������� ����� �������������,
	 * ������������ ��� ��������� �������� ���
	 *
	 * @return ���� ����������
	 */
	ResidentBank getReceiverBank();

}