package com.rssl.phizicgate.sbcms.messaging;

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
import java.util.GregorianCalendar;

/**
 * todo. Комментарии оставлены на случай, если из ПЦ сообщение все таки с <message>...</message> придет. Убрать как станет понятно.
 * @author Egorova
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class CMSMessageGenerator extends MessageGenerator
{
	public static final String ABONEBT_ESK = "ESK";

	public CMSMessageData buildMessage(Document document, MessageInfo info, MessageHead messageHead) throws GateException
	{
		try
		{
			StringWriter writer = new StringWriter();
			InnerSerializer serializer = new InnerSerializer(writer);

			Attributes empty = new AttributesImpl();

			String messageId = new RandomGUID().getStringValue();

			serializer.startDocument();
//			serializer.startElement("", "", "message", empty);
//
//			// ID
//			serializer.startElement("", "", "messageId", empty);
//			writeText(serializer, messageId);
//			serializer.endElement("", "", "messageId");
//
//			// DATE
//			serializer.startElement("", "", "messageDate", empty);
			GregorianCalendar messageDate = new GregorianCalendar();
//			writeText(serializer, XMLDatatypeHelper.formatDate(messageDate));
//			serializer.endElement("", "", "messageDate");
//
//			// ABONENT
//			serializer.startElement("", "", "fromAbonent", empty);
//			writeText(serializer, ABONEBT_ESK);
//			serializer.endElement("", "", "fromAbonent");

			serializer.startElement("", "", "POSGATE_MSG", empty);
			// insert document
			serializer.serializeElementOnly(document.getDocumentElement());
			serializer.endElement("", "", "POSGATE_MSG");			

//			serializer.endElement("", "", "message");
			serializer.endDocument();

			String text = writer.toString();

			CMSMessageData messageData = new CMSMessageData();
			messageData.setId(messageId);
			messageData.setDate(messageDate);
			messageData.setAbonent(ABONEBT_ESK);
			messageData.setBody(text);

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
