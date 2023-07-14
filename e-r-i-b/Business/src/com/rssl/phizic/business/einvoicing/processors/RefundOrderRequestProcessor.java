package com.rssl.phizic.business.einvoicing.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.handlers.RequestProcessorBase;
import com.rssl.phizic.business.einvoicing.ShopWithdrawDocumentExecutor;
import com.rssl.phizic.gate.documents.WithdrawMode;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;
import com.rssl.phizgate.einvoicing.Constants;
import java.math.BigDecimal;

/**
 * Обработчик запросов на возврат товаров и отмену платежа интернет-заказа.
 *
 * @author bogdanov
 * @ created 21.02.14
 * @ $Author$
 * @ $Revision$
 */

public class RefundOrderRequestProcessor extends RequestProcessorBase
{
	@Override
	protected String getResponceType()
	{
		return "RefundGoodsResponce";
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element element = requestInfo.getBody().getDocumentElement();
		String orderUUID = XmlHelper.getSimpleElementValue(element, Constants.ORDER_UUID_TAG);
		String refundUUID = XmlHelper.getSimpleElementValue(element, Constants.REFUND_UUID_TAG);
		BigDecimal amount = new BigDecimal(XmlHelper.getElementValueByPath(element, Constants.AMOUNT_TAG));
		String currency = XmlHelper.getElementValueByPath(element, Constants.CURRENCY_TAG);
		String paymentType = XmlHelper.getElementValueByPath(element, Constants.PAYMENT_TYPE_TAG);
		String returnGood = XmlHelper.getElementValueByPath(element, Constants.RETURN_GOODS_TAG);

		ShopWithdrawDocumentExecutor.getIt().withdraw(amount, currency, orderUUID, refundUUID, paymentType.equals("FULL") ? WithdrawMode.Full : WithdrawMode.Partial, returnGood);

		return getSuccessResponseBuilder().end().getResponceInfo();
	}
}
