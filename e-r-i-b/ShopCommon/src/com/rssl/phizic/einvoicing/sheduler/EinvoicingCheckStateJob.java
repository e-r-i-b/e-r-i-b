package com.rssl.phizic.einvoicing.sheduler;

import com.rssl.phizic.common.types.Period;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.invoicing.InvoicingConfig;
import com.rssl.phizic.einvoicing.ShopOrderServiceImpl;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.List;

/**
 * Уточнение статусов старых заказов/отмен платежей/возвратов
 * @author gladishev
 * @ created 12.03.2014
 * @ $Author$
 * @ $Revision$
 */

public class EinvoicingCheckStateJob extends BaseJob implements StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private ShopOrderServiceImpl shopService = new ShopOrderServiceImpl(null);

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			LogThreadContext.setIPAddress(ApplicationConfig.getIt().getApplicationHost().getHostAddress());
			checkOrdersState();
			checkRecallsState();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		finally
		{
			LogThreadContext.clear();
		}
	}

	private void checkOrdersState()
	{
		List<ShopOrder> delayedOrders = null;
		try
		{
			delayedOrders = shopService.getDelayedOrders(getPeriod());
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
		}

		if (CollectionUtils.isEmpty(delayedOrders))
			return;

		InvoiceGateBackService service = GateSingleton.getFactory().service(InvoiceGateBackService.class);
		for (ShopOrder order : delayedOrders)
		{
			try
			{
				OrderStateInfo orderStateInfo = service.getOrderState(order);
				if (order.getState() != orderStateInfo.getState())
					shopService.changeOrderStatus(order.getUuid(), orderStateInfo.getState(), order.getNodeId(), orderStateInfo.getUtrrno(), null, null);
			}
			catch (Exception e)
			{
				log.error("Ошибка при уточнении статуса заказа uuid=" + order.getUuid(), e);
			}
		}
	}

	private void checkRecallsState()
	{
		List<ShopRecall> delayedRecalls = null;
		try
		{
			delayedRecalls = shopService.getDelayedRecalls(getPeriod());
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
		}

		if (CollectionUtils.isEmpty(delayedRecalls))
			return;

		InvoiceGateBackService service = GateSingleton.getFactory().service(InvoiceGateBackService.class);
		for (ShopRecall recall : delayedRecalls)
		{
			try
			{
				RecallStateInfo stateInfo = service.getRecallState(recall);
				if (recall.getState() != stateInfo.getState())
					shopService.changeRecallStatus(recall.getUuid(), stateInfo.getState(), stateInfo.getUtrrno(), recall.getType());
			}
			catch (Exception e)
			{
				log.error("Ошибка при уточнении статуса отмены платежа/возврата товара uuid=" + recall.getUuid(), e);
			}
		}
	}

	private Period getPeriod()
	{
		Pair<Integer,Integer> delayedOrdersDays = ConfigFactory.getConfig(InvoicingConfig.class).getDelayedOrdersPeriod();

		if (delayedOrdersDays == null)
		{
			log.error("Не указан период выборки заказов для уточнения статусов");
			return null;
		}

		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.DAY_OF_MONTH, -delayedOrdersDays.getFirst());

		Calendar endDate = Calendar.getInstance();
		endDate.add(Calendar.DAY_OF_MONTH, -delayedOrdersDays.getSecond());

		return new Period(startDate, endDate);
	}
}
