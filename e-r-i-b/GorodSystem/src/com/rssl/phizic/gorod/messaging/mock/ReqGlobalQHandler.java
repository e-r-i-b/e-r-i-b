package com.rssl.phizic.gorod.messaging.mock;

import org.w3c.dom.Document;
import com.rssl.phizic.utils.xml.XmlHelper;

/**
 * Created by IntelliJ IDEA.
 * User: Stolbovskiy
 * Date: 08.06.2010
 * Time: 15:33:12
 * To change this template use File | Settings | File Templates.
 */
public class ReqGlobalQHandler extends MockHandlerSupport
{
	protected Document makeMockRequest(Document message) throws Exception
	{
	    Document response = XmlHelper.loadDocumentFromResource("com/rssl/phizic/gorod/messaging/xml/AnsGlobal.xml");
        return response;
	}
}
