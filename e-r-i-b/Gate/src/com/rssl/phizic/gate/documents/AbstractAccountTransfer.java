/***********************************************************************
 * Module:  AbstractTransfer.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface AbstractTransfer
 ***********************************************************************/

package com.rssl.phizic.gate.documents;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * ����������� ������� �� ����� ������� ���� ����
 *
 * �� ��������� ���� � �������� ����������� ���������� �� ������� ��������
 * ������ ������ �������� �������� �� ����� �������� (getChargeOffAccount)
 */
public interface AbstractAccountTransfer extends AbstractTransfer
{
   /**
    * ���� ��������. ���� � �������� ����������� �������� ��� ���������� �������(��� ������ ��������)
    * Domain: AccountNumber
    *
    * @return ���� ��������
    */
   String getChargeOffAccount();

	/**
	 * @return ������� ����� ��������
	 */
    Currency getChargeOffCurrency() throws GateException;
}
