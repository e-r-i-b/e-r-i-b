package com.rssl.phizgate.common.messaging.mock;

import com.rssl.phizic.gate.exceptions.GateException;
import org.w3c.dom.Document;

/**
 * @author Krenev
 * @ created 02.02.2010
 * @ $Author$
 * @ $Revision$
 * ��������� ��� �������� RequestHandler
 */
public interface MockRequestHandler
{

	/**
	 * ���������� ������
	 * @param request ������
	 * @return �������
	 * @throws GateException
	 */
	public Document proccessRequest(Document request) throws GateException;
}
