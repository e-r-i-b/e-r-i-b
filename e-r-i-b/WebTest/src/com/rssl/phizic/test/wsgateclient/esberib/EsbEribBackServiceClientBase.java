package com.rssl.phizic.test.wsgateclient.esberib;

import com.rssl.phizic.test.wsgateclient.esberib.generated.*;
import org.apache.axis.client.Stub;

/**
 * @author osminin
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class EsbEribBackServiceClientBase
{
	public String sendRequest(String request, String url)
	{
		EsbEribBackService_ServiceLocator locator = new EsbEribBackService_ServiceLocator();
		try
		{
			EsbEribBackService_BindingStub stub = (EsbEribBackService_BindingStub) locator.getEsbEribBackService();
			stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
			return makeResponse(stub.doIFX(getParameters(request)));
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
	}

	public abstract String createDefaultRequest();

	protected abstract IFXRq_Type getParameters(String parameters) throws Exception;

	protected abstract String makeResponse(IFXRs_Type result);
}
