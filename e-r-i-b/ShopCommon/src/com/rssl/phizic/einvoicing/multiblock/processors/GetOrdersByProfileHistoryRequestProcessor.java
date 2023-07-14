package com.rssl.phizic.einvoicing.multiblock.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseBuilder;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.einvoicing.ShopProfile;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.rssl.phizic.einvoicing.multiblock.processors.Constants.*;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
public class GetOrdersByProfileHistoryRequestProcessor extends EInvoicingRequestProcessorBase
{
	protected String getResponceType()
	{
		return "GetOrdersByProfileHistoryResponce";
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element root = requestInfo.getBody().getDocumentElement();

		Element profilesElement = XmlHelper.selectSingleNode(root, PROFILES_TAG);
		final List<ShopProfile> profiles = new ArrayList<ShopProfile>();
		XmlHelper.foreach(profilesElement, PROFILE_TAG, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				profiles.add(ResponseBuilderHelper.parseProfile(element));
			}
		});

		Calendar dateFrom = ResponseBuilderHelper.getDateValue(root, DATE_FROM_TAG);
		Calendar dateTo = ResponseBuilderHelper.getDateValue(root, DATE_TO_TAG);
		Calendar dateDelayedTo = ResponseBuilderHelper.getDateValue(root, DATE_DELAYED_TO_TAG);
		BigDecimal amountFrom = ResponseBuilderHelper.getBigDecimalValue(root, AMOUNT_FROM_TAG);
		BigDecimal amountTo = ResponseBuilderHelper.getBigDecimalValue(root, AMOUNT_TO_TAG);
		String currency = ResponseBuilderHelper.getElementValue(root, CURRENCY_TAG);

		Element statesElement = XmlHelper.selectSingleNode(root, STATES_TAG);
		final List<OrderState> states = new ArrayList<OrderState>();
		XmlHelper.foreach(statesElement, STATE_TAG, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				states.add(OrderState.valueOf(XmlHelper.getElementText(element)));
			}
		});

		List<ShopOrder> orders = service.getOrdersByProfileHistory(profiles, dateFrom, dateTo, dateDelayedTo, amountFrom, amountTo, currency, states.toArray(new OrderState[states.size()]));
		ResponseBuilder responseBuilder = getSuccessResponseBuilder();
		responseBuilder.openTag(ORDERS_TAG);
		if (CollectionUtils.isNotEmpty(orders))
		{
			for (ShopOrder order : orders)
				ResponseBuilderHelper.addOrder(order, responseBuilder);
		}
		responseBuilder.closeTag();
		return responseBuilder.end().getResponceInfo();
	}
}
