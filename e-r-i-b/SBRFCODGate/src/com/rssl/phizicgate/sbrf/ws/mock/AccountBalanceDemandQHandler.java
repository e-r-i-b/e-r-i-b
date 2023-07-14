package com.rssl.phizicgate.sbrf.ws.mock;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.phizic.utils.xml.XmlHelper;

/**
 * @author Kidyaev
 * @ created 19.09.2006
 * @ $Author$
 * @ $Revision$
 */
public class AccountBalanceDemandQHandler extends MockHandlerSupport
{
    protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
    {
        Element  requestAccount = XmlHelper.selectSingleNode(message.getDocumentElement(), "/accountBalanceDemand_q/account");
        Document response       = XmlHelper.loadDocumentFromResource("com/rssl/phizicgate/sbrf/ws/mock/xml/accountBalance_a.xml");
        Element responseAccount = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/accountBalance_a/account");
        responseAccount.setTextContent(requestAccount.getTextContent());

        return response;
    }
}
