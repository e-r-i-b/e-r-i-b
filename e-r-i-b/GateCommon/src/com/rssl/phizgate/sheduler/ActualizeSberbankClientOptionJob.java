package com.rssl.phizgate.sheduler;

import com.rssl.phizgate.common.profile.MBKCastService;
import com.rssl.phizgate.common.profile.MBKPhoneLoaderCallBack;
import com.rssl.phizgate.common.profile.PhoneUpdate;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.PropertiesGateConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * Джоб актуализации признака Клиент Сбербанка в блоке для всех адреснных книг и в E-Invoicing.
 *
 * @author bogdanov
 * @ created 03.10.14
 * @ $Author$
 * @ $Revision$
 */

public class ActualizeSberbankClientOptionJob extends BaseJob implements StatefulJob
{

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			MBKCastService mbkCastService = new MBKCastService((MBKPhoneLoaderCallBack) ConfigFactory.getConfig(PropertiesGateConfig.class).getMBKPhoneLoaderCallback());

			PhoneUpdate upd = mbkCastService.getLastPhoneInfo();
			if (upd == null)
				return;

			mbkCastService.loadByDelta(upd.getUpdateIndex() - 1);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(LogModule.Scheduler).error("Ошибка во время синхронизации телефонных номеров", e);
		}
	}
}
