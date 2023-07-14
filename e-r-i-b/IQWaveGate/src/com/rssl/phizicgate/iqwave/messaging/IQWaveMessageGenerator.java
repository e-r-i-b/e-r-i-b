package com.rssl.phizicgate.iqwave.messaging;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.MessageGenerator;
import com.rssl.phizic.gate.messaging.MessageHead;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.GregorianCalendar;

/**
 * @author gladyshev
 * @ created 04.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class IQWaveMessageGenerator extends MessageGenerator
{
	public MessageData buildMessage(Document document, MessageInfo info, MessageHead messageHead) throws GateException
	{
		try
		{
			Attributes emptyAttributes = new AttributesImpl();
			StringWriter writer = new StringWriter();
			InnerSerializer serializer = new InnerSerializer(writer, "utf-8");
			String messageType = info.getName();

			serializer.startDocument();
			serializer.startElement("", "", messageType, emptyAttributes);
			// START HEAD
			serializer.startElement("", "", Constants.HEAD_TAG, emptyAttributes);

			//START MessUID
			serializer.startElement("", "", Constants.MESSAGE_UID_TAG, emptyAttributes);
			//MessageId
			serializer.startElement("", "", Constants.MESSAGE_ID_TAG, emptyAttributes);

			String messageId = (messageHead.getMessageId() == null) ? new RandomGUID().getStringValue() : messageHead.getMessageId();
			writeText(serializer, messageId);
			serializer.endElement("", "", Constants.MESSAGE_ID_TAG);
			//MessageDate
			serializer.startElement("", "", Constants.MESSAGE_DATE_TAG, emptyAttributes);

			GregorianCalendar messageDate = new GregorianCalendar();
			writeText(serializer, XMLDatatypeHelper.formatDateWithoutTimeZone(
					messageHead.getMessageDate() != null ? messageHead.getMessageDate() : messageDate));
			serializer.endElement("", "", Constants.MESSAGE_DATE_TAG);
			//FromAbonent
			serializer.startElement("", "", Constants.FROM_ABONENT_TAG, emptyAttributes);
			writeText(serializer, Constants.ABONENT_SBOL);
			serializer.endElement("", "", Constants.FROM_ABONENT_TAG);

			serializer.endElement("", "", Constants.MESSAGE_UID_TAG);
			//END MessUID

			//MessageType
			serializer.startElement("", "", Constants.MESSAGE_TYPE_TAG, emptyAttributes);
			writeText(serializer, messageType);
			serializer.endElement("", "", Constants.MESSAGE_TYPE_TAG);
			//END MessageType

			//Version
			serializer.startElement("", "", Constants.VERSION_TAG, emptyAttributes);
			writeText(serializer, "1");//Версия формата сообщения, передается значение «1».
			serializer.endElement("", "", Constants.VERSION_TAG);
			//END Version

			//Если пришел parentId, то указываем его
			if (messageHead != null && !StringHelper.isEmpty(messageHead.getParentMessageId()))
			{
				//START ParentId
				serializer.startElement("", "", Constants.PARENT_ID_TAG, emptyAttributes);

				//MessageId
				serializer.startElement("", "", Constants.MESSAGE_ID_TAG, emptyAttributes);
				writeText(serializer, messageHead.getParentMessageId());
				serializer.endElement("", "", Constants.MESSAGE_ID_TAG);

				//MessageDate
				serializer.startElement("", "", Constants.MESSAGE_DATE_TAG, emptyAttributes);
				writeText(serializer, XMLDatatypeHelper.formatDateWithoutTimeZone(messageDate));
				serializer.endElement("", "", Constants.MESSAGE_DATE_TAG);

				//fromAbonent
				serializer.startElement("", "", Constants.FROM_ABONENT_TAG, emptyAttributes);
				writeText(serializer, Constants.ABONENT_SBOL);
				serializer.endElement("", "", Constants.FROM_ABONENT_TAG);

				serializer.endElement("", "", Constants.PARENT_ID_TAG);
				//END ParentId
			}

			serializer.endElement("", "", Constants.HEAD_TAG);
			// END HEAD

			//START BODY
			serializer.startElement("", "", Constants.BODY_TAG, emptyAttributes);
			// insert document
			NodeList childNodes = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++)
			{
				serializer.serializeElementOnly((Element) childNodes.item(i));
			}
			//END BODY
			serializer.endElement("", "", Constants.BODY_TAG);

			serializer.endElement("", "", messageType);
			serializer.endDocument();

			String text = writer.toString();

			IQWaveMessageData messageData = new IQWaveMessageData(text);
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
