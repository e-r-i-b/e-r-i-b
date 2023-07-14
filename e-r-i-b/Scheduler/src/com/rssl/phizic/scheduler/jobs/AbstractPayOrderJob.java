package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.businessProperties.BusinessPropertyService;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.payments.job.JobRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.invoicing.InvoicingConfig;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 13.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовый класс джобов по обработке платёжных поручений e-invoicing
 */
abstract class AbstractPayOrderJob extends BaseJob implements StatefulJob, InterruptableJob
{
	protected static final long CUSTOM_ERROR_CODE = 314159265358L; //Pi

	protected static final String CUSTOM_ERROR_DESCR = "Notification error";

	protected static final Log log = PhizICLogFactory.getLog(LogModule.Scheduler);

	private static final BusinessPropertyService businessPropertyService = new BusinessPropertyService();

	protected static final ServiceProviderService providerService = new  ServiceProviderService();

	/**
	 * Флажок "выполнение джоба было прервано"
	 */
	private transient volatile boolean interrupted = false;

	private int maxRows;

	private int notifyMaxCount;

	private Date executeStartTime;

	/**
	 * Мапа "системное_имя -> поставщик"
	 */
	private transient final Map<String, InternetShopsServiceProvider> providers = new HashMap<String, InternetShopsServiceProvider>();

	public void interrupt()
	{
		interrupted = true;
	}

	protected boolean isInterrupted()
	{
		return interrupted;
	}

	protected int getMaxRows()
	{
		return maxRows;
	}

	protected int getNotifyMaxCount()
	{
		return notifyMaxCount;
	}

	/**
	 * @return время (текущего) старта job-а
	 */
	protected Date getExecuteStartTime()
	{
		return executeStartTime;
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			maxRows = ConfigFactory.getConfig(JobRefreshConfig.class).getMaxRowsFromFetch();
			notifyMaxCount = readNotifyMaxCount();
			executeStartTime = new Date();

			providers.clear();

			while (!interrupted)
			{
				int executedOrdersCount = executeOrders();
				// Заказов нет => останавливаемся
				if (executedOrdersCount == 0)
					break;
			}
		}
		catch(Exception e)
		{
			log.error("Ошибка отправки оповещения", e);
		}
	}

	/**
	 * Обработать заказы
	 * @return кол-во обработанных заказов, 0 = больше нечего обрабатывать 
	 * @throws Exception исключение, которое блокирует обработку всех заказов  
	 */
	protected abstract int executeOrders() throws Exception;

	private static int readNotifyMaxCount() throws JobExecutionException
	{
		Long notificationCount = ConfigFactory.getConfig(InvoicingConfig.class).getOrderNotificationCount();

		if (notificationCount == null)
		{
			log.warn("Не задано количество попыток уведомлений об оплате заказа (Интернет-магазины) в пункте Настройки > Ограничения на операции");
			notificationCount = 0L;
		}
		return notificationCount.intValue();
	}

	/**
	 * Возвращает поставщика с указанным системным именем
	 * @param systemName - системное имя
	 * @return поставщик (never null)
	 */
	protected InternetShopsServiceProvider getServiceProvider(String systemName) throws BusinessException
	{
		InternetShopsServiceProvider provider = providers.get(systemName);
		if (provider == null)
		{
			provider = providerService.getRecipientActivityBySystemName(systemName);
			if (provider == null)
				throw new NotFoundException("Не найден активный поставщик с systemName=" + systemName);
			providers.put(systemName, provider);
		}
		return provider;
	}

	/**
	 * @param businessDocument - платёж
	 * @return UTTRNO - это идентификатор платежа во внешней системе
	 */
	protected String getPaymentUTTRNO(BusinessDocument businessDocument)
	{
		String utrrno = null;
		if (businessDocument instanceof AbstractPaymentSystemPayment)
		{
			AbstractPaymentSystemPayment payment = (AbstractPaymentSystemPayment) businessDocument;
			utrrno = payment.getIdFromPaymentSystem();
		}
		else if (businessDocument instanceof WithdrawDocument)
		{
			WithdrawDocument document = (WithdrawDocument) businessDocument;
			utrrno = document.getIdFromPaymentSystem();
		}

		if (StringHelper.isEmpty(utrrno))
			log.warn("Не определён UTTRNO для платежа во внешнюю систему с ID = " + businessDocument.getId());

		return utrrno;
	}
}
