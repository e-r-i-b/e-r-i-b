/***********************************************************************
 * Module:  BankrollNotificationService.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Interface BankrollNotificationService
 ***********************************************************************/

package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;

public interface BankrollNotificationService extends Service
{
	/**
	 * ��������� ����� �� �����.
	 *
	 * @param card ����� �� ������� ���������� ��������� �����.
	 * @param address ����� ��� �������� ����������.
	 * @param fromDate ��������� ���� (������� ��)
	 * @param toDate �������� ���� (������� ��)
	 * @exception GateException
	 * @exception GateLogicException
	 */
	void sendCardReport(Card card, java.lang.String address, java.util.Calendar fromDate, java.util.Calendar toDate) throws GateException, GateLogicException;

}
