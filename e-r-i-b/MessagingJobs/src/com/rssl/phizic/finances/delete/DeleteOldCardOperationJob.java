package com.rssl.phizic.finances.delete;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.Calendar;

/**
 * Периодическая очистка старых операций в АЛФ
 * @author komarov
 * @ created 22.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class DeleteOldCardOperationJob extends BaseJob implements StatefulJob
{
	private static final CardOperationService cardOperationService = new CardOperationService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);

	/**
	 * конструктор
	 * @throws JobExecutionException
	 */
	public DeleteOldCardOperationJob() throws JobExecutionException
	{
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			cardOperationService.deleteOldCardOperation(DateHelper.addDays(Calendar.getInstance(), -getOperationLifeTime()));
		}
		catch(BusinessException ex)
		{
		    log.error("Ошибка удаления данных о карточных операциях пользователя", ex);
		}
	}

	private int getOperationLifeTime()
	{
		return ConfigFactory.getConfig(IPSConfig.class).getCardOperationMaxTime();
	}
}
