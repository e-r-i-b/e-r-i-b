package com.rssl.phizic.gorod.messaging;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageGenerator;
import com.rssl.phizic.gate.messaging.MessageHead;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.utils.RandomGUID;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Gainanov
 * @ created 16.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class GorodMessageGenerator extends MessageGenerator
{
	public GorodMessageData buildMessage(Document document, MessageInfo info, MessageHead messageHead) throws GateException
	{
		try
		{
			StringWriter writer = new StringWriter();
			InnerSerializer serializer = new InnerSerializer(writer);

			Attributes empty = new AttributesImpl();

			serializer.startDocument();
			serializer.startElement("", "", "RSASMsg", empty);

			// insert document
			serializer.serializeElementOnly(document.getDocumentElement());

			serializer.endElement("", "", "RSASMsg");
			serializer.endDocument();

			String text = writer.toString();
			text = text.substring(0, text.indexOf("RSASMsg") + 7) + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"gorod.xsd\"" + text.substring( text.indexOf("RSASMsg") + 7);
			GorodMessageData messageData = new GorodMessageData();
			messageData.setId(new RandomGUID().getStringValue());
			messageData.setBody(text.getBytes("utf-8"));

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
