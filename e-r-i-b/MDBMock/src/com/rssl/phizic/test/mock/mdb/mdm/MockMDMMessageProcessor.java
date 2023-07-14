package com.rssl.phizic.test.mock.mdb.mdm;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author akrenev
 * @ created 20.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ���������
 */

public interface MockMDMMessageProcessor
{
	/**
	 * ���������� ���������
	 * @param message ���������
	 * @param messageId ������������� ��������������� ���������
	 */
	void processMessage(Object message, String messageId) throws GateLogicException, GateException;
}
