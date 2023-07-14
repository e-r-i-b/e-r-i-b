package com.rssl.phizicgate.sbrf.ws.mock;

import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 21.09.2006
 * Time: 10:18:19
 * To change this template use File | Settings | File Templates.
 */
public class LongOfferDemandQHandler extends MockHandlerSupport
{
    protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
    {
        Element  requestAccount = XmlHelper.selectSingleNode(message.getDocumentElement(), "/form190ResultDemand_q/account");
        Element  requestStartDate = XmlHelper.selectSingleNode(message.getDocumentElement(), "/form190ResultDemand_q/startDate");
        Element  requestEndDate = XmlHelper.selectSingleNode(message.getDocumentElement(), "/form190ResultDemand_q/endDate");

        Document response = null;
        if ( XmlHelper.getElementText(requestEndDate).equals("2007-01-01"))
            response       = XmlHelper.loadDocumentFromResource("com/rssl/phizicgate/sbrf/ws/mock/xml/empty_form190Result_a.xml");
        else
            response       = XmlHelper.loadDocumentFromResource("com/rssl/phizicgate/sbrf/ws/mock/xml/form190Result_a.xml");

        Element responseAccount = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/form190Result_a/account");
        Element responseStartDate = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/form190Result_a/startDate");
        Element responseEndDate = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/form190Result_a/endDate");

        responseAccount.setTextContent(requestAccount.getTextContent());
        responseStartDate.setTextContent(requestStartDate.getTextContent());
        responseEndDate.setTextContent(requestEndDate.getTextContent());

        return response;
    }
}
