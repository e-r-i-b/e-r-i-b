package com.rssl.phizic.gate.bki;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * ������ ���
 * @author Puzikov
 * @ created 03.10.14
 * @ $Author$
 * @ $Revision$
 */

public interface CreditBureauService extends Service
{
	/**
	 * �������� ������� �� �������� ������� ��������� �������
	 * @param client ������
	 */
	void sendCheckCreditHistory(Client client) throws GateException;

	/**
	 * �������� ������� �� ��������� ������ ��������� �������
	 * @param client ������
	 * @param payment ������, �� �������� �������� ��������� ��
	 */
	void sendGetCreditHistory(Client client, GateDocument payment) throws GateException;
}
