package com.rssl.phizicgate.wsgate.services.einvoicing;

import com.rssl.phizgate.einvoicing.Constants;
import com.rssl.phizgate.messaging.internalws.client.InternalServiceForMainPagesSender;
import com.rssl.phizgate.messaging.internalws.client.InternalServiceSender;
import com.rssl.phizgate.messaging.internalws.client.ResponseData;
import com.rssl.phizic.business.einvoicing.InvoiceResponceHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.einvoicing.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.wsgate.services.einvoicing.requests.*;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.rssl.phizic.business.einvoicing.Constants.*;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ShopOrderServiceImpl extends AbstractService implements ShopOrderService
{
	private static final InternalServiceSender sender = new InternalServiceSender(com.rssl.phizic.logging.messaging.System.shop.toValue());
	private static final InternalServiceForMainPagesSender senderForMainPages = new InternalServiceForMainPagesSender(com.rssl.phizic.logging.messaging.System.shop.toValue());

	protected ShopOrderServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public GateFactory getFactory()
	{
		return super.getFactory();
	}

	public void linkOrderToClient(String orderUUID, ShopProfile profile) throws GateException, GateLogicException
	{
		sender.sendRequest(new LinkOrderToClientRequestData(orderUUID, profile));
	}

	public ShopOrder getOrder(String orderUUID) throws GateException, GateLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetOrderRequestData(orderUUID));
		return InvoiceResponceHelper.fillShopOrder(responseData.getBody());
	}

	public ShopOrder getOrder(Long id) throws GateException, GateLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetOrderByIdRequestData(id));
		return InvoiceResponceHelper.fillShopOrder(responseData.getBody());
	}

	public List<ShopOrder> getOrdersByProfileHistory(List<ShopProfile> profiles, Calendar dateFrom, Calendar dateTo, Calendar dateDelayedTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, OrderState... status) throws GateException, GateLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetOrdersByProfileHistoryRequestData(profiles, dateFrom, dateTo, dateDelayedTo, amountFrom, amountTo, currency, null, null, status));
		return processOrdersByProfileHistoryResponse(responseData);
	}

	public List<ShopOrder> getOrdersByProfileHistory(List<ShopProfile> profiles, Calendar dateFrom, Calendar dateTo, Calendar dateDelayedTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, Long limit, Boolean orderByDelayDate, OrderState... status) throws GateException, GateLogicException
	{
		ResponseData responseData = senderForMainPages.sendRequest(new GetOrdersByProfileHistoryRequestData(profiles, dateFrom, dateTo, dateTo, amountFrom, amountTo, currency, limit, orderByDelayDate, status));
		return processOrdersByProfileHistoryResponse(responseData);
	}

	public List<ShopOrder> getOrdersByProfileHistoryForMainPage(List<ShopProfile> profiles, Calendar dateFrom, Calendar dateTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, OrderState... status) throws GateException, GateLogicException
	{
		ResponseData responseData = senderForMainPages.sendRequest(new GetOrdersByProfileHistoryRequestData(profiles, dateFrom, dateTo, dateTo, amountFrom, amountTo, currency, null, null, status));
		return processOrdersByProfileHistoryResponse(responseData);
	}

	private List<ShopOrder> processOrdersByProfileHistoryResponse(ResponseData responseData) throws GateException
	{
		final List<ShopOrder> result = new ArrayList<ShopOrder>();
		try
		{
			XmlHelper.foreach(responseData.getBody().getDocumentElement(), ORDERS_TAG + "/" + ORDER_TAG, new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					result.add(InvoiceResponceHelper.fillShopOrder(element));
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		return result;
	}

	public String getOrderInfo(String orderUuid) throws GateException, GateLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetOrderInfoRequestData(orderUuid));

		try
		{
			String text = XmlHelper.getNodeAsString(responseData.getBody().getDocumentElement(), FIELDS);
			if (!StringHelper.isEmpty(text))
				return text;

			return XmlHelper.getNodeAsString(responseData.getBody().getDocumentElement(), AIRLINE_RESERVATION);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void changeOrderStatus(String orderUUID, OrderState newState, Long nodeId, String utrrno, String paidBy, Calendar delayDate) throws GateException, GateLogicException
	{
		sender.sendRequest(new ChangeOrderStatusRequestData(orderUUID, newState, nodeId, utrrno, paidBy, delayDate));
	}

	public void changeRecallStatus(String recallUUID, RecallState newState, String utrrno, RecallType recallType) throws GateException, GateLogicException
	{
		sender.sendRequest(new ChangeRecallStatusRequestData(recallUUID, newState, utrrno, recallType));
	}

	public String getTicketInfo(String orderUUID) throws GateException, GateLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetTicketInfoRequestData(orderUUID));
		return  XmlHelper.getSimpleElementValue(responseData.getBody().getDocumentElement(), Constants.TICKETS_INFO_TAG);
	}

	public void markViewed(String orderUUID) throws GateException, GateLogicException
	{
		sender.sendRequest(new MarkViewedRequestData(orderUUID));
	}

	private List<FacilitatorProvider> processFacilitatorProviders(ResponseData responseData) throws GateException
	{
		final List<FacilitatorProvider> result = new ArrayList<FacilitatorProvider>();
		try
		{
			XmlHelper.foreach(responseData.getBody().getDocumentElement(), FACILITATORS_TAG + "/" + FACILITATOR_TAG, new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					result.add(InvoiceResponceHelper.fillProvider(element));
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		return result;
	}

	public List<FacilitatorProvider> findEndPointProviderByCode(String facilitatorCode, int firstResult, int maxResult) throws GateException, GateLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetEndPointProvidersByCodeRequestData(facilitatorCode, firstResult, maxResult));
		return processFacilitatorProviders(responseData);
	}

	public List<FacilitatorProvider> findEndPointProviderByName(String name, String inn, int firstResult, int maxResult) throws GateException, GateLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetEndPointProvidersByNameRequestData(name, inn, firstResult, maxResult));
		return processFacilitatorProviders(responseData);
	}

	public FacilitatorProvider getEndPointProvider(long providerId) throws GateLogicException, GateException
	{
		ResponseData responseData = sender.sendRequest(new GetEndPointProviderRequestData(providerId));
		return InvoiceResponceHelper.fillProvider(responseData.getBody().getDocumentElement());
	}

	public void updateEndPointProvider(long providerId, Boolean mcheckoutEnabled, Boolean eInvoicingEnabled, Boolean mbCheckEnabled) throws GateLogicException, GateException
	{
		sender.sendRequest(new UpdateEndPointProviderRequestData(providerId, eInvoicingEnabled, mcheckoutEnabled, mbCheckEnabled));
	}

	public void updateEndPointProviders(String facilitatorCode, Boolean mcheckoutEnabled, Boolean eInvoicingEnabled, Boolean mbCheckEnabled) throws GateLogicException, GateException
	{
		sender.sendRequest(new UpdateEndPointProvidersRequestData(facilitatorCode, eInvoicingEnabled, mcheckoutEnabled, mbCheckEnabled));
	}
}
