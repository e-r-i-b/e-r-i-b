package com.rssl.phizic.gorod.messaging.mock;

import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.RandomGUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Gainanov
 * @ created 21.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ReqSaveEOrderQHandler extends MockHandlerSupport
{
	protected Document makeMockRequest(Document message) throws Exception
	{
		Document response       = XmlHelper.loadDocumentFromResource("com/rssl/phizic/gorod/messaging/xml/AnsSaveEOrder.xml");
		Element ePay = XmlHelper.selectSingleNode(response.getDocumentElement(),"AnsSaveEOrder/EOrder/EPay");
		ePay.setAttribute("unp", new RandomGUID().getStringValue());
		return response;
	}
}
