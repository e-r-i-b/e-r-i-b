package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.connectors.MAPIConnector;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 16.04.15
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на получение информации о наличии мАпи ПРо-версии у клиента
 */
public class GetContainsProMAPIInfoRequestProcessor extends RequestProcessorBase
{
	private static final String RESPONSE_TYPE = "getContainsProMAPIInfoRs";
	private static final String REQUEST_TYPE  = "getContainsProMAPIInfoRq";

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
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document document = requestInfo.getBody();
		CSAUserInfo userInfo = fillUserInfo(document.getDocumentElement());
		boolean containsPro = MAPIConnector.isContainsPRO(userInfo);

		return createResponse(containsPro);
	}

	private ResponseInfo createResponse(boolean containsPro) throws Exception
	{
		return getSuccessResponseBuilder()
				.addParameter(Constants.CONTAINS_PRO_MAPI_TAG, containsPro)
				.end();
	}
}
