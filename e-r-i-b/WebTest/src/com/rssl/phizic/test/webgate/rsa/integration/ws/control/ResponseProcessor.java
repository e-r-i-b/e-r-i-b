package com.rssl.phizic.test.webgate.rsa.integration.ws.control;

import com.rssl.phizic.test.webgate.rsa.integration.ws.TransportProvider;
import com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.*;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author khudyakov
 * @ created 16.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ResponseProcessor<RQ extends GenericRequest, RS extends GenericResponse>
{
	private static final Map<Class<? extends GenericRequest>, GenericResponse> responses = new HashMap<Class<? extends GenericRequest>, GenericResponse>();
	static
	{
		responses.put(AnalyzeRequest.class, new AnalyzeResponse());
		responses.put(NotifyRequest.class,  new NotifyResponse());
	}

	private RQ request;

	public ResponseProcessor(RQ request)
	{
		this.request = request;
	}

	public RS process()
	{
		try
		{
			TransportProvider.send(new Request_TypeBuilder(request).build());
			//return buildResponse(200, "Operations were completed successfully");
			return null;
		}
		catch (Exception e)
		{
			//return buildResponse(500, e.getMessage());
			return null;
		}
	}

	private RS buildResponse(int code, String message)
	{
		RS response = (RS) responses.get(request.getClass());
		response.setStatusHeader(new StatusHeader(code, message, code));
		return response;
	}
}
