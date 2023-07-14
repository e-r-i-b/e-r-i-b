package com.rssl.auth.csa.back.protocol.handlers.clients;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseBuilder;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.protocol.handlers.ResponseBuilderHelper;
import com.rssl.auth.csa.back.servises.client.ClientForMigrationInformation;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * @author akrenev
 * @ created 30.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запросов на получение количества клиентов, ожидающих миграции
 */

public class GetTemporaryNodeClientsCountRequestProcessor extends RequestProcessorBase
{
	private static final String REQUEST_TYPE = "getTemporaryNodeClientsCountRq";
	private static final String RESPONSE_TYPE = "getTemporaryNodeClientsCountRs";

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
		Long nodeId = Long.valueOf(XmlHelper.getSimpleElementValue(rootElement, Constants.NODE_ID_TAG));
		return buildResponse(ClientForMigrationInformation.getCount(nodeId));
	}

	private ResponseBuilder buildResponse(Long count) throws SAXException
	{
		ResponseBuilderHelper builder = getSuccessResponseBuilder();
		builder.addParameter(Constants.COUNT_NODE_NAME, count);
		return builder.end();
	}
}
