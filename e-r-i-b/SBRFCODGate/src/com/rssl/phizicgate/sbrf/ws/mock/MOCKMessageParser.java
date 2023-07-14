package com.rssl.phizicgate.sbrf.ws.mock;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.sbrf.ws.CODMessageData;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;

/**
 * @author Omeliyanchuk
 * @ created 13.10.2006
 * @ $Author$
 * @ $Revision$
 */

public class MOCKMessageParser
{
	private MessageData messageData = null;
	private Document document = null;
	private Document body = null;

	public void setMessage(MessageData messageData) throws GateException
	{
		this.messageData = messageData;
		this.document = null;
		this.body = null;
	}

	public Document getMessageDocument() throws GateException
	{
		initDocument();
		return document;
	}

	public Document getMessageBody() throws GateException
	{
		initBody();
		return body;
	}

	public String getMessageId() throws GateException
	{
		if (document == null)
			initDocument();
		try
		{
			return XmlHelper.getSimpleElementValue(document.getDocumentElement(), "messageId");
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

	}

	public String getMessageType() throws GateException
	{
		initBody();
		return body.getDocumentElement().getNodeName();
	}

	public MessageData makeMessage(Document responseDoc) throws GateException
	{
		try
		{
			String responseText = XmlHelper.convertDomToText(responseDoc);
			MessageData response = new CODMessageData();
			response.setBody(responseText);
			return response;
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	private void initDocument() throws GateException
	{
		if( messageData == null )
			throw new GateException("Не установлено сообщение");

		if(document==null)
			document = parseMessage(messageData);
	}

	private void initBody() throws GateException
	{
		initDocument();
		body = extractBody(document);
	}

	private Document parseMessage(MessageData messageData) throws GateException
	{
		try
		{
			return XmlHelper.parse(messageData.getBodyAsString(null));
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private Document extractBody(Document messageDoc) throws GateException
	{
		try
		{
			return XmlHelper.extractPart(messageDoc, "/message/*[position()=4]");
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

}
