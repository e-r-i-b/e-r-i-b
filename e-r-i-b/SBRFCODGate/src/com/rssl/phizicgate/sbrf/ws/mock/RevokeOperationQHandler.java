package com.rssl.phizicgate.sbrf.ws.mock;

import org.w3c.dom.Document;

import java.util.Random;

/**
 * @author Gainanov
 * @ created 12.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class RevokeOperationQHandler extends MockHandlerSupport
{
	protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
	{
		Random rnd = new Random();
//		if (rnd.nextInt(5) != 4)
//			return new ErrorAHandler(DefaultErrorMessageHandler.ERROR_CLIENT_CODE, "Низя отозвать. Пешите письма").makeMockRequest(message);
		return new ConfirmationHandler().makeMockRequest(message, parentMessageId);
	}
}
