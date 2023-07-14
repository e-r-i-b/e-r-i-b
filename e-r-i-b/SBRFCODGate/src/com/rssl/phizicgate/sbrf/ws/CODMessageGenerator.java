package com.rssl.phizicgate.sbrf.ws;

import com.rssl.phizic.gate.exceptions.GateException;
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
import java.util.GregorianCalendar;

/**
 * @author Evgrafov
 * @ created 28.08.2006
 * @ $Author: bogdanov $
 * @ $Revision: 45085 $
 */
public class CODMessageGenerator extends MessageGenerator
{
	public static final String ABONEBT_ESK = "ESK";

	private String abonent = ABONEBT_ESK;

	public CODMessageData buildMessage(Document document, MessageInfo info, MessageHead messageHead) throws GateException
	{
		try
		{
			StringWriter    writer = new StringWriter();
			InnerSerializer serializer = new InnerSerializer(writer);

			Attributes      empty = new AttributesImpl();

			String messageId = getMessageId(messageHead);

			serializer.startDocument();
			serializer.startElement("", "", "message", empty);

			// ID
			serializer.startElement("", "", "messageId", empty);
			writeText(serializer, messageId);
			serializer.endElement("", "", "messageId");

			// DATE
			serializer.startElement("", "", "messageDate", empty);
			GregorianCalendar messageDate = new GregorianCalendar();
			writeText(serializer, XMLDatatypeHelper.formatDate(messageDate));
			serializer.endElement("", "", "messageDate");

			// ABONENT
			serializer.startElement("", "", "fromAbonent", empty);
			writeText(serializer, abonent);
			serializer.endElement("", "", "fromAbonent");

			// insert document
			serializer.serializeElementOnly(document.getDocumentElement());

			//вставляем parentID если нужно
			if(messageHead != null && messageHead.getParentMessageId() != null && info.getAttribute("hasParent") != null && info.getAttribute("hasParent").equals("true"))
			{
				// режем на внешний идентификатор и дату создания сообщения
				String[] externalIdAndDate = messageHead.getParentMessageId().split(Constants.EXTERNAL_ID_DELIMITER, 2);
				String entityIdentifier = externalIdAndDate[0];
				String logEntryDate = externalIdAndDate[1];

				serializer.startElement("", "", "parentId", empty);

				serializer.startElement("", "", "messageId", empty);
				writeText(serializer, entityIdentifier);
				serializer.endElement("", "", "messageId");

				serializer.startElement("", "", "messageDate", empty);
				writeText(serializer, logEntryDate);
				serializer.endElement("", "", "messageDate");

				serializer.startElement("", "", "fromAbonent", empty);
				writeText(serializer, abonent);
				serializer.endElement("", "", "fromAbonent");

				serializer.endElement("", "", "parentId");
			}

			serializer.endElement("", "", "message");
			serializer.endDocument();

			String text = writer.toString();

			CODMessageData messageData = new CODMessageData();
			messageData.setId(messageId);
			messageData.setDate(messageDate);
			messageData.setAbonent(abonent);
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

	/**
	 * @param head иныормация о сообщении.
	 * @return идентификатор документа.
	 */
	protected String getMessageId(MessageHead head)
	{
		return new RandomGUID().getStringValue();
	}
}