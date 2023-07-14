package com.rssl.phizicgate.sbrf.ws.listener;

import com.rssl.phizicgate.sbrf.ws.MessageDataParser;
import com.rssl.phizicgate.sbrf.ws.CODMessageData;
import com.rssl.phizgate.ext.sbrf.common.messaging.CODFormatResponseHandler;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.configuration.MessagingConfig;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.TransformerException;


/**
 * @author Omeliyanchuk
 * @ created 09.02.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * вспомогательный класс для разбора offline сообщения
 */
public class OfflineMessageParser
{
	private static String MESSAGE_CODE = "code";
	private static String MESSAGE_TEXT = "message";


	public OfflineMessageParser()
	{
	}

	public static InternalMessageInfoContainer processMessage(MessagingConfig messageConfig, String xmlMessage) throws GateException
	{
		CODFormatResponseHandler handler = new CODFormatResponseHandler(messageConfig.avaibleRequests());

		CODMessageData requestMessage = MessageDataParser.parse(xmlMessage, handler);

		InternalMessageInfoContainer container = new InternalMessageInfoContainer();

		Document messDocument = (Document)handler.getBody();
		container.setMessageTag( handler.getResponceTag() );
		container.setMessage(requestMessage);

		if (requestMessage.getParentMessageId() != null)
		{
			container.setLink( requestMessage.getParentMessageId());

		}

		try
		{
			Element  errorCode = XmlHelper.selectSingleNode(messDocument.getDocumentElement(), MESSAGE_CODE);
			if(errorCode != null)
			{
				container.setErrorCode( errorCode.getTextContent() );

				Element  errorReason = XmlHelper.selectSingleNode(messDocument.getDocumentElement(), MESSAGE_TEXT);
				container.setErrorText( errorReason.getTextContent() );
			}

		}
		catch(TransformerException ex)
		{
			throw new GateException(ex);
		}

		return container;
	}
}
