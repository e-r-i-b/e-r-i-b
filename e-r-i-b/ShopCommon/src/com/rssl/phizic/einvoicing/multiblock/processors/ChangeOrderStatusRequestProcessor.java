package com.rssl.phizic.einvoicing.multiblock.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizic.einvoicing.exceptions.OrderNotFoundException;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Element;

import java.util.Calendar;

import static com.rssl.phizic.einvoicing.multiblock.processors.Constants.*;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ChangeOrderStatusRequestProcessor extends EInvoicingRequestProcessorBase
{
	protected String getResponceType()
	{
		return "ChangeOrderStatusResponce";
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element documentElement = requestInfo.getBody().getDocumentElement();
		String orderUUID = getOrderUUID(documentElement);
		ShopOrder order = service.getOrder(orderUUID);
		if (order == null)
			throw new OrderNotFoundException();

		String state = ResponseBuilderHelper.getElementValue(documentElement, STATE_TAG);
		String nodeId = ResponseBuilderHelper.getElementValue(documentElement, NODE_ID_TAG);
		String paidBy = ResponseBuilderHelper.getElementValue(documentElement, PAYD_BY_TAG);
		String utrrno = ResponseBuilderHelper.getElementValue(documentElement, UTRRNO_TAG);
		String stringDelayDate = ResponseBuilderHelper.getElementValue(documentElement, DELAY_DATE_TAG);
		Calendar delayDate = null;
		if (stringDelayDate != null)
		{
			delayDate =  DateHelper.fromDMYDateToDate(stringDelayDate);
		}
		service.changeOrderStatus(orderUUID,
								StringHelper.isNotEmpty(state) ? OrderState.valueOf(state) : null,
								StringHelper.isNotEmpty(nodeId) ? Long.valueOf(nodeId) : null,
								utrrno,
								paidBy,
								delayDate);
		return getSuccessResponseBuilder().end().getResponceInfo();
	}
}
