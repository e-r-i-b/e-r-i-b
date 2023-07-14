package com.rssl.phizicgate.enisey.messaging.mock;

import org.w3c.dom.Document;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author mihaylov
 * @ created 12.04.2010
 * @ $Author$
 * @ $Revision$
 */

public interface MockQHandler
{
	Document makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException;
}
