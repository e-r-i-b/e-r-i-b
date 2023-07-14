package com.rssl.phizicgate.sbrf.ws.mock;

import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;
import org.w3c.dom.Document;

import java.util.Random;

/**
 * @author hudyakov
 * @ created 17.06.2009
 * @ $Author$
 * @ $Revision$
 */

public class TransferOtherBankQHandler extends MockHandlerSupport 
{

	protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
	{
		Random rand = new Random();
		if (rand.nextInt(100) > 90)
			return new ErrorAHandler(DefaultErrorMessageHandler.ERROR_SHORT_MONEY, "").makeMockRequest(message, parentMessageId);
		return new OfflineHandler().makeMockRequest(message, parentMessageId);
	}
}
