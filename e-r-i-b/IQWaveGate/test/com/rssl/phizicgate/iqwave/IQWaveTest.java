package com.rssl.phizicgate.iqwave;

import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import junit.framework.TestCase;

/**
 * @author gladishev
 * @ created 06.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class IQWaveTest extends TestCase
{
	private WebBankServiceFacade service;

	protected void setUp() throws Exception
	{
		super.setUp();
		GateFactory factory = GateSingleton.getFactory();
		service = factory.service(WebBankServiceFacade.class);
	}

	public void testSend() throws GateException, GateLogicException
	{
		GateMessage message = service.createRequest(Constants.CARDS_TRANSFER_REQUEST);
		Document requestDoc = message.getDocument();
		Element body = XmlHelper.appendSimpleElement(requestDoc.getDocumentElement(), Constants.BODY_TAG);
		//DebitCard Информаци по карте списания
		Element debitCard = XmlHelper.appendSimpleElement(body, "DebitCard");
		XmlHelper.appendSimpleElement(debitCard, "CardNumber", "1234567899012345678");
		XmlHelper.appendSimpleElement(debitCard, "EndDate", "2010-05-06");
		//CurrCode Валюта операции
		XmlHelper.appendSimpleElement(body, "CurrCode", "RUB");
		//CreditCard Номер карты зачисления
		XmlHelper.appendSimpleElement(body, "CreditCard", "987654321987654321");
		//Summa Сумма платежа
		XmlHelper.appendSimpleElement(body, "Summa", "150.99");

		Document response = service.sendOnlineMessage(message, null);
		assertNotNull(response);
	}
}
