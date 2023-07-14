package com.rssl.phizic.gorod.messaging.mock;

import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author Gainanov
 * @ created 21.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ReqDelEOrderQHandler extends MockHandlerSupport
{
	protected Document makeMockRequest(Document message) throws Exception
	{
		Document response = XmlHelper.loadDocumentFromResource("com/rssl/phizic/gorod/messaging/xml/AnsDelEOrder.xml");
        return response;
	}
}
