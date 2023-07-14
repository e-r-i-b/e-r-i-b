package com.rssl.auth.csa.wsclient;

import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;

import static com.rssl.auth.csa.wsclient.RequestConstants.*;

/**
 * @author niculichev
 * @ created 28.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class MessageGenerator
{
	private static final MessageGenerator INSTANCE = new MessageGenerator();
	private static final String VERSION_VALUE = "1";

	private MessageGenerator()
	{
	}

	public static MessageGenerator getInstance()
	{
		return INSTANCE;
	}
	
	public String buildMessage(Document body)
	{
		StringWriter writer = new StringWriter();
		InnerSerializer serializer = new InnerSerializer(writer, "utf-8");

		Attributes empty = new AttributesImpl();

		String messageId = new RandomGUID().getStringValue();

		try
		{
			serializer.startDocument();
			serializer.startElement("", "", MESSAGE_TAG, empty);

			serializer.startElement("", "", UID_TAG, empty);
			writeText(serializer, messageId);
			serializer.endElement("", "", UID_TAG);

			serializer.startElement("", "", DATE_TAG, empty);
			writeText(serializer, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));
			serializer.endElement("", "", DATE_TAG);

			serializer.startElement("", "", SOURCE_TAG, empty);
			writeText(serializer, StringHelper.getEmptyIfNull(LogThreadContext.getApplication()));
			serializer.endElement("", "", SOURCE_TAG);

			serializer.startElement("", "", VERSION_TAG, empty);
			writeText(serializer, VERSION_VALUE);
			serializer.endElement("", "", VERSION_TAG);

			serializer.startElement("", "", IP_TAG, empty);
			writeText(serializer, StringHelper.getEmptyIfNull(LogThreadContext.getIPAddress()));
			serializer.endElement("", "", IP_TAG);

			// тело запроса
			serializer.serializeElementOnly(body.getDocumentElement());

			serializer.endElement("", "", MESSAGE_TAG);
			serializer.endDocument();
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return writer.toString();
	}

	private void writeText(InnerSerializer serializer, String text) throws SAXException
	{
		char[] arr = text.toCharArray();
		serializer.characters(arr, 0, arr.length);
	}
}
