package com.rssl.phizicgate.rsV51.demand;

import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 13.04.2007
 * Time: 16:47:02
 * To change this template use File | Settings | File Templates.
 */
public final class ApplicationKeyGenerator
{
	public static String getApplicationKey(Long applicationKind) throws GateException, GateLogicException
	{
       WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
	   GateMessage message = service.createRequest("getApplicationKey_q");
	   message.addParameter("appKind", applicationKind.toString());
	   Document document = service.sendOnlineMessage(message, null);
 	   NodeList list = document.getDocumentElement().getChildNodes();
       String applicationKey = "";

		if (list.getLength() > 0)
		{
		   Element paramElem = (Element) list.item(0);
		   applicationKey = paramElem.getTextContent();
		}

		return applicationKey;
	}
}
