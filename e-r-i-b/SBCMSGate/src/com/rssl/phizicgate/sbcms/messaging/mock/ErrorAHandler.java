package com.rssl.phizicgate.sbcms.messaging.mock;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.util.Calendar;

/**
 * @author Egorova
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
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

    protected Document makeMockRequest(Document message) throws Exception
    {
        Document response       = XmlHelper.loadDocumentFromResource("WAY4_ERROR.xml");

	    //todo. Если из ПЦ сообщение все таки с <message>...</message> придет, вместо /POSGATE_MSG пишем /message
		Element messageElement = XmlHelper.selectSingleNode(response.getDocumentElement(), "/POSGATE_MSG/WAY4_ERROR/ERR_MESS");
        Element codeElement    = XmlHelper.selectSingleNode(response.getDocumentElement(), "/POSGATE_MSG/WAY4_ERROR/ERR_CODE");
	    Element dateElement    = XmlHelper.selectSingleNode(response.getDocumentElement(), "/POSGATE_MSG/WAY4_ERROR/DATE_TIME");

        codeElement.setTextContent(errorCode);
        messageElement.setTextContent(errorMessage);
	    Calendar calendar = Calendar.getInstance();
	    dateElement.setTextContent(calendar.getTime().toString());

        return response;
    }
}
