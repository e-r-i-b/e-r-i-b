 package com.rssl.phizicgate.sbrf.ws.mock.offline;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;

import java.util.Random;

/**
 * @author Omeliyanchuk
 * @ created 07.06.2008
 * @ $Author$
 * @ $Revision$
 */

public class AgrementCancelationOfflineHandler implements OfflineMockHandler
{
	protected static final String ERROR_OFFLINE_XML_PATH = "com/rssl/phizicgate/sbrf/ws/mock/xml/refusalCancellation_a.xml";
	protected static final String CONFIRMATION_OFFLINE_XML_PATH = "com/rssl/phizicgate/sbrf/ws/mock/xml/cancellationConfirmation_a.xml";
	protected static final String MESSAGE_ID = "/message/messageId";
	protected static final String MESSAGE_PARENT_ID = "/message/parentId/messageId";
	protected static final String REFUSAL_BASE = "/message/refusalCancellation_a/code";
	protected static final String ERROR_CODE = "/message/refusalCancellation_a/code";
	protected static final String ERROR_CLIENT = "ERROR_CLIENT";
	protected static final String ERROR_SHORT_MONEY = "ERROR_SHORT_MONEY";
	protected static final String DEBTS = "debts";

	public Document handle(Object object) throws GateException
	{
		try
		{
			MessagingLogEntry entry = (MessagingLogEntry)object;
			String request = entry.getMessageRequest();
			Document requestDocument = XmlHelper.parse(request);

			String parentMessageid = XmlHelper.selectSingleNode(requestDocument.getDocumentElement(), MESSAGE_ID).getTextContent();


			Random rnd = new Random();
			if(rnd.nextInt(4)!=3)
			{
				return createSuccessAnswer(parentMessageid);
			}
			else
			{
				return createFaultAnswer(parentMessageid);
			}
		}
		catch(Exception ex)
		{
			throw new GateException("Ошибка при подготовке вызова offline сообщения",ex);
		}
	}

	protected Document createFaultAnswer(String parentMessageId) throws Exception
	{
		Document response = XmlHelper.loadDocumentFromResource(ERROR_OFFLINE_XML_PATH);
		Element root = response.getDocumentElement();
		Element messageId = XmlHelper.selectSingleNode(root, MESSAGE_ID);
		messageId.setTextContent(new RandomGUID().getStringValue());
		Element parentId = XmlHelper.selectSingleNode(root, MESSAGE_PARENT_ID);
		parentId.setTextContent(parentMessageId);

		return response;
	}

	protected Document createSuccessAnswer(String parentMessageId) throws Exception
	{
		Document response = XmlHelper.loadDocumentFromResource(CONFIRMATION_OFFLINE_XML_PATH);
		Element root = response.getDocumentElement();
		Element messageId = XmlHelper.selectSingleNode(root, MESSAGE_ID);
		messageId.setTextContent(new RandomGUID().getStringValue());
		Element parentId = XmlHelper.selectSingleNode(root, MESSAGE_PARENT_ID);
		parentId.setTextContent(parentMessageId);
		return response;
	}
}
