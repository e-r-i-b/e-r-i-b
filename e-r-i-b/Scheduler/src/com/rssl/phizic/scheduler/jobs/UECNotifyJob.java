package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.shop.ExternalPaymentService;
import org.quartz.JobExecutionException;
import com.rssl.phizic.business.shop.Order;
import com.rssl.phizic.business.shop.OrderStatus;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.payments.PaymentsSystemNameConstants;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizicgate.shopclient.UECPayOrder;
import com.rssl.phizicgate.uec.UECWebService;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Erkin
 * @ created 17.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Джоб сообщает УЭК статус платёжных поручений
 */
public class UECNotifyJob extends AbstractPayOrderJob
{
	private static final String UEC_SPNAME = PaymentsSystemNameConstants.SYSTEM_NAME_UEC;
	private ExternalPaymentService externalPaymentService;
	/**
	 * ctor
	 */
	public UECNotifyJob() throws JobExecutionException
	{
	}

	protected int executeOrders() throws Exception
	{
		externalPaymentService = ExternalPaymentService.get();
		// достаем заказы без отправленного оповещения небольшими порциями
		List<Order> orders = externalPaymentService.getNotNotifiedUECOrders(getMaxRows(), getExecuteStartTime(), getNotifyMaxCount());

		if (isInterrupted() || orders.isEmpty())
			return 0;

		Map<Long, UECPayOrder> uecPayOrders = convertOrdersToUEC(orders);
		getUecWebService().notifyUECOrders(UEC_SPNAME, uecPayOrders.values());

		updateUECOrders(orders, uecPayOrders);

		return orders.size();
	}

	private UECWebService getUecWebService() throws BusinessException
	{
		InternetShopsServiceProvider provider = getServiceProvider(UEC_SPNAME);
		return new UECWebService(provider.getUrl());
	}

	private Map<Long, UECPayOrder> convertOrdersToUEC(List<Order> orders) throws BusinessException
	{
		Map<Long, UECPayOrder> uecPayOrders = new HashMap<Long, UECPayOrder>(orders.size());
		for (Order order : orders)
		{
			BusinessDocument payment = DocumentHelper.getPaymentByOrder(order.getUuid());
			State paymentState = payment.getState();
			UECPayOrder uecPayOrder = new UECPayOrder(order.getId(), order.getExtendedId(), paymentState.getCode());
			uecPayOrders.put(uecPayOrder.getOrderId(), uecPayOrder);
		}
		return uecPayOrders;
	}

	private void updateUECOrders(List<Order> orders, Map<Long, UECPayOrder> uecPayOrders) throws BusinessException
	{
		for (Order order : orders)
		{
			order.incrementNotificationCount();
			UECPayOrder uecPayOrder = uecPayOrders.get(order.getId());

			OrderStatus orderStatus = null;
			String orderStatusDescr = null;
			if (uecPayOrder != null)
			{
				if (uecPayOrder.getNotifyStatusCode() != null)
				{
					orderStatus = (uecPayOrder.getNotifyStatusCode() == 0) ? OrderStatus.OK : OrderStatus.ERROR;
					orderStatusDescr = uecPayOrder.getNotifyStatusDescr();
				}
			}
			else
			{
				orderStatus = OrderStatus.ERROR;
				orderStatusDescr = CUSTOM_ERROR_DESCR;
			}

			if ((order.getNotificationStatus() != OrderStatus.OK) && (order.getNotificationCount() >= getNotifyMaxCount()))
				order.setNotificationStatus(OrderStatus.SUSPENDED);

			order.setNotificationStatus(orderStatus);
			order.setDescription(orderStatusDescr);
			order.setNotificationTime(getExecuteStartTime());
		}

		saveOrders(orders);
	}

	private void saveOrders(List<Order> orders) throws BusinessException
	{
		externalPaymentService.getSimpleService().addOrUpdateList(orders);
	}
}