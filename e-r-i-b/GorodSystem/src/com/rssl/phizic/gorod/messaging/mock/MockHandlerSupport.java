package com.rssl.phizic.gorod.messaging.mock;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import org.w3c.dom.Document;

/**
 * @author Evgrafov
 * @ created 18.09.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3720 $
 */

public abstract class MockHandlerSupport
{

	public Document makeRequest(Document body, MessageInfo messageInfo) throws GateException
	{
		try
		{
			return makeMockRequest(body);
		}
		catch(GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	protected abstract Document makeMockRequest(Document message) throws Exception;
}
