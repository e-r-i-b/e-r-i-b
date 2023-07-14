package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.client.ClientInformation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 24.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запросов на получение списка клиентов
 */

public class GetClientsInformationRequestProcessor extends RequestProcessorBase
{
	private static final String REQUEST_TYPE = "getClientsInformationRq";
	private static final String RESPONSE_TYPE = "getClientsInformationRs";
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
		trace("проверяем входные данные");
		Element rootElement = body.getDocumentElement();
		String fio = XmlHelper.getSimpleElementValue(rootElement, Constants.FIO_TAG);
		String document = XmlHelper.getSimpleElementValue(rootElement, Constants.PASSPORT_TAG);
		String birthdayValue = XmlHelper.getSimpleElementValue(rootElement, Constants.BIRTHDATE_TAG);
		String creationType = XmlHelper.getSimpleElementValue(rootElement, Constants.CREATION_TYPE_TAG);
		String agreementNumber = XmlHelper.getSimpleElementValue(rootElement, Constants.AGREEMENT_NUMBER_TAG);
		String phoneNumber = XmlHelper.getSimpleElementValue(rootElement, Constants.PHONE_NUMBER_TAG);
		Calendar birthday = null;
		if (StringHelper.isNotEmpty(birthdayValue))
			birthday = XMLDatatypeHelper.parseDateTime(birthdayValue);
		String login = XmlHelper.getSimpleElementValue(rootElement, Constants.LOGIN_TAG);
		final List<String> tbList = new ArrayList<String>();
		XmlHelper.foreach(rootElement, Constants.TB_LIST_TAG + "/" + Constants.TB_TAG, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				tbList.add(XmlHelper.getElementText(element));
			}
		});
		int maxResults = Integer.valueOf(XmlHelper.getSimpleElementValue(rootElement, MAX_RESULTS_TAG));
		int firstResult = Integer.valueOf(XmlHelper.getSimpleElementValue(rootElement, FIRST_RESULT_TAG));
		return getSuccessResponseBuilder().addClientsInfo(ClientInformation.findLike(fio, document, birthday, login, creationType, agreementNumber, phoneNumber, tbList, maxResults, firstResult)).end();
	}
}
