package com.rssl.phizic.gate.mbuesi;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * ������ ��� ������ � ����������� UESI
 * @author Pankin
 * @ created 21.01.15
 * @ $Author$
 * @ $Revision$
 */
public interface UESIMessagesService extends Service
{
	/**
	 * �������� ��������� ��������� ���������
	 * @param externalId ������������� ���������
	 * @param success ������� ���������� ���������
	 */
	void processMessage(String externalId, boolean success) throws GateException;
}
