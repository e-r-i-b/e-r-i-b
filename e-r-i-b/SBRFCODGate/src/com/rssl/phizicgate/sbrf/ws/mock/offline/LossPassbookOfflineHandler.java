package com.rssl.phizicgate.sbrf.ws.mock.offline;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.RandomGUID;

/**
 * @author Egorova
 * @ created 17.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class LossPassbookOfflineHandler extends BaseOfflineHandler
{

	protected Document createSuccessAnswer(String parentMessageId, String debitSumReal,String creditSumReal) throws Exception
	{
		Document response = XmlHelper.loadDocumentFromResource(CONFIRMATION_OFFLINE_XML_PATH);
		Element root = response.getDocumentElement();
		Element messageId = XmlHelper.selectSingleNode(root, MESSAGE_ID);
		messageId.setTextContent(new RandomGUID().getStringValue());

		String[] parentMessage = parentMessageId.split("@", 2);
		Element parentId = XmlHelper.selectSingleNode(root, MESSAGE_PARENT_ID);
		parentId.setTextContent(parentMessage[0]);
		Element parentDate = XmlHelper.selectSingleNode(root, MESSAGE_PARENT_DATE);
		parentDate.setTextContent(parentMessage[1]);
		return response;
	}
}
