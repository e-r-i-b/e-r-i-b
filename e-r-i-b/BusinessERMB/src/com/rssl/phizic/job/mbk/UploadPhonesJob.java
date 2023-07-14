package com.rssl.phizic.job.mbk;

import com.rssl.phizic.business.ermb.migration.mbk.PhonesUploadStatus;
import com.rssl.phizic.business.ermb.migration.mbk.PhonesUploader;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.jmx.MobileBankConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.logging.Log;
import org.quartz.*;

/**
 * @author Rtischeva
 * @ created 15.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Джоб синхронизации ЕРМБ телефонов с МБК
 */
public class UploadPhonesJob implements InterruptableJob, StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private final PhonesUploader phonesUploader = new PhonesUploader();
	private volatile boolean isInterrupt = false;

	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		ApplicationInfo.setCurrentApplication(Application.Scheduler);

		try
		{
			while (true)
            {
	            if (isInterrupt)
		            break;

	            MobileBankConfig mobileBankСonfig = ConfigFactory.getConfig(MobileBankConfig.class);
                mobileBankСonfig.doRefresh();
                if (!mobileBankСonfig.isErmbManagePhonesEnabled())
	                break;

                log.trace("Начало синхронизации ЕРМБ-телефонов с МБК");
                PhonesUploadStatus status = phonesUploader.uploadPhones();
                if (status == PhonesUploadStatus.ENOUGH)
                    break;
            }
		}
		catch (Exception e)
		{
			log.error("Ошибка синхронизации ЕРМБ-телефонов с МБК", e);
		}
		finally
		{
			log.trace("Конец синхронизации ЕРМБ-телефонов с МБК");
		}
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}
}
