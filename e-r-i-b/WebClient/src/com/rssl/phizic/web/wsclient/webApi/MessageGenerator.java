package com.rssl.phizic.web.wsclient.webApi;

import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.IOException;
import java.io.StringWriter;

import static com.rssl.phizic.web.wsclient.webApi.RequestConstants.*;

/**
 * Создает "скелет" сообщения
 * @author Jatsky
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 */

public class MessageGenerator
{
	private static final MessageGenerator INSTANCE = new MessageGenerator();

	private MessageGenerator()
	{
	}

	public static MessageGenerator getInstance()
	{
		return INSTANCE;
	}

	public String buildMessage(Document body, String operation, String ip)
	{
		StringWriter writer = new StringWriter();
		InnerSerializer serializer = new InnerSerializer(writer, "utf-8");

		Attributes empty = new AttributesImpl();

		try
		{
			serializer.startDocument();
			serializer.startElement("", "", MESSAGE_TAG, empty);

			serializer.startElement("", "", IP_TAG, empty);
			writeText(serializer, ip);
			serializer.endElement("", "", IP_TAG);

			serializer.startElement("", "", OPERATION_TAG, empty);
			writeText(serializer, operation);
			serializer.endElement("", "", OPERATION_TAG);

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

