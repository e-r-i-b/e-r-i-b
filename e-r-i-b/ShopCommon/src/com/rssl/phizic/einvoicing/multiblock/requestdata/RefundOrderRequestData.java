package com.rssl.phizic.einvoicing.multiblock.requestdata;

import com.rssl.phizgate.einvoicing.Constants;
import com.rssl.phizgate.messaging.internalws.client.RequestDataBase;
import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import com.rssl.phizic.gate.einvoicing.RecallType;
import org.w3c.dom.Document;

import java.math.BigDecimal;

/**
 * @author bogdanov
 * @ created 21.02.14
 * @ $Author$
 * @ $Revision$
 */

public class RefundOrderRequestData extends RequestDataBase
{
	private final BigDecimal amount;
	private final String currencyCode;
	private final String refundUuid;
	private final String uuid;
	private final RecallType recallType;
	private final String returnGoods;

	public RefundOrderRequestData(BigDecimal amount, String currencyCode, String refundUuid, String uuid, RecallType recallType, String returnGoods)
	{
		this.amount = amount;
		this.currencyCode = currencyCode;
		this.refundUuid = refundUuid;
		this.uuid = uuid;
		this.recallType = recallType;
		this.returnGoods = returnGoods;
	}

	public String getName()
	{
		return "RefundGoodsRequest";
	}

	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();
		builder.addParameter(Constants.ORDER_UUID_TAG, uuid);
		builder.addParameter(Constants.REFUND_UUID_TAG, refundUuid);
		builder.addParameter(Constants.AMOUNT_TAG, amount.toString());
		builder.addParameter(Constants.CURRENCY_TAG, currencyCode);
		builder.addParameter(Constants.PAYMENT_TYPE_TAG, recallType.name());
		builder.addParameter(Constants.RETURN_GOODS_TAG, returnGoods);

		return builder.closeTag().toDocument();
	}
}
