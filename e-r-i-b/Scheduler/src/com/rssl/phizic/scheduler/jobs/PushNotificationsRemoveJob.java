package com.rssl.phizic.scheduler.jobs;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.messaging.push.PushRemoveDevice;
import com.rssl.phizic.messaging.push.PushTransportService;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.net.InetAddress;
import java.util.List;

/**
 * Джоб мониторинга доступности push-уведомлений для МП
 * @author miklyaev
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PushNotificationsRemoveJob extends BaseJob implements StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);

	public PushNotificationsRemoveJob() throws JobExecutionException
	{
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		PushTransportService pushTransportService = MessagingSingleton.getInstance().getPushTransportService();

		try
		{
			List<PushRemoveDevice> pushRemoveDeviceList = pushTransportService.getRemoveDevices();
			if (CollectionUtils.isEmpty(pushRemoveDeviceList))
				return;

			LogThreadContext.setIPAddress(ApplicationConfig.getIt().getApplicationHost().getHostAddress());
			for (PushRemoveDevice device : pushRemoveDeviceList)
			{
				try
				{
					CSABackRequestHelper.sendChangePushSupportedRq(null, device.getDeviceId(), false, null);
					pushTransportService.updateRemoveDeviceState(device);
				}
				catch (Exception e)
				{
					log.error(e.getMessage(), e);
				}
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
