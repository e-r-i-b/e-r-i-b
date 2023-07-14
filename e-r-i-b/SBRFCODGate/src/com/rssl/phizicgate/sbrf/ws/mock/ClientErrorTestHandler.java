package com.rssl.phizicgate.sbrf.ws.mock;

import org.w3c.dom.Document;
import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;

/**
 * @author osminin
 * @ created 01.09.2009
 * @ $Author$
 * @ $Revision$
 */

// ������� ��� ����������� ���������� ������ Error_Client. ������������ ��� ������������ ����������� �� ������� ��������� ������ ������
// ��� ������������� �������� ���������� � MockWebBankServiceFacade � ������������ ��������� ��� ��� ������� ���������.
// ��������: handlerMap.put("executeBillingPayment",          new ClientErrorTestHandler());
public class ClientErrorTestHandler extends MockHandlerSupport
{
	protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
	{
		return new ErrorAHandler(DefaultErrorMessageHandler.ERROR_CLIENT_CODE,"������������ ���������� ������").makeMockRequest(message, parentMessageId);
	}
}
