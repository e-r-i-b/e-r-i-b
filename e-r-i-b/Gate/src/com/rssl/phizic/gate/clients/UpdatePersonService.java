package com.rssl.phizic.gate.clients;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.common.types.Money;

import java.util.Date;

/**
 * @author Omeliyanchuk
 * @ created 07.06.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� ������� � ������� ��� ��������� �� ������� �������.
 */
public interface UpdatePersonService extends Service
{
	/**
	 * �������� ��������� �������
	 * @param clientId ������� ������������� �������
	 * @param newState ����� ������ �������
	 */
	void updateState(String clientId, ClientState newState) throws GateException, GateLogicException;

	/**
	 * �������� ��������� �������
	 * @param callback ������-�����������, ��� ����������� ������������
	 * @param newState ����� ������ �������
	 */
	void updateState(CancelationCallBack callback, ClientState newState) throws GateException, GateLogicException;


	/**
	 * ������������� �������
	 * @param clientId - ������� ������������� �������
	 * @param lockDate - ����, ������� � ������� ������� ��������� ������ � �������
	 * @param islock - ������� ����������. ���� true - ���������, false - ������������
	 * @param liability - ����� ������������� (��� ����������)
	 */
	void lockOrUnlock(String clientId, Date lockDate, Boolean islock, Money liability) throws GateException, GateLogicException;
}
