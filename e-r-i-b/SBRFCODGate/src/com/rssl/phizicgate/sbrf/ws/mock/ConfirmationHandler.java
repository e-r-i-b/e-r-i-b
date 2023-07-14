package com.rssl.phizicgate.sbrf.ws.mock;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Evgrafov
 * @ created 19.09.2006
 * @ $Author: gladishev $
 * @ $Revision: 46026 $
 */

public class ConfirmationHandler extends MockHandlerSupport
{
	protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
	{
		Document document = XmlHelper.loadDocumentFromResource("confirmation_a.xml");
		if (StringHelper.isNotEmpty(parentMessageId))
		{
			Element messageIdElement = XmlHelper.selectSingleNode(document.getDocumentElement(), "/message/messageId");
			messageIdElement.setTextContent(parentMessageId);
		}
		return document;
	}
}