package com.rssl.phizicgate.sbrf.ws.mock;

import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Evgrafov
 * @ created 19.09.2006
 * @ $Author: gladishev $
 * @ $Revision: 46026 $
 */

public class AgreementModificationQHandler extends MockHandlerSupport
{
    protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
    {
        Element citizen = XmlHelper.selectSingleNode(message.getDocumentElement(), "owner/citizen");

        if ( XmlHelper.getElementText(citizen).equals("Албания") )
            return new ErrorAHandler(DefaultErrorMessageHandler.ERROR_CLIENT_CODE, "Уже выучил албанский?").makeMockRequest(message, parentMessageId);

        return new ConfirmationHandler().makeMockRequest(message, parentMessageId);
    }
}