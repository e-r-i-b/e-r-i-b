package com.rssl.phizic.job.fpp;

import com.rssl.phizic.business.SimpleJobService;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbSubscribeFeeConfig;
import com.rssl.phizic.config.ermb.SubscribeFeeConstants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleTrigger;

import java.util.Date;

import static com.rssl.phizic.business.SimpleJobService.ERMB_QUARTZ_PROPERTIES;

/**
 * User: Moshenko
 * Date: 04.06.14
 * Time: 10:24
 * Джоб-координатор.Создаёт джобы по выгрузке ФПП (FPPUnloadJob).
 */
public class FPPStarterJob implements Job
{
	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_SCHEDULER);

	private final SimpleJobService jobService = new SimpleJobService(ERMB_QUARTZ_PROPERTIES);

	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		ApplicationInfo.setCurrentApplication(Application.Scheduler);
		try
		{
			ErmbSubscribeFeeConfig config = ConfigFactory.getConfig(ErmbSubscribeFeeConfig.class);
			config.doRefresh();
			//снимаем флажок "профиль недоступен для списания абонентской платы" для всех ермб-профилей
			profileService.unlockFppBlockedProfiles();
			for(int i = 0 ; i < config.getFppProcTotalCount() ; i++)
			{
				//Создаем новый SimpleTrigger с временем исполнения в текущий момент
				SimpleTrigger trigger = new SimpleTrigger(SubscribeFeeConstants.FPP_UNLOAD + "_" + i, SubscribeFeeConstants.FPP_UNLOAD,
						SubscribeFeeConstants.FPP_UNLOAD, SubscribeFeeConstants.FPP_UNLOAD, new Date(), null, 0 ,1);
				//Добавляем триггер в расписание джобы-разгрузчики
				jobService.addNewTriggerToJob(SubscribeFeeConstants.FPP_UNLOAD,FPPUnloadJob.class.getCanonicalName(), SubscribeFeeConstants.FPP_UNLOAD,trigger);
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}
}
