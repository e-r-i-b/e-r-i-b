package com.rssl.phizicgate.enisey.messaging.mock;

import org.w3c.dom.Document;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;

/**
 * @author mihaylov
 * @ created 14.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class RequestBillingAttr implements MockQHandler
{
	public Document makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException
	{
		try
		{
			return XmlHelper.loadDocumentFromResource("com/rssl/phizicgate/enisey/messaging/mock/xml/requestBillingAttr_a.xml");
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
