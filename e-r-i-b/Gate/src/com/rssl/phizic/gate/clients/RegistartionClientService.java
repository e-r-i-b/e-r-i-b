package com.rssl.phizic.gate.clients;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * @author Roshka
 * @ created 08.09.2006
 * @ $Author$
 * @ $Revision$
 */


public interface RegistartionClientService extends Service
{
	/**
	 * ���������������� �������.
	 * @param office
	 * @param registerRequest
	 * @throws GateLogicException
	 * @throws GateException
	 */
	void register(Office office, Document registerRequest) throws GateLogicException, GateException;

	/**
	 * �������� ������� �� ������� �������.
	 * @param client - ������, ������ �������� ���������� �� ������� �������
	 * @param lastUpdateDate - ���� ���������� ���������� �������
	 * @param isNew - �������� �� ������ �����. true - �����, false - ������.
	 * @param user - ������������, ������� �������� ������� (client)
	 * @throws GateLogicException
	 * @throws GateException
	 */
	void update(Client client, Calendar lastUpdateDate, boolean isNew, User user) throws GateLogicException, GateException;

	/**
	 * ���������� ������ �������
	 * @param office
	 * @param registerRequest
	 * @throws GateLogicException
	 * @throws GateException
	 */
	void update( Office office, Document registerRequest) throws GateLogicException, GateException;

	 /**
	 * ����������� ��������
	 * @param client ������
	 * @param trustingClientId ������� ������������� �������, �� ������������ �������� ������� �������������.
	  * ���� ���������� � �� ��������������, �� null
	 * @param date ���� �����������
	 * @param type ��� �����������
	 * @param reason ������� �����������
	 * @return ������ ��� null ���� �������� ����������.
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public CancelationCallBack cancellation(Client client, String trustingClientId, Calendar date, CancelationType type, String reason) throws GateLogicException, GateException;

}
