package com.rssl.phizic.scheduler.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbOfficialInfoNotificationSender;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.auxiliary.ErmbAuxChannel;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.scheduler.StatefulModuleJob;
import com.rssl.phizic.utils.EntityUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * Джоб для подтверждения версий ЕРМБ-профилей
 * 
 @ author: EgorovaA
 @ created: 01.02.2013
 @ $Author$
 @ $Revision$
 */

public class ErmbProfilesUpdateJob extends StatefulModuleJob
{
	private final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);

	private final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	public Class<? extends Module> getModuleClass()
	{
		return ErmbAuxChannel.class;
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			List<ErmbProfileImpl> profiles = profileService.findProfilesToUpdate();

			if (!CollectionUtils.isEmpty(profiles))
			{
				if (log.isDebugEnabled())
					log.debug("[Шедуллер ЕРМБ] Отправка сообщения об изменении профилей " + StringUtils.join(EntityUtils.collectEntityIds(profiles), ", "));
				ErmbOfficialInfoNotificationSender sender = new ErmbOfficialInfoNotificationSender();
				sender.sendNotification(profiles);
			}
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (RuntimeException e)
		{
			log.error(e.getMessage(), e);
		}
	}
}