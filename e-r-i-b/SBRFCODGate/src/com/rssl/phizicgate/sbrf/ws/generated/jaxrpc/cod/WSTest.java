package com.rssl.phizicgate.sbrf.ws.generated.jaxrpc.cod;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.cod.CodGateConfig;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;

/**
 * @author Roshka
 * @ created 07.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class WSTest extends TestCase
{
	public void test() throws ServiceException, IOException
	{
		WebBankService service = new WebBankService_Impl();
		WebBankServiceIF serviceIFPort = service.getWebBankServiceIFPort();

		CodGateConfig config = ConfigFactory.getConfig(CodGateConfig.class);

		String endPoint = config.getCodUrl();
		((com.sun.xml.rpc.client.StubBase) serviceIFPort)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, endPoint);

		String request;

		request = getTestMessage();

		System.out.println(request);
		String responce = serviceIFPort.sendMessage(request);
		System.out.println(responce);

		assertNotNull(responce);
	}

	private String getTestMessage()
			throws IOException
	{
		String request;

		InputStream msgStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("dummy_a.xml");

		InputStreamReader streamReader = new InputStreamReader(msgStream);
		BufferedReader bufferedReader = new BufferedReader(streamReader);
		StringBuffer buf = new StringBuffer();

		for (String ln; (ln = bufferedReader.readLine()) != null;)
		{
			buf.append(ln);
			buf.append('\n');
		}

		request = buf.toString();
		return request;
	}
}