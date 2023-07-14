package com.rssl.phizicgate.iqwave.messaging.mock;

import com.rssl.phizgate.common.messaging.mock.FilenameRequestHandler;
import com.rssl.phizgate.common.messaging.mock.MockRequestHandler;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author gladishev
 * @ created 02.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class IQWFilenameRequestHandler extends FilenameRequestHandler implements MockRequestHandler
{
	public IQWFilenameRequestHandler(String filename) throws GateException
	{
		super(filename);
	}

	public Document proccessRequest(Document request) throws GateException
	{
		Document document = super.proccessRequest(request);
		Element root = document.getDocumentElement();
		try
		{
			Element element = XmlHelper.selectSingleNode(root, "//MessUID/MessageId");
			element.setTextContent(new RandomGUID().getStringValue());
			element = XmlHelper.selectSingleNode(root, "//MessUID/MessageDate");
			element.setTextContent(XMLDatatypeHelper.formatDateWithoutTimeZone(DateHelper.getCurrentDate()));

			Element requestRoot = request.getDocumentElement();
			Element requestMessageId = XmlHelper.selectSingleNode(requestRoot, "//MessUID/MessageId");
			Element requestMessageDate = XmlHelper.selectSingleNode(requestRoot, "//MessUID/MessageDate");

			element = XmlHelper.selectSingleNode(root, "//parentId/MessageId");
			element.setTextContent(requestMessageId.getTextContent());
			element = XmlHelper.selectSingleNode(root, "//parentId/MessageDate");
			element.setTextContent(requestMessageDate.getTextContent());
			return document;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
