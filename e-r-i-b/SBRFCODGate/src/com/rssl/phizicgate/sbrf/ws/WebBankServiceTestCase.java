/**
 * WebBankServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.sbrf.ws;

import com.rssl.phizicgate.sbrf.ws.generated.cod.WebBankServiceIFBindingStub;
import com.rssl.phizicgate.sbrf.ws.generated.cod.WebBankServiceLocator;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import javax.xml.rpc.ServiceException;

public class WebBankServiceTestCase extends TestCase
{
	private static final String WEB_SERVICE_URL = "http://localhost/WebService1/WebBankService.asmx";

	public void manualWebBankServiceIFPortSendMessage() throws Exception
	{
		WebBankServiceIFBindingStub binding;
		try
		{
			WebBankServiceLocator serviceLocator = new WebBankServiceLocator();
			serviceLocator.setWebBankServiceIFPortEndpointAddress(WEB_SERVICE_URL);

			binding = (WebBankServiceIFBindingStub) serviceLocator.getWebBankServiceIFPort();
		}
		catch (ServiceException jre)
		{
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
			throw new AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
		}
		assertNotNull("binding is null", binding);

		// Test operation
		String value = "<test/>";
		value = binding.sendMessage(value);
		// TBD - validate results
	}

	public void test()
	{

	}

}
