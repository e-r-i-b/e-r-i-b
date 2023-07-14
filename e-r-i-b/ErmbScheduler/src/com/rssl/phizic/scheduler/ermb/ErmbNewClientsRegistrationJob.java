package com.rssl.phizic.scheduler.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.auxiliary.ErmbAuxChannel;
import com.rssl.phizic.business.ermb.disconnector.OSSConfig;
import com.rssl.phizic.business.ermb.newclient.CellularOperatorWebService;
import com.rssl.phizic.business.ermb.newclient.ErmbRegistrationBatch;
import com.rssl.phizic.business.ermb.newclient.ErmbRegistrationLocator;
import com.rssl.phizic.business.ermb.registration.ErmbRegistrationEvent;
import com.rssl.phizic.business.ermb.registration.ErmbRegistrationEventService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.scheduler.StatefulModuleJob;
import com.rssl.phizic.utils.EntityUtils;
import com.rssl.phizic.utils.PhoneNumber;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Gulov
 * @ created 19.08.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Извещение оператора о новых клиентах ЕРМБ
 */
public class ErmbNewClientsRegistrationJob extends StatefulModuleJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private static final ErmbRegistrationEventService registrationService = new ErmbRegistrationEventService();

	@Override
	public Class<? extends Module> getModuleClass()
	{
		return ErmbAuxChannel.class;
	}

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		if (!ConfigFactory.getConfig(OSSConfig.class).getUseIntegration())
			return;

		try
		{
			List<ErmbRegistrationEvent> registrations = registrationService.find(ConfigFactory.getConfig(OSSConfig.class).getMaxRegistrations());
			if (CollectionUtils.isEmpty(registrations))
				return;
			if (log.isDebugEnabled())
				log.debug("[Шедуллер ЕРМБ] Извещение Оператора о новых клиентах ЕРМБ " +
						StringUtils.join(EntityUtils.collectEntityIds(registrations), ", "));

			ErmbRegistrationLocator locator = new ErmbRegistrationLocator();
			Set<PhoneNumber> removed = new HashSet<PhoneNumber>();

			locator.locate(registrations);
			for (ErmbRegistrationBatch batch : locator.getBatches())
			{
				CellularOperatorWebService service = new CellularOperatorWebService(batch.getUrl(), batch.getLogin(), batch.getPassword());
				Set<PhoneNumber> notified = service.notify(batch.getErmbRegistrations());
				removed.addAll(notified);
			}
			removed.addAll(locator.getSkipped());

			if (CollectionUtils.isEmpty(removed))
				return;
			registrationService.remove(removed);
			if (log.isDebugEnabled())
				log.debug("[Шедуллер ЕРМБ] Извещение Оператора о новых клиентах ЕРМБ завершен");
		}
		catch (BusinessException e)
		{
			throw new JobExecutionException(e);
		}
		finally
		{
			LogThreadContext.clear();
		}
	}
}
