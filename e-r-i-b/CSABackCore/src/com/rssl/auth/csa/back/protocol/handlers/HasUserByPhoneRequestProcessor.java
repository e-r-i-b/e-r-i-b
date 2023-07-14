package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.connectors.ERMBConnector;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.util.Arrays;
import java.util.List;

/**
 * Есть ли пользователи по номерам телефонов.
 *
 * Параметры запроса
 * phones                  Номера телефонов                                              [1]
 *
 * Параметры ответа
 * phones	               Номера телефонов, которые есть в базе                         [1]
 *
 * @author bogdanov
 * @ created 14.10.14
 * @ $Author$
 * @ $Revision$
 */

public class HasUserByPhoneRequestProcessor extends RequestProcessorBase
{
	private static final String RESPONSE_TYPE = "HasUserByPhoneRs";
	private static final String REQUEST_TYPE  = "HasUserByPhoneRq";

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document document = requestInfo.getBody();
		Element element = document.getDocumentElement();
        String phoneNumbers[] = XmlHelper.getSimpleElementValue(element, Constants.PHONES_TAG).split("\\|");
		List<String> srcPhones = Arrays.asList(phoneNumbers);
		List<String> distPhones = ERMBConnector.findDuplicatesPhones(srcPhones);

		StringBuilder phones = new StringBuilder(12);
		for (String phone : distPhones)
		{
		    if (phones.length() != 0)
			    phones.append("|");
		    phones.append(phone);
		}

		return doRequest(phones);
	}

	private ResponseInfo doRequest(StringBuilder phones) throws SAXException
	{
		return getSuccessResponseBuilder()
				.addParameter(Constants.PHONES_TAG, phones.toString())
				.end().getResponceInfo();
	}
}
