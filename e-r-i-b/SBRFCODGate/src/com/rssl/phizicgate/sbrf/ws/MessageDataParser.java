package com.rssl.phizicgate.sbrf.ws;

import com.rssl.phizgate.ext.sbrf.common.messaging.CODFormatResponseHandler;
import com.rssl.phizic.gate.exceptions.GateException;
import org.xml.sax.InputSource;

import java.io.StringReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Roshka
 * @ created 25.10.2006
 * @ $Author$
 * @ $Revision$
 */

public class MessageDataParser
{
	public static CODMessageData parse( String messageXml, CODFormatResponseHandler handler ) throws GateException
	{
		CODMessageData messageData = new CODMessageData();

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser;
		StringReader characterStream = null;

		try
		{
			saxParser = factory.newSAXParser();

			messageData.setBody( messageXml );
			characterStream = new StringReader(messageData.getBody());
			InputSource is = new InputSource(characterStream);

			saxParser.parse(is, handler);

			messageData.setId(handler.getMessageId());
			messageData.setDate(handler.getMessageDate());
			messageData.setAbonent(handler.getFromAbonent());
			messageData.setParentFromAbonent(handler.getParentFromAbonent());
			messageData.setParentMessageDate(handler.getParentMessageDate());
			messageData.setParentMessageId(handler.getParentMessageId());
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		finally
		{
			if (characterStream != null)
				characterStream.close();
		}

		return messageData;
	}
}