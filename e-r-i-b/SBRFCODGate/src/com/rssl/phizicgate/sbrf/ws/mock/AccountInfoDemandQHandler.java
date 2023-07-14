package com.rssl.phizicgate.sbrf.ws.mock;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import java.util.Random;

/**
 * @author Evgrafov
 * @ created 18.09.2006
 * @ $Author: rtishcheva $
 * @ $Revision: 48011 $
 */

public class AccountInfoDemandQHandler extends MockHandlerSupport
{
	private static final String MOCK_FULL_NAME = "This wouldn't be seen in a real system";
	protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
	{
		Element requestAccount = XmlHelper.selectSingleNode(message.getDocumentElement(), "/accountInfoDemand_q/account");
		Document response = XmlHelper.loadDocumentFromResource("accountInfo.xml");
		Element responseAccount = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/accountInfo_a/account");
		responseAccount.setTextContent(requestAccount.getTextContent());

		Element responseStatus = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/accountInfo_a/status");
		Random rand = new Random();
		int result = rand.nextInt(6);
		if(result<=4)
		{
			responseStatus.setTextContent("OPENED");
		}
		else
		{
			responseStatus.setTextContent("ARRESTED");
		}

		fillClient(response,  requestAccount.getTextContent());
		return response;
	}

	private void fillClient(Document response, String accountNumber) throws Exception
	{
		BackRefClientService backRefClientService = GateSingleton.getFactory().service(BackRefClientService.class);
		try
		{
			/* В случае СКС пытаемся получить loginId из номера счета - см ResponseHelper, метод createMockCard())
			   установка номера СКС:
			   cardAcctId.setAcctId("42301810"+RandomHelper.rand(6, RandomHelper.DIGITS) + login);
			 */
			Client client = backRefClientService.getClientById(Long.parseLong(accountNumber.substring(14)));

			if (client!=null && client.getId()!=null)
				fillClient(response, client);
			else
			{
				// Если клиент не найден, значит это не СКС, заполняем дефолтными данными
				fillClient(response);
			}
		}
		catch (Exception e)
		{
			fillClient(response);
		}
	}

	private void fillClient(Document response, Client client) throws Exception
	{
		Element responseFullname = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/accountInfo_a/depositor/fullName");
		responseFullname.setTextContent(client.getFullName());
		Element responseBirthdate = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/accountInfo_a/depositor/birthdate");
		responseBirthdate.setTextContent(DateHelper.toXMLDateFormat(DateHelper.toDate(client.getBirthDay())));

		ClientDocument document = getDocument(client.getDocuments());
		Element responseDocType = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/accountInfo_a/depositor/identityCard/type");
		String documentType = document.getDocumentType().toString();
		if (document.getDocumentType() == ClientDocumentType.RESIDENTIAL_PERMIT_RF)
			documentType = "RESIDENСЕ_PERMIT_RF";
		responseDocType.setTextContent(documentType);

		Element responseDocTypeName = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/accountInfo_a/depositor/identityCard/typeName");
		responseDocTypeName.setTextContent(document.getDocTypeName());
		Element responseDocSeries = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/accountInfo_a/depositor/identityCard/series");
		responseDocSeries.setTextContent(document.getDocSeries());
		Element responseDocNumber = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/accountInfo_a/depositor/identityCard/number");
		responseDocNumber.setTextContent(document.getDocNumber());
		Element responseDocDateOfIssue = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/accountInfo_a/depositor/identityCard/dateOfIssue");
		responseDocDateOfIssue.setTextContent(DateHelper.toXMLDateFormat(DateHelper.toDate(document.getDocIssueDate())));
		Element responseDocAuthoruty = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/accountInfo_a/depositor/identityCard/authoruty");
		responseDocAuthoruty.setTextContent(document.getDocIssueBy());
	}

	private void fillClient(Document response) throws Exception
	{
		Element responseFullname = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/accountInfo_a/depositor/fullName");
		responseFullname.setTextContent(MOCK_FULL_NAME);
	}

	private ClientDocument getDocument(List<? extends ClientDocument> documentList)
	{
		for(ClientDocument document: documentList)
		{
			if ((!StringHelper.isEmpty(document.getDocSeries())) || (!StringHelper.isEmpty(document.getDocNumber())))
				return document;
		}
		return null;
  	}

}