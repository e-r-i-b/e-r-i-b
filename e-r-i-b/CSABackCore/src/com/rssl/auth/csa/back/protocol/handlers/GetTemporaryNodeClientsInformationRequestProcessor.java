package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseBuilder;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.client.ClientForMigrationInformation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 29.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запросов на получение списка клиентов, ожидающих миграции
 */

public class GetTemporaryNodeClientsInformationRequestProcessor extends RequestProcessorBase
{
	private static final String REQUEST_TYPE = "getTemporaryNodeClientsInformationRq";
	private static final String RESPONSE_TYPE = "getTemporaryNodeClientsInformationRs";
	private static final String MAX_RESULTS_TAG = "maxResults";
	private static final String FIRST_RESULT_TAG = "firstResult";

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		Element rootElement = body.getDocumentElement();
		String fio = XmlHelper.getSimpleElementValue(rootElement, Constants.FIO_TAG);
		String document = XmlHelper.getSimpleElementValue(rootElement, Constants.PASSPORT_TAG);
		String birthdayValue = XmlHelper.getSimpleElementValue(rootElement, Constants.BIRTHDATE_TAG);
		String creationType = XmlHelper.getSimpleElementValue(rootElement, Constants.CREATION_TYPE_TAG);
		String agreementNumber = XmlHelper.getSimpleElementValue(rootElement, Constants.AGREEMENT_NUMBER_TAG);
		Calendar birthday = null;
		if (StringHelper.isNotEmpty(birthdayValue))
			birthday = XMLDatatypeHelper.parseDateTime(birthdayValue);
		final List<String> tbList = new ArrayList<String>();
		XmlHelper.foreach(rootElement, Constants.TB_LIST_TAG + "/" + Constants.TB_TAG, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				tbList.add(XmlHelper.getElementText(element));
			}
		});
		Long nodeId = Long.valueOf(XmlHelper.getSimpleElementValue(rootElement, Constants.NODE_ID_TAG));
		int maxResults = Integer.valueOf(XmlHelper.getSimpleElementValue(rootElement, MAX_RESULTS_TAG));
		int firstResult = Integer.valueOf(XmlHelper.getSimpleElementValue(rootElement, FIRST_RESULT_TAG));
		List<ClientForMigrationInformation> clients = ClientForMigrationInformation.findLike(fio, document, birthday, creationType, agreementNumber, tbList, nodeId, maxResults, firstResult);
		return buildResponse(clients);
	}

	private ResponseBuilder buildResponse(List<ClientForMigrationInformation> clients) throws SAXException
	{
		ResponseBuilderHelper builder = getSuccessResponseBuilder();
		builder.openTag(Constants.CLIENTS_TAG);
		for (ClientForMigrationInformation client : clients)
		{
			builder.openTag(Constants.CLIENT_INFO_TAG)
					.addParameter(Constants.EXTERNAL_ID_PARAM_NAME, client.getId())
					.addParameter(Constants.FIRSTNAME_TAG, client.getFirstname())
					.addParameter(Constants.SURNAME_TAG, client.getSurname())
					.addParameterIfNotEmpty(Constants.PATRNAME_TAG, client.getPatronymic())
					.addParameter(Constants.BIRTHDATE_TAG, client.getBirthday())
					.addParameter(Constants.PASSPORT_TAG, client.getDocument())
					.addParameter(Constants.TB_TAG, client.getTb())
					.addParameter(Constants.AGREEMENT_NUMBER_TAG, client.getAgreementNumber())
					.addParameter(Constants.CREATION_TYPE_TAG, StringHelper.getNullIfNull(client.getCreationType()))
			.closeTag();
		}
		builder.closeTag();
		return builder.end();
	}
}
