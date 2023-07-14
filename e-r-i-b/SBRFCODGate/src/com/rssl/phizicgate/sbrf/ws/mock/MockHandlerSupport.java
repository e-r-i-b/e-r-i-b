package com.rssl.phizicgate.sbrf.ws.mock;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizicgate.sbrf.ws.WebBankServiceFacadeImpl;
import org.w3c.dom.Document;

/**
 * @author Evgrafov
 * @ created 18.09.2006
 * @ $Author: gladishev $
 * @ $Revision: 46026 $
 */

public abstract class MockHandlerSupport
{
	private XMLRequestValidator validator=null;

	public Document makeRequest(Document body, MessageInfo messageInfo, String parentMessageId) throws GateException
	{
		try
		{
			return makeMockRequest(body, parentMessageId);
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

	public void validate(Document message,  MessageInfo messageInfo) throws GateException
	{
		if( validator==null )
			validator = new XMLRequestValidator();

		if( messageInfo.getEndpoint().equals(WebBankServiceFacadeImpl.ENDPOINT_COD))
			validator.validate( message );
	}

	protected abstract Document makeMockRequest(Document message, String parentMessageId) throws Exception;
}