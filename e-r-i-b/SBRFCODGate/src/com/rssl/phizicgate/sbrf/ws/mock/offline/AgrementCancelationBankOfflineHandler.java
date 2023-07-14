package com.rssl.phizicgate.sbrf.ws.mock.offline;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.RandomGUID;

import java.util.Random;

/**
 * @author Omeliyanchuk
 * @ created 07.06.2008
 * @ $Author$
 * @ $Revision$
 */

public class AgrementCancelationBankOfflineHandler extends AgrementCancelationOfflineHandler
{
	protected Document createFaultAnswer(String parentMessageId) throws Exception
	{
		Document response = XmlHelper.loadDocumentFromResource(ERROR_OFFLINE_XML_PATH);
		Element root = response.getDocumentElement();
		Element messageId = XmlHelper.selectSingleNode(root, MESSAGE_ID);
		messageId.setTextContent(new RandomGUID().getStringValue());
		Element parentId = XmlHelper.selectSingleNode(root, MESSAGE_PARENT_ID);
		parentId.setTextContent(parentMessageId);
		Random rnd = new Random();
		if(rnd.nextInt(4)==3)
		{
			Element code = XmlHelper.selectSingleNode(root, ERROR_CODE);
			code.setTextContent(ERROR_SHORT_MONEY);
			Element base = XmlHelper.selectSingleNode(root, REFUSAL_BASE);
			XmlHelper.appendSimpleElement(base,DEBTS,"10.0");
			code.setTextContent(ERROR_SHORT_MONEY);
		}
		return response;
	}
}
