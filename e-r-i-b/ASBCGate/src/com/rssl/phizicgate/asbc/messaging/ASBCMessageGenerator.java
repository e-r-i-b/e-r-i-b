package com.rssl.phizicgate.asbc.messaging;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.MessageGenerator;
import com.rssl.phizic.gate.messaging.MessageHead;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.utils.RandomGUID;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Krenev
 * @ created 02.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class ASBCMessageGenerator extends MessageGenerator
{
	public MessageData buildMessage(Document document, MessageInfo info, MessageHead messageHead) throws GateException
	{
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			InnerSerializer serializer = new InnerSerializer(os, "windows-1251");
			String messageId = new RandomGUID().getStringValue();
			AttributesImpl attributes = new AttributesImpl();
			attributes.addAttribute("", "", "key", "CDATA", info.getAttribute("key"));
			attributes.addAttribute("", "", "expectedEncoding", "CDATA", "windows-1251");
			serializer.startDocument();
			serializer.startElement("", "", "request", attributes);

			// insert document
			serializer.serializeElementOnly(document.getDocumentElement());

			serializer.endElement("", "", "request");
			serializer.endDocument();

			ASBCMessageData messageData = new ASBCMessageData(os.toByteArray());
			messageData.setId(messageId);

			return messageData;
		}
		catch (SAXException e)
		{
			throw new GateException(e);
		}
		catch (IOException e)
		{
			throw new GateException(e);
		}
	}
}
