package com.rssl.phizic.gate.payments;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * ������� ����� ������� �������.
 *
 * @author Krenev
 * @ created 22.06.2007
 * @ $Author$
 * @ $Revision$
 */
public interface ClientAccountsTransfer extends AbstractAccountTransfer
{
   /**
    * ���� ����������.
    * ������ ����� ������ ��������� � ������� getAmount � ������� getPayerAccount
    * ���� ������ ��������� � ��� �� ����� � ������������ �������.
    * ����� ���������� null � ������ ����� �������� ����� 0.
    * 
    * Domain: AccountNumber
    * @return String
    */
   String getReceiverAccount();

	/**
	 * @return ������ ����� ����������
	 */
	Currency getDestinationCurrency() throws GateException;

}
