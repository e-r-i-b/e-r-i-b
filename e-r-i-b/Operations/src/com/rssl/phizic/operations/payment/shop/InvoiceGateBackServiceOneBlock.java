package com.rssl.phizic.operations.payment.shop;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.einvoicing.ShopWithdrawDocumentExecutor;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.WithdrawMode;
import com.rssl.phizic.gate.einvoicing.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.security.SecurityLogicException;

import java.math.BigDecimal;

/**
 * Сервис для взаимодействия EInvoicing с ERIB (одноблочная схема).
 *
 * @author bogdanov
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */

public class InvoiceGateBackServiceOneBlock extends AbstractService implements InvoiceGateBackService
{
	private static final ServiceProviderService providerService = new ServiceProviderService();

	public InvoiceGateBackServiceOneBlock(GateFactory factory)
	{
		super(factory);
	}

	public ShopProvider getActiveProvider(String receiverCode) throws GateException
	{
		try
		{
			return providerService.getRecipientActivityBySystemName(receiverCode);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public void sendRefundOrder(ShopOrder shopOrder, BigDecimal amount, String currencyCode, String refundUuid) throws GateException, GateLogicException
	{
		try
		{
			ShopWithdrawDocumentExecutor.getIt().withdraw(amount, currencyCode, shopOrder.getUuid(), refundUuid, WithdrawMode.Full, null);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
		catch (BusinessTimeOutException e)
		{
			throw new GateTimeOutException(e);
		}
	}

	public void sendReturnGoods(ShopOrder shopOrder, BigDecimal amount, String currencyCode, String returnGoodsUuid, String returnedGoods) throws GateException, GateLogicException
	{
		try
		{
			ShopWithdrawDocumentExecutor.getIt().withdraw(amount, currencyCode, shopOrder.getUuid(), returnGoodsUuid, WithdrawMode.Partial, returnedGoods);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
		catch (BusinessTimeOutException e)
		{
			throw new GateTimeOutException(e);
		}
	}

	public void createOrderPayment(ShopOrder order) throws GateException, GateLogicException
	{
		try
		{
			OrderMobileCheckoutDocumentExecutor.getIt().createPayment(order);
		}
		catch (BusinessLogicException ble)
		{
			throw new GateLogicException(ble);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void sendOrderPayment(ShopOrder order) throws GateException, GateLogicException
	{
		try
		{
			OrderMobileCheckoutDocumentExecutor.getIt().sendPayment(order);
		}
		catch (SecurityLogicException sle)
		{
			throw new GateLogicException(sle);
		}
		catch (BusinessLogicException ble)
		{
			throw new GateLogicException(ble);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void sendTickets(ShopOrder order, String ticketInfo) throws GateException, GateLogicException
	{
		try
		{
			ShopHelper.get().setTicketsInfo(order.getUuid(), ticketInfo);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new GateLogicException(e);
		}
	}

	public OrderStateInfo getOrderState(ShopOrder order) throws GateException, GateLogicException
	{
		try
		{
			return ShopHelper.getOrderStateByDocument(order.getUuid());
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public RecallStateInfo getRecallState(ShopRecall recall) throws GateException, GateLogicException
	{
		try
		{
			return ShopHelper.get().getRecallStateByDocument(recall.getOrderUuid(), recall.getUuid(), recall.getType());
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
