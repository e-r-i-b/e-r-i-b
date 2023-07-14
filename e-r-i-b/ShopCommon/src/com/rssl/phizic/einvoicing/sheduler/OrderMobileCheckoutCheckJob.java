package com.rssl.phizic.einvoicing.sheduler;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.einvoicing.ShopOrderServiceImpl;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.InvoiceGateBackService;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.einvoicing.ShopOrderService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.AcceptInfo;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.net.InetAddress;
import java.util.List;

/**
 * Джоб для проверки наличия подтвержденных оферт по заказам интернет-магазинов в МБ
 * @author gladishev
 * @ created 24.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class OrderMobileCheckoutCheckJob extends BaseJob implements StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private static final String MC_ORDER_ERROR = "Ошибка обработки mobile checkout заказа id=%s";

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			LogThreadContext.setIPAddress(ApplicationConfig.getIt().getApplicationHost().getHostAddress());
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			List<AcceptInfo> acceptInfos = mobileBankService.getOfferConfirm();
			if (CollectionUtils.isEmpty(acceptInfos))
				return;

			for (AcceptInfo acceptInfo : acceptInfos)
			{
				processPayment(acceptInfo, mobileBankService);
			}
		}
		catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
		finally
		{
			LogThreadContext.clear();
		}
	}

	private void processPayment(AcceptInfo acceptInfo, MobileBankService mobileBankService)
	{
		Long orderId = acceptInfo.getMessageId();
		ShopOrder order = null;
		try
		{
			order = GateSingleton.getFactory().service(ShopOrderService.class).getOrder(orderId);

			if (!checkOrder(order))
			{
				log.error(String.format(MC_ORDER_ERROR, orderId));
				return;
			}

			//формируем и отправляем платеж
			InvoiceGateBackService backService = GateSingleton.getFactory().service(InvoiceGateBackService.class);
			backService.sendOrderPayment(order);
		}
		catch (Exception e)
		{
			log.error("Ошибка обработки заказа id=" + orderId, e);
			return;
		}
		finally
		{
			if (order != null)
			{
				//удаляем оферту из базы МБ
				try
				{
					mobileBankService.sendOfferQuit(orderId);
				}
				catch (GateException e)
				{
					log.error("Ошибка при удалении оферты из базы МБ. Идентификатор заказа=" + orderId, e);
				}
				catch (GateLogicException e)
				{
					log.error("Ошибка при удалении оферты из базы МБ. Идентификатор заказа=" + orderId, e);
				}
			}
		}
	}

	private boolean checkOrder(ShopOrder order)
	{
		if (order == null)
			return false;

		return order.getState() == OrderState.PAYMENT;
	}
}
