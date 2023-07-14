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
 * �������� ������ ���
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
			MobileBankConfig mobileBank�onfig = ConfigFactory.getConfig(MobileBankConfig.class);
			if (mobileBank�onfig.isMbkRegistrationLoadEnabled())
			{
				MBKRegistrationLoader loader = new MBKRegistrationLoader();
				while (!isInterrupt)
				{
					log.trace("������ �������� ������ ���");
					//���� �� ������ ENOUGH
					if (loader.load() == MBKRegistrationLoader.Status.ENOUGH)
						break;
				}
			}
		}
		catch (Exception e)
		{
			log.error("������ �������� ������ ���", e);
		}
		finally
		{
			log.debug("����� �������� ������ ���");
		}
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}
}
