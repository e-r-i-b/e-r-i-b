package com.rssl.phizicgate.sofia.messaging.mock;

import org.w3c.dom.Document;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;

/**
 * @author gladishev
 * @ created 07.04.2010
 * @ $Author$
 * @ $Revision$
 */
public interface MockQHandler
{
	Document makeRequest(MessageData messageData, MessageInfo messageInfo) throws Exception;
}
