package com.rssl.phizic.business.einvoicing.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.handlers.RequestProcessorBase;
import com.rssl.phizic.business.einvoicing.InvoiceResponceHelper;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.InvoiceGateBackService;
import com.rssl.phizic.gate.einvoicing.ShopOrder;

/**
 * @author gladishev
 * @ created 28.02.14
 * @ $Author$
 * @ $Revision$
 */
public class SendOrderPaymentRequestProcessor extends RequestProcessorBase
{
	@Override
	protected String getResponceType()
	{
		return "SendOrderPaymentResponce";
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		ShopOrder shopOrder = InvoiceResponceHelper.fillShopOrder(requestInfo.getBody());
		GateSingleton.getFactory().service(InvoiceGateBackService.class).sendOrderPayment(shopOrder);
		return getSuccessResponseBuilder().end().getResponceInfo();
	}
}
