package com.rssl.phizicgate.cpfl.messaging;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.MessageGenerator;
import com.rssl.phizic.gate.messaging.MessageHead;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;

/**
 * @author krenev
 * @ created 15.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class CPFLMessageGenerator extends MessageGenerator
{
	public static final String ABONEBT_ESK = "ESK";

	public MessageData buildMessage(Document document, MessageInfo info, MessageHead messageHead) throws GateException
	{
		try
		{
			StringWriter writer = new StringWriter();
			InnerSerializer serializer = new InnerSerializer(writer);

			Attributes empty = new AttributesImpl();

			String messageId = new RandomGUID().getStringValue();

			serializer.startDocument();
			serializer.startElement("", "", "message", empty);

			// ID
			serializer.startElement("", "", "messageId", empty);
			writeText(serializer, messageId);
			serializer.endElement("", "", "messageId");

			// DATE
			serializer.startElement("", "", "messageDate", empty);
			writeText(serializer, XMLDatatypeHelper.formatDate(Calendar.getInstance()));
			serializer.endElement("", "", "messageDate");

			// ABONENT
			serializer.startElement("", "", "fromAbonent", empty);
			writeText(serializer, ABONEBT_ESK);
			serializer.endElement("", "", "fromAbonent");

			// insert document
			serializer.serializeElementOnly(document.getDocumentElement());
			serializer.endElement("", "", "message");
			serializer.endDocument();

			CPFLMessageData messageData = new CPFLMessageData(writer.toString());
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
