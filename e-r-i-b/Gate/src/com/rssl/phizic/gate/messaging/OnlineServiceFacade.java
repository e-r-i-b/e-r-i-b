package com.rssl.phizic.gate.messaging;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.w3c.dom.Document;

/**
 * @author Evgrafov
 * @ created 09.03.2007
 * @ $Author: niculichev $
 * @ $Revision: 38254 $
 */

public interface OnlineServiceFacade extends Service
{
	/**
	 * �������� online ��������� �� ������� �������
	 * @param message XML ��������� (������ ������������ ����� ��� ���� message � ��.)
	 * @param messageHead ���� � ��������� ���������
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException ������ ��� ����������� �����������
	 * @throws com.rssl.phizic.gate.exceptions.GateException ������ ���������� ��� ��� ���-�� � ���� ����
	 * @return XML �������� - ����� ������� �������
	 */
	Document sendOnlineMessage(Document message, MessageHead messageHead) throws GateLogicException, GateException;

	/**
	 * �������� �������
	 * @param nameRequest ��� ������
	 * @return ������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	GateMessage createRequest(String nameRequest) throws GateException;

	/**
	 * �������� online ��������� �� ������� �������
	 *
	 * @param request ������
	 * @param messageHead ���� � ��������� ���������
	 * @return XML �������� - ����� ������� �������
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException ������ ��� ����������� �����������
	 * @throws com.rssl.phizic.gate.exceptions.GateException      ������ ���������� ��� ��� ���-�� � ���� ����
	 */
	Document sendOnlineMessage(GateMessage request, MessageHead messageHead) throws GateLogicException, GateException;
}
