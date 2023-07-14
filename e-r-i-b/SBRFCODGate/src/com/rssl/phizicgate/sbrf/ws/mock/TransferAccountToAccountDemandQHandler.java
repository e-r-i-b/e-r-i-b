package com.rssl.phizicgate.sbrf.ws.mock;

import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Evgrafov
 * @ created 16.10.2006
 * @ $Author: gladishev $
 * @ $Revision: 46026 $
 */

public class TransferAccountToAccountDemandQHandler extends MockHandlerSupport
{
	protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
	{
		Element sumElement = XmlHelper.selectSingleNode(message.getDocumentElement(), "sum");
		if(sumElement.getTextContent().equals("3.14"))
		{
			return new ErrorAHandler(DefaultErrorMessageHandler.ERROR_CLIENT_CODE, "1428 еще? :)").makeMockRequest(message, parentMessageId);
		}
		else
		{
			return new OfflineHandler().makeMockRequest(message, parentMessageId);
		}
	}
}