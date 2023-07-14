package com.rssl.phizgate.messaging;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageGenerator;
import com.rssl.phizic.gate.messaging.MessageHead;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Evgrafov
 * @ created 09.03.2007
 * @ $Author: omeliyanchuk $
 * @ $Revision: 6040 $
 */

public abstract class RetailMessageGeneratorBase extends MessageGenerator
{
	private static final String RETAIL_DOCUMENT_SCHEME = "com.rssl.iccs.retail.document.scheme";

	private void validate(Document document) throws GateException
	{
		RetailDocumentValidator validator = new RetailDocumentValidator();
		if (!StringHelper.isEmpty(ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(RETAIL_DOCUMENT_SCHEME)))
		{
			validator.validate(document);
		}
	}

	public RetailMessageData buildMessage(Document document, MessageInfo info, MessageHead messageHead) throws GateException
	{
		validate(document);
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			InnerSerializer serializer = new InnerSerializer(os, "IBM-866");

			Attributes empty = new AttributesImpl();

			String messageId = generateId();

			serializer.startDocument();
			serializer.startElement("", "", "request", empty);

			// ID
			serializer.startElement("", "", "id", empty);
			writeText(serializer, messageId);
			serializer.endElement("", "", "id");

			// insert document
			serializer.serializeElementOnly(document.getDocumentElement());

			serializer.endElement("", "", "request");
			serializer.endDocument();

			RetailMessageData messageData = new RetailMessageData();
			messageData.setId(messageId);
			messageData.setBody(os.toByteArray());

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

	protected abstract String generateId();
}