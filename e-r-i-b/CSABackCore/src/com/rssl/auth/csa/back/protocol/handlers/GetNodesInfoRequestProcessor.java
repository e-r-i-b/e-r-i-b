package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.nodes.Node;

/**
 * @author gladishev
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на получение данных о блоках
 */
public class GetNodesInfoRequestProcessor extends RequestProcessorBase
{
	private static final String REQUEST_TYPE = "getNodesInfoRq";
	private static final String RESPONCE_TYPE = "getNodesInfoRs";

	@Override
	protected String getResponceType()
	{
		return RESPONCE_TYPE;
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
		return getSuccessResponseBuilder().addNodesInfo(Node.getAll()).end();
	}
}
