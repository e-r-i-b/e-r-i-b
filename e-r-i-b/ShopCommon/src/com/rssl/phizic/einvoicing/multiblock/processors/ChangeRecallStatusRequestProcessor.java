package com.rssl.phizic.einvoicing.multiblock.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizic.einvoicing.exceptions.OrderNotFoundException;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.gate.einvoicing.RecallState;
import com.rssl.phizic.gate.einvoicing.RecallType;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Element;

import static com.rssl.phizic.einvoicing.multiblock.processors.Constants.*;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ChangeRecallStatusRequestProcessor extends EInvoicingRequestProcessorBase
{
	protected String getResponceType()
	{
		return "ChangeRecallStatusResponce";
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element documentElement = requestInfo.getBody().getDocumentElement();
		String recallUUID = getOrderUUID(documentElement);
		String state = ResponseBuilderHelper.getElementValue(documentElement, STATE_TAG);
		String recallType = ResponseBuilderHelper.getElementValue(documentElement, PAYMENT_TYPE_TAG);
		String utrrno = ResponseBuilderHelper.getElementValue(documentElement, UTRRNO_TAG);

		service.changeRecallStatus(recallUUID,
				StringHelper.isNotEmpty(state) ? RecallState.valueOf(state) : null,
				utrrno,
				StringHelper.isNotEmpty(recallType) ? RecallType.valueOf(recallType) : null);
		return getSuccessResponseBuilder().end().getResponceInfo();
	}
}
