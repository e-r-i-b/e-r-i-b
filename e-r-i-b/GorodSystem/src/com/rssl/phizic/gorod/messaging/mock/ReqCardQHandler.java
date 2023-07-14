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
public class ReqCardQHandler extends MockHandlerSupport
{
	protected Document makeMockRequest(Document message) throws Exception
	{
		if(XmlHelper.selectSingleNode(message.getDocumentElement(), "/RSASMsg/ReqCard/Card") == null)
		{
			Document response       = XmlHelper.loadDocumentFromResource("com/rssl/phizic/gorod/messaging/xml/AnsCardServiceType.xml");
			String resp = XmlHelper.convertDomToText(response);
			resp.replace("UTF-8", "windows-1251");
			return response;
		}
		
		Element pan = XmlHelper.selectSingleNode(message.getDocumentElement(), "/RSASMsg/ReqCard/Card/pan");
        Document response       = XmlHelper.loadDocumentFromResource("com/rssl/phizic/gorod/messaging/xml/AnsCard.xml");
        String resp = XmlHelper.convertDomToText(response);
		resp.replace("UTF-8", "windows-1251");
		response = XmlHelper.parse(resp);
		Element responseAccount = XmlHelper.selectSingleNode(response.getDocumentElement(), "/RSASMsg/AnsCard/Card");

        responseAccount.setAttribute("pan",pan.getTextContent());

        return response;
	}
}
