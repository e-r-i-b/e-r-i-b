package com.rssl.phizic.gate.deposit;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.List;

/**
 * ��������� ������ �� ���������
 */
public interface DepositService extends Service
{
	/**
	 * ��������� ������ ��������� �������.
	 *
	 * @param client ������
	 * @return ������
	 * @exception GateException
	 */
	List<? extends Deposit> getClientDeposits(Client client) throws GateException, GateLogicException;

	/**
	 * ��������� �������� �� ��� �������� ID
	 *
	 * @param depositId (Domain: ExternalID)
	 * @return �������
    *  @exception GateException
	 */
	Deposit getDepositById(String depositId) throws GateException, GateLogicException;

	/**
	 * ��������� ����������� ������ �� ��������
	 *
	 * @param deposit ������� ��� �������� ���� �������� ��� ������
	 * @return ����������� ����������
	 * @exception GateException
	 */
	DepositInfo getDepositInfo(Deposit deposit) throws GateException, GateLogicException;
}
