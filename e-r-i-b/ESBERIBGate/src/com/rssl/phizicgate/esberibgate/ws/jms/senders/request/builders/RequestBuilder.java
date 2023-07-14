package com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author komarov
 * @ created 06.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ������� ��������
 */
public interface RequestBuilder<R, D>
{
	/**
	 * ��������� ������ �� ���������
	 * @param document ��������
	 * @return ������
	 */
	R makeRequest(D document) throws GateException, GateLogicException;

	/**
	 * ���������� ������������� �������
	 * @param request ������
	 * @return ������������� �������
	 */
	String getRequestId(R request);

	/**
	 * ���������� ��� ���������
	 * @return ��� ���������
	 */
	String getRequestMessageType();
}
