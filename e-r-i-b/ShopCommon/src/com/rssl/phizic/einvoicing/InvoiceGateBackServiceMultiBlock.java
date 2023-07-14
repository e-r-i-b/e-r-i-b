package com.rssl.phizic.einvoicing;

import com.rssl.phizgate.messaging.internalws.client.InternalServiceSender;
import com.rssl.phizgate.messaging.internalws.client.ResponseData;
import com.rssl.phizic.einvoicing.multiblock.requestdata.*;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.einvoicing.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import static com.rssl.phizic.einvoicing.multiblock.processors.Constants.STATE_TAG;
import static com.rssl.phizic.einvoicing.multiblock.processors.Constants.UTRRNO_TAG;

/**
 * Сервис для взаимодействия EInvoicing с CSAAdmin и ЕРИБ (многоблочная схема).
 *
 * @author bogdanov
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */

public class InvoiceGateBackServiceMultiBlock extends AbstractService implements InvoiceGateBackService
{
	private static final ProviderService providerService = new ProviderService();

	private final ConcurrentHashMap<Long, InternalServiceSender> senders = new ConcurrentHashMap<Long, InternalServiceSender>();

	private InternalServiceSender getSender(ShopOrder order)
	{
		Long nodeId = order.getNodeId();
		if (senders.containsKey(nodeId))
			return senders.get(nodeId);
		
		senders.putIfAbsent(nodeId, new InternalServiceSender(com.rssl.phizic.logging.messaging.System.ERIB.toValue() + "_" + nodeId));
		return senders.get(nodeId);
	}

	public InvoiceGateBackServiceMultiBlock(GateFactory factory)
	{
		super(factory);
	}

	public ShopProvider getActiveProvider(String receiverCode) throws GateException
	{
		try
		{
			return providerService.findProvider(receiverCode);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void sendRefundOrder(ShopOrder shopOrder, BigDecimal amount, String currencyCode, String refundUuid) throws GateException, GateLogicException
	{
		getSender(shopOrder).sendRequest(new RefundOrderRequestData(amount, currencyCode, refundUuid, shopOrder.getUuid(), RecallType.FULL, null));
	}

	public void sendReturnGoods(ShopOrder shopOrder, BigDecimal amount, String currencyCode, String returnGoodsUuid, String returnedGoods) throws GateException, GateLogicException
	{
		getSender(shopOrder).sendRequest(new RefundOrderRequestData(amount, currencyCode, returnGoodsUuid, shopOrder.getUuid(), RecallType.PARTIAL, returnedGoods));
	}

	public void createOrderPayment(ShopOrder order) throws GateException, GateLogicException
	{
		getSender(order).sendRequest(new CreateOrderPaymentRequestData(order));
	}

	public void sendOrderPayment(ShopOrder order) throws GateException, GateLogicException
	{
		getSender(order).sendRequest(new SendOrderPaymentRequestData(order));
	}

	public void sendTickets(ShopOrder order, String ticketInfo) throws GateException, GateLogicException
	{
		getSender(order).sendRequest(new SendTicketsRequestData(order.getUuid(), ticketInfo));
	}

	public OrderStateInfo getOrderState(ShopOrder order) throws GateException, GateLogicException
	{
		ResponseData responseData = getSender(order).sendRequest(new GetOrderStateRequestData(order.getUuid()));
		Element documentElement = responseData.getBody().getDocumentElement();
		return new OrderStateInfo(OrderState.valueOf(XmlHelper.getSimpleElementValue(documentElement, STATE_TAG)), XmlHelper.getSimpleElementValue(documentElement, UTRRNO_TAG));
	}

	public RecallStateInfo getRecallState(ShopRecall recall) throws GateException, GateLogicException
	{
		ShopOrderService service = new ShopOrderServiceImpl(null);
		ResponseData responseData = getSender(service.getOrder(recall.getOrderUuid())).sendRequest(new GetRecallStateRequestData(recall));
		Element documentElement = responseData.getBody().getDocumentElement();
		return new RecallStateInfo(RecallState.valueOf(XmlHelper.getSimpleElementValue(documentElement, STATE_TAG)), XmlHelper.getSimpleElementValue(documentElement, UTRRNO_TAG));
	}
}
