package com.rssl.phizicgate.sbrf.ws.mock;

import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Evgrafov
 * @ created 19.09.2006
 * @ $Author: gladishev $
 * @ $Revision: 46026 $
 */

public class OfflineHandler extends MockHandlerSupport
{
	protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
	{
		Document response = XmlHelper.loadDocumentFromResource("com/rssl/phizicgate/sbrf/ws/mock/xml/acknowledge_t.xml");
		Element messageIdElement = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/messageId");
		if (StringHelper.isNotEmpty(parentMessageId))
			messageIdElement.setTextContent(parentMessageId);
		else
			messageIdElement.setTextContent(new RandomGUID().getStringValue());

		return response;
	}
}