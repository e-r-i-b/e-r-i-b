package com.rssl.phizicgate.sbrf.ws;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.impl.MessageHeadImpl;
import com.rssl.phizicgate.sbrf.ws.mock.MockWebBankServiceFacade;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author Evgrafov
 * @ created 29.08.2006
 * @ $Author: niculichev $
 * @ $Revision: 38254 $
 */

public class WebBankServiceFacadeTest extends SBRFGateTestCaseBase
{

	public void testMock() throws Exception
	{
		WebBankServiceFacade facade = new MockWebBankServiceFacade(gateFactory);

		testFasade(facade);
	}

	public void manualTestWS() throws Exception
	{
		WebBankServiceFacade facade = new WebBankServiceFacadeImpl(gateFactory);
		testFasade(facade);
	}

	private void testFasade(WebBankServiceFacade facade) throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document request = builder.newDocument();

		Element root = request.createElement("agreementRegistration_q");
		request.appendChild(root);

		Element elem = request.createElement("agreementNumber");
		elem.setTextContent("12312");
		root.appendChild(elem);

		elem = request.createElement("agreementDate");
		elem.setTextContent("2006-08-09+04:00");
		root.appendChild(elem);

		elem = request.createElement("insertionOfService");
		elem.setTextContent("2006-08-09+04:00");
		root.appendChild(elem);

		elem = request.createElement("agreementOwner");
		elem.setTextContent("2342");
		root.appendChild(elem);

		elem = request.createElement("deposit");
		root.appendChild(elem);

		Element nelem = request.createElement("account");
		nelem.setTextContent("23422810434343434343");
		elem.appendChild(nelem);

		elem = request.createElement("owner");
		root.appendChild(elem);

		nelem = request.createElement("id");
		nelem.setTextContent("23422810434343434343");
		elem.appendChild(nelem);

		nelem = request.createElement("fullName");
		nelem.setTextContent("23422810434343434343");
		elem.appendChild(nelem);

		nelem = request.createElement("birthdate");
		nelem.setTextContent("1999-01-21");
		elem.appendChild(nelem);

		nelem = request.createElement("citizenRF");
		nelem.setTextContent("sdfsdfs");
		elem.appendChild(nelem);

		nelem = request.createElement("login");
		nelem.setTextContent("login");
		elem.appendChild(nelem);

		Document response = facade.sendOnlineMessage(request, null);
		assertNotNull(response);

		try
		{
			facade.sendOfflineMessage(request, new MessageHeadImpl(null, null, null, "777L", null, null));
			assertFalse("не тот ответ", true);
		}
		catch (GateException e)
		{
			// должна быть ошибка
			System.out.println("Ошибка. Как и хотели :) " + e.getMessage());
		}
	}

	public  void testValidate() throws Exception
	{

		WebBankServiceFacade facade = new MockWebBankServiceFacade(gateFactory);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);

		DocumentBuilder builder = factory.newDocumentBuilder();
		Document request = builder.newDocument();

		Element root = request.createElement("agreementRegistration_q");
		request.appendChild(root);

		Element elem = request.createElement("agreementNumber");
		elem.setTextContent("12312");
		root.appendChild(elem);

		elem = request.createElement("insertionOfService");
		elem.setTextContent("2006-08-09+04:00");
		root.appendChild(elem);

		try
		{
			facade.sendOnlineMessage(request, null);
			assertFalse("Должен быть exception", true);
		}
		catch(GateException e)
		{
			System.out.println("Ошибка. Как и хотели :) " + e.getMessage());
		}
	}
}

