package com.rssl.phizicgate.rsretailV6r20.test;

import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.deposit.DepositOpeningClaim;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizicgate.rsretailV6r20.senders.ExternalKeyCreator;
import com.rssl.phizicgate.rsretailV6r20.job.CarryPaymentsJob;
import com.rssl.phizicgate.rsretailV6r20.junit.RSRetailV6r20GateTestCaseBase;
import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.util.Map;
import java.util.HashMap;

/**
 * @author emakarov
 * @ created 14.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class JobTest extends RSRetailV6r20GateTestCaseBase
{
	public void testGettingReferenc() throws Exception
	{
		//key:0000290119111318000220002500, kind:1
		String applicationKind = "1";
		String applicationKey = "0000290119111318000220002500";

		ExternalKeyCreator externalKeyCreator = new ExternalKeyCreator(applicationKind, applicationKey);

		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		UpdateDocumentService updateService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		SynchronizableDocument synchronizableDocument = updateService.find(externalKeyCreator.createId());
		Map<String, Object> additionalFields = new HashMap<String, Object>();
		if (synchronizableDocument instanceof DepositOpeningClaim)
		// если количество if-ов будет расти, то реализовать по-другому
		{
			GateMessage gateMessage = service.createRequest("getReferencFromDocument_q");
			gateMessage.addParameter("applicationKind", applicationKind);
			gateMessage.addParameter("applicationKey", applicationKey);

			Document messageResponse = service.sendOnlineMessage(gateMessage, null);
			NodeList nodeList = XmlHelper.selectNodeList(messageResponse.getDocumentElement(), "response");
			Element element = (Element)nodeList.item(0);

			String errorCode = XmlHelper.selectSingleNode(element, "errorCode").getTextContent();
			if (CarryPaymentsJob.OK_ERROR_CODE.equals(errorCode))
			{
				((DepositOpeningClaim)synchronizableDocument).setAccount(XmlHelper.selectSingleNode(element, "accountReferenc").getTextContent());
			}
			else
			{
				String errorText = XmlHelper.selectSingleNode(element, "errorText").getTextContent();
				additionalFields.put("Error", errorText);
				System.out.println(errorText);
			}
		}
		updateService.update(synchronizableDocument, new DocumentCommand(DocumentEvent.EXECUTE, additionalFields));
	}
}
