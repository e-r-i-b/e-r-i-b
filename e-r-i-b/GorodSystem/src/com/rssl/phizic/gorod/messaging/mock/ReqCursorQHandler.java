package com.rssl.phizic.gorod.messaging.mock;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.phizic.utils.xml.XmlHelper;

/**
 * @author Gainanov
 * @ created 24.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class ReqCursorQHandler extends MockHandlerSupport
{
	protected Document makeMockRequest(Document message) throws Exception
	{
        Document response       = XmlHelper.loadDocumentFromResource("com/rssl/phizic/gorod/messaging/xml/AnsCursor.xml");
        String resp = XmlHelper.convertDomToText(response);
		resp.replace("UTF-8", "windows-1251");
		response = XmlHelper.parse(resp);
		return response;
	}
}
