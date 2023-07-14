package com.rssl.phizicgate.sbrf.ws.mock;

import org.w3c.dom.Document;
import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;

/**
 * @author osminin
 * @ created 01.09.2009
 * @ $Author$
 * @ $Revision$
 */

// Нэндлер для выкидывания клиентской ошибки Error_Client. Используется для тестирования функционала на предмет обработки данной ошибки
// Для использования хэндлера необходимо в MockWebBankServiceFacade в конструкторе прописать его для нужного сообщения.
// например: handlerMap.put("executeBillingPayment",          new ClientErrorTestHandler());
public class ClientErrorTestHandler extends MockHandlerSupport
{
	protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
	{
		return new ErrorAHandler(DefaultErrorMessageHandler.ERROR_CLIENT_CODE,"Тестирование клиентской ошибки").makeMockRequest(message, parentMessageId);
	}
}
