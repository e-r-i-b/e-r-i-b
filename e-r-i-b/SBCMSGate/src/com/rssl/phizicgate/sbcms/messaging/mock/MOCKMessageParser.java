package com.rssl.phizicgate.sbcms.messaging.mock;

import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.sbcms.messaging.CMSMessageData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerException;

/**
 * @author Egorova
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class MOCKMessageParser
{
	private MessageData messageData = null;
	private Document document = null;
	private Document body = null;

	void setMessage(MessageData messageData) throws GateException
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

	public String getMessageType() throws GateException
	{
		initBody();
		return extractMessageType(body.getDocumentElement());
	}

	public MessageData makeMessage(Document responseDoc) throws GateException
	{
		try
		{
			String responseText = XmlHelper.convertDomToText(responseDoc);
			MessageData response = new CMSMessageData();
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
			return XmlHelper.extractPart(messageDoc, "/message/*[position()=4]/*");
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private String extractMessageType(Element documentElement) throws GateException
	{
		try
		{
			return XmlHelper.getElementValueByPath(documentElement, "/WAY4_REQUEST/PRO_CODE");
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

}
