package com.rssl.phizic.esb.ejb;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
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
	 * @param source �������� ���������
	 */
	void processMessage(Object message, Message source) throws GateLogicException, GateException;
}
