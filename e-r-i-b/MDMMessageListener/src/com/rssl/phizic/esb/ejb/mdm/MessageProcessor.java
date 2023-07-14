package com.rssl.phizic.esb.ejb.mdm;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author akrenev
 * @ created 17.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ���������
 */

public interface MessageProcessor
{
	/**
	 * �������� ��� ���������
	 * @param message ���������
	 * @return ��� ���������
	 */
	String getMessageType(Object message);

	/**
	 * �������� ������������� ���������
	 * @param message ���������
	 * @return ������������� ���������
	 */
	String getMessageId(Object message);

	/**
	 * ���������� ���������
	 * @param message ���������
	 */
	void processMessage(Object message) throws GateLogicException, GateException;
}
