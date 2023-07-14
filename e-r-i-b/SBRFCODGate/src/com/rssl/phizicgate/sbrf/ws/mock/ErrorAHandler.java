package com.rssl.phizicgate.sbrf.ws.mock;

import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Evgrafov
 * @ created 19.09.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 2175 $
 */

public class ErrorAHandler extends MockHandlerSupport
{
    private String errorCode;
    private String errorMessage;

    public ErrorAHandler(String code, String message)
    {
        this.errorCode    = code;
        this.errorMessage = message;
    }

    protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
    {
        Document response       = XmlHelper.loadDocumentFromResource("error_a.xml");
        Element  codeElement    = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/error_a/code");
        Element  messageElement = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/error_a/message");
	    if (StringHelper.isNotEmpty(parentMessageId))
	    {
			Element  messageIdElement = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/messageId");
			messageIdElement.setTextContent(parentMessageId);
	    }

        codeElement.setTextContent(errorCode);
        messageElement.setTextContent(errorMessage);

        return response;
    }
}
