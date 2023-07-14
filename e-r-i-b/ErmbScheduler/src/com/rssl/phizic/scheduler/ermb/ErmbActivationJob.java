package com.rssl.phizic.scheduler.ermb;

import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.migration.CodWayErmbConnectionSender;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.Calendar;
import java.util.List;

/**
 * Джоб активации ермб во внешних системах (досылка признака подключения)
 * @author Puzikov
 * @ created 25.06.14
 * @ $Author$
 * @ $Revision$
 */

public class ErmbActivationJob extends BaseJob implements StatefulJob, InterruptableJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private static final int BATCH_SIZE = 1000;

	private transient volatile boolean interrupted = false;

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			Calendar currentDate = Calendar.getInstance();
			CodWayErmbConnectionSender sender = new CodWayErmbConnectionSender();
			boolean codOnly = sender.isSendCod() && !sender.isSendWay();

			while (!interrupted)
			{
				List<ErmbProfileImpl> profiles = profileService.getProfilesToActivate(currentDate, codOnly, BATCH_SIZE);
				if (CollectionUtils.isEmpty(profiles))
					return;

				for (ErmbProfileImpl profile : profiles)
				{
					if (interrupted)
						return;
					sender.sendErmbConnected(profile);
				}
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка выполнения джоба отсылки признака подключения ЕРМБ в ЦОД/Way", e);
		}
	}

	public void interrupt()
	{
		interrupted = true;
	}
}
