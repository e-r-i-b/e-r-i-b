package com.rssl.phizic.test.web.monitoring.fraud.support;

import com.rssl.phizic.test.webgate.rsa.integration.ws.TransportProvider;
import com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.Response_Type;
import com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.StatusHeader_Type;
import com.rssl.phizic.test.webgate.rsa.integration.ws.control.Request_TypeBuilder;

/**
 * @author khudyakov
 * @ created 10.06.15
 * @ $Author$
 * @ $Revision$
 */
public class SendOperation
{
	private String request;
	private String response;


	public SendOperation(String request)
	{
		this.request = request;
	}

	public void send() throws Exception
	{
		Response_Type response_type = TransportProvider.send(new Request_TypeBuilder(request).build());

		StatusHeader_Type statusHeader_type = response_type.getStatusHeader();
		if (statusHeader_type != null)
		{
			response = statusHeader_type.getStatusCode().toString();
		}
	}

	public String getResponse()
	{
		return response;
	}
}
