package com.rssl.phizicgate.sbcms.messaging.mock;

import org.w3c.dom.Document;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.sbcms.messaging.CMSMessagingService;

/**
 * @author Egorova
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class MockHandlerSupport
{
	private XMLRequestValidator validator=null;

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

	public void validate(Document message,  MessageInfo messageInfo) throws GateException
	{
		if( validator==null )
			validator = new XMLRequestValidator();

		if( messageInfo.getEndpoint().equals(CMSMessagingService.ENDPOINT_CMS))
			validator.validate( message );
	}

	protected abstract Document makeMockRequest(Document message) throws Exception;
}
