package com.rssl.phizicgate.sofia.messaging.mock;

import org.w3c.dom.Document;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.utils.xml.XmlHelper;

/**
 * @author gladishev
 * @ created 07.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class RevokeBillPaymentQHandler implements MockQHandler
{
	public Document makeRequest(MessageData messageData, MessageInfo messageInfo) throws Exception
	{
		return XmlHelper.loadDocumentFromResource("com/rssl/phizicgate/sofia/messaging/xml/revokeBillPaymentA.xml");
	}
}
