package com.rssl.phizic.job.mbk;

import com.rssl.phizic.business.ermb.migration.mbk.MBKRegistrationLoader;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.jmx.MobileBankConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.*;

/**
 * User: Moshenko
 * Date: 09.07.14
 * Time: 10:14
 * Загрузка связок МБК
 */
public class LoadMBKRegistrationsJob implements StatefulJob,InterruptableJob
{
	private final Log log = PhizICLogFactory.getLog(LogModule.Scheduler);
	private volatile boolean isInterrupt = false;

	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		ApplicationInfo.setCurrentApplication(Application.Scheduler);
		try
		{
			MobileBankConfig mobileBankСonfig = ConfigFactory.getConfig(MobileBankConfig.class);
			if (mobileBankСonfig.isMbkRegistrationLoadEnabled())
			{
				MBKRegistrationLoader loader = new MBKRegistrationLoader();
				while (!isInterrupt)
				{
					log.trace("Начало загрузки связок мбк");
					//пока не вернет ENOUGH
					if (loader.load() == MBKRegistrationLoader.Status.ENOUGH)
						break;
				}
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка загрузки связок мбк", e);
		}
		finally
		{
			log.debug("Конец загрузки связок мбк");
		}
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}
}
