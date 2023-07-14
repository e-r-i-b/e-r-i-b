package com.rssl.phizic.business.einvoicing.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseBuilder;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.handlers.RequestProcessorBase;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.gate.einvoicing.OrderStateInfo;
import com.rssl.phizic.gate.einvoicing.RecallStateInfo;
import com.rssl.phizic.gate.einvoicing.RecallType;
import com.rssl.phizic.utils.xml.XmlHelper;

import static com.rssl.phizic.business.einvoicing.Constants.*;

/**
 * @author gladishev
 * @ created 12.03.2014
 * @ $Author$
 * @ $Revision$
 */

public class GetRecallStateRequestProcessor extends RequestProcessorBase
{
	@Override
	protected String getResponceType()
	{
		return "GetRecallStateResponce";
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		String orderUuid = XmlHelper.getSimpleElementValue(requestInfo.getBody().getDocumentElement(), UUID_TAG);
		String recallUuid = XmlHelper.getSimpleElementValue(requestInfo.getBody().getDocumentElement(), RECALL_UUID_TAG);
		String recallType = XmlHelper.getSimpleElementValue(requestInfo.getBody().getDocumentElement(), RECALL_TYPE_TAG);
		RecallStateInfo info = ShopHelper.get().getRecallStateByDocument(orderUuid, recallUuid, RecallType.valueOf(recallType));
		ResponseBuilder successResponseBuilder = getSuccessResponseBuilder();
		successResponseBuilder.addParameter(STATE_TAG, info.getState().name());
		successResponseBuilder.addParameterIfNotEmpty(UTRRNO_TAG, info.getUtrrno());
		return successResponseBuilder.end().getResponceInfo();
	}
}
