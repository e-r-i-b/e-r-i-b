package com.rssl.phizicgate.bars.messaging;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.jmx.BarsConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.StringWriter;

/**
 * @author osminin
 * @ created 31.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class BarsMessageGenerator
{
	private static final String DOC_TYPE = "001";

	public MessageData buildMessage(String personalAcc, String bic, String tb) throws GateException
	{
		try
		{
			StringWriter writer = new StringWriter();
			InnerSerializer serializer = new InnerSerializer(writer);
			BarsConfig config = ConfigFactory.getConfig(BarsConfig.class);

			String barsParticipantCode = config.getBarsPartCode(tb);

			Attributes emptyAttributes = new AttributesImpl();

			serializer.startDocument();
			serializer.startPrefixMapping("","urn:sbrf:xsb:v1.0");
			serializer.startElement("urn:sbrf:xsb:v1.0", "", "XSB", emptyAttributes);

			AttributesImpl edAttributes = new AttributesImpl();
			edAttributes.addAttribute("","","Form","CDATA", "SBRF");
			serializer.startElement("", "", "ED", edAttributes);

			serializer.startElement("", "", "Head", emptyAttributes);
			serializer.startElement("", "", "SBRF", emptyAttributes);

			serializer.startElement("", "", "Sender", emptyAttributes);
			writeText(serializer, barsParticipantCode);
			serializer.endElement("", "", "Sender");

			serializer.startElement("", "", "Receiver", emptyAttributes);
			writeText(serializer, barsParticipantCode);
			serializer.endElement("", "", "Receiver");

			serializer.startElement("", "", "DocType", emptyAttributes);
			writeText(serializer, DOC_TYPE);
			serializer.endElement("", "", "DocType");

			serializer.endElement("", "", "SBRF");
			serializer.endElement("", "", "Head");

			serializer.startElement("", "", "SB001", emptyAttributes);
			serializer.startElement("", "", "Benif", emptyAttributes);

			serializer.startElement("", "", "PersonalAcc", emptyAttributes);
			writeText(serializer, personalAcc);
			serializer.endElement("", "", "PersonalAcc");

			serializer.startElement("", "", "Bank", emptyAttributes);
			serializer.startElement("", "", "BIC", emptyAttributes);
			writeText(serializer, bic);
			serializer.endElement("", "", "BIC");
			serializer.endElement("", "", "Bank");

			serializer.endElement("", "", "Benif");
			serializer.endElement("", "", "SB001");
			serializer.endElement("", "", "ED");
			serializer.endElement("", "", "XSB");
			serializer.endDocument();

			String text = writer.toString();
			BarsMessageData data = new BarsMessageData();
			data.setBody(text);

			return data;
		}
		catch (SAXException e)
		{
			throw new GateException(e);
		}
	}

	private void writeText(InnerSerializer  serializer, String text) throws SAXException
	{
		char[] arr = text.toCharArray();
		serializer.characters(arr, 0, arr.length);
	}
}
