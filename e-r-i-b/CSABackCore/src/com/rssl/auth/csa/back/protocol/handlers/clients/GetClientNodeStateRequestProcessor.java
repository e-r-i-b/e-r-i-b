package com.rssl.auth.csa.back.protocol.handlers.clients;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.protocol.handlers.ResponseBuilderHelper;
import com.rssl.auth.csa.back.servises.client.ClientForMigrationInformation;
import org.w3c.dom.Document;

/**
 * @author akrenev
 * @ created 30.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запросов на получение состояния клиента
 */

public class GetClientNodeStateRequestProcessor extends RequestProcessorBase
{
	private static final String REQUEST_TYPE = "getClientNodeStateRq";
	private static final String RESPONSE_TYPE = "getClientNodeStateRs";

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
		Document document = requestInfo.getBody();
		CSAUserInfo userInfo = fillUserInfo(document.getDocumentElement());

		String status = ClientForMigrationInformation.getState(userInfo);

		ResponseBuilderHelper builder = getSuccessResponseBuilder();
		builder.addParameter(Constants.PROFILE_NODE_STATE_TAG, status);

		return builder.end();
	}
}
