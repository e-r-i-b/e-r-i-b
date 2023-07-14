package com.rssl.phizicgate.sbrf.ws.mock;

import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Evgrafov
 * @ created 19.09.2006
 * @ $Author: gladishev $
 * @ $Revision: 46026 $
 */

public class AgreementCancelationQHandler extends OfflineHandler
{
	public static ConcurrentLinkedQueue<String> PerantID = new ConcurrentLinkedQueue<String>();

	protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
	{
		//для дальнейшей обработки делаем так, чтоб потом легко достать клиента.

		Element clientIdEl = XmlHelper.selectSingleNode(message.getDocumentElement(), "id");
		String clientId = clientIdEl.getTextContent();

		PerantID.add(clientId);

		Document response = super.makeMockRequest(message, parentMessageId);

		Element responseMessageId = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/messageId");
		responseMessageId.setTextContent(clientId);
		return response;

	}

	protected String getMessageName()
	{
		return "agreementCancellation_q";
	}
}