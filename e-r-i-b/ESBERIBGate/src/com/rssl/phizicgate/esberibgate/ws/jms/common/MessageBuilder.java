package com.rssl.phizicgate.esberibgate.ws.jms.common;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author akrenev
 * @ created 28.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ �������
 */

public interface MessageBuilder
{
	/**
	 * ������������ ������
	 * @param requestData ����������, ����������� ��� �������
	 * @return ������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public <RqClass> String buildMessage(RqClass requestData) throws GateException;

}
