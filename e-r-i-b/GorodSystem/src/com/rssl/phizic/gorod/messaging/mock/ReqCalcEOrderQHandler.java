package com.rssl.phizic.gorod.messaging.mock;

import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Gainanov
 * @ created 21.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ReqCalcEOrderQHandler extends MockHandlerSupport
{
	protected Document makeMockRequest(Document message) throws Exception
	{
		Element pan = XmlHelper.selectSingleNode(message.getDocumentElement(), "/RSASMsg/ReqCalcEOrder/EOrder/PayOrder");
		String calcMode = pan.getAttribute("calcmode");
		Document response = null;
		if(calcMode.equals("1"))
			response = XmlHelper.loadDocumentFromResource("com/rssl/phizic/gorod/messaging/xml/AnsCalcEOrder1.xml");
		else
			response = XmlHelper.loadDocumentFromResource("com/rssl/phizic/gorod/messaging/xml/AnsCalcEOrder2.xml");
		
        return response;
	}
}
