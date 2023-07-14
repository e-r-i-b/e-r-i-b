package com.rssl.phizicgate.esberibgate.ws.jms.common.message;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 21.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ������ jms ���������
 */

public interface OnlineMessageProcessor<Rs> extends MessageProcessor<OnlineMessageProcessor<Rs>>
{
	/**
	 * ��������� ����� �������
	 * @return ��� �������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	String getServiceName() throws GateException, GateLogicException;

	/**
	 * ��������� ��������� ��������� ��������� �� �������
	 * @param message ������
	 * @return ��������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	String getResponseMessageSelector(Message message) throws GateException;

	/**
	 * ��������� ����������� ������������� ������
	 * @param message �����
	 * @return ���������� ������������� ������
	 * @throws GateException
	 */
	Response<Rs> buildResponse(Message message) throws GateException, GateLogicException;

	/**
	 * ���������� �����
	 * @param response �����
	 * @throws GateLogicException
	 * @throws GateException
	 */
	void processResponse(Response<Rs> response) throws GateLogicException, GateException;
}
