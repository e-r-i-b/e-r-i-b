package com.rssl.phizicgate.sbrf.ws.depocod;

import com.rssl.phizgate.common.messaging.ByteArrayMessageData;
import com.rssl.phizgate.common.payments.PaymentCompositeId;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.MessageGenerator;
import com.rssl.phizic.gate.messaging.MessageHead;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XMLElementAttributes;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.sbrf.ws.CODMessageData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Генератор сообщений для DepoCOD
 * @author gladishev
 * @ created 17.04.2014
 * @ $Author$
 * @ $Revision$
 */

public class DepoCodMessageGenerator extends MessageGenerator
{
	private static final Attributes MESSAGE_ATTRIBUTES = new XMLElementAttributes().add("xmlns", "XMLDepoCOD");
	private static final String ENCODING = "Windows-1251";
	private static final String SYSTEM_ID = "BP_ERIB";

	private static final String SUBSYSTEM_TAG = "SubSystem";
	private static final String OPERDATE_TAG = "OperDate";
	private static final String PARENT_TAG = "message";
	private static final String LOGIN_TAG = "Login";
	private static final String USER_TAG = "User";
	private static final String UUID_TAG = "UUID";

	public MessageData buildMessage(Document document, MessageInfo info, MessageHead messageHead) throws GateException
	{
		try
		{
			StringWriter    writer = new StringWriter();
			InnerSerializer serializer = new InnerSerializer(writer);

			serializer.startDocument();
			serializer.startElement(PARENT_TAG, MESSAGE_ATTRIBUTES);
			serializer.startElement(info.getName());

			String messageId = null;

			if(messageHead.getMessageId() != null)
			{
				PaymentCompositeId compositeId = getCompositeId(messageHead.getMessageId());
				messageId = compositeId.getMessageId();
				// User
				serializer.startElement(USER_TAG);
				serializer.addElement(SUBSYSTEM_TAG, SYSTEM_ID);
				serializer.addElement(LOGIN_TAG, SYSTEM_ID);
				serializer.addElement(OPERDATE_TAG, compositeId.getMessageDate());
				serializer.addElement(UUID_TAG, messageId);
				serializer.endElement(USER_TAG);

				NodeList childNodes = document.getDocumentElement().getChildNodes();
				for (int i=0; i<childNodes.getLength(); i++)
					serializer.serializeElementOnly((Element) childNodes.item(i));
			}
			else if (messageHead.getParentMessageId() != null)
			{
				PaymentCompositeId compositeId = getCompositeId(messageHead.getParentMessageId());
				messageId = compositeId.getMessageId();
				serializer.addElement(OPERDATE_TAG, compositeId.getMessageDate());
				serializer.addElement(UUID_TAG, messageId);
			}
			else throw new GateException("Не указан идентификатор сообщения для DepoCOD");

			serializer.endElement(info.getName());
			serializer.endElement(PARENT_TAG);
			serializer.endDocument();

			String text = writer.toString();

			ByteArrayMessageData messageData = new ByteArrayMessageData(text.getBytes(ENCODING));
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

	private PaymentCompositeId getCompositeId(String externalId)
	{
		return new PaymentCompositeId(externalId, 3);
	}

}
