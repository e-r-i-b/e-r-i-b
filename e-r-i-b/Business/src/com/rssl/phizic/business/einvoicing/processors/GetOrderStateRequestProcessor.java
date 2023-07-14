package com.rssl.phizic.business.einvoicing.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseBuilder;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.handlers.RequestProcessorBase;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.gate.einvoicing.OrderStateInfo;
import com.rssl.phizic.utils.xml.XmlHelper;

import static com.rssl.phizic.business.einvoicing.Constants.UTRRNO_TAG;
import static com.rssl.phizic.business.einvoicing.Constants.UUID_TAG;
import static com.rssl.phizic.business.einvoicing.Constants.STATE_TAG;

/**
 * @author gladishev
 * @ created 12.03.2014
 * @ $Author$
 * @ $Revision$
 */

public class GetOrderStateRequestProcessor extends RequestProcessorBase
{
	@Override
	protected String getResponceType()
	{
		return "GetOrderStateResponce";
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		String orderUuid = XmlHelper.getSimpleElementValue(requestInfo.getBody().getDocumentElement(), UUID_TAG);
		OrderStateInfo info = ShopHelper.getOrderStateByDocument(orderUuid);
		ResponseBuilder successResponseBuilder = getSuccessResponseBuilder();
		successResponseBuilder.addParameter(STATE_TAG, info.getState().name());
		successResponseBuilder.addParameterIfNotEmpty(UTRRNO_TAG, info.getUtrrno());
		return successResponseBuilder.end().getResponceInfo();
	}
}
