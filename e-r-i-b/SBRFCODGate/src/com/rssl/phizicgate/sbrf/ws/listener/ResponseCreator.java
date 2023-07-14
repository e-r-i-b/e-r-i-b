package com.rssl.phizicgate.sbrf.ws.listener;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import javax.xml.parsers.DocumentBuilder;

/**
 * @author Omeliyanchuk
 * @ created 12.02.2007
 * @ $Author$
 * @ $Revision$
 */

public class ResponseCreator
{
	public static final String SUCCESS_MESSAGE_TYPE = "acknowledge_t";
	public static final String ERROR_MESSAGE_TYPE = "refusal_t";

	public static Document createAcknowledge(String messageId, Calendar date, String abonent) throws GateException
	{
		Document document = createMessagePart(messageId, DateHelper.toXMLDateFormat(date.getTime()), abonent);
		Element root = document.getDocumentElement();
		XmlHelper. appendSimpleElement(root, SUCCESS_MESSAGE_TYPE);
		return document;
	}

	public static Document createRefusal(String messageId, Calendar date, String abonent, String code, String message) throws GateException
	{
		Document document = createMessagePart(messageId, DateHelper.toXMLDateFormat(date.getTime()), abonent);
		Element root = document.getDocumentElement();
		Element refusalEl = XmlHelper. appendSimpleElement(root, ERROR_MESSAGE_TYPE);
		XmlHelper.appendSimpleElement(refusalEl, "code", code);
		XmlHelper.appendSimpleElement(refusalEl, "message", message);
		return document;
	}

	private static Document createMessagePart(String messageId, String date, String abonent) throws GateException
	{

		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();

		Document document = documentBuilder.newDocument();
		Element root = document.createElement("message");
		document.appendChild(root);
		XmlHelper.appendSimpleElement(root, "messageId", messageId);
		XmlHelper.appendSimpleElement(root, "messageDate", date);
		XmlHelper.appendSimpleElement(root, "fromAbonent", abonent);
		return document;
	}
}
