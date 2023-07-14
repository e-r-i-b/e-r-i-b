package com.rssl.phizic.scheduler.jobs.addressbook;

import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizgate.common.profile.MBKCastService;
import com.rssl.phizgate.common.profile.PhoneUpdate;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.profile.smartaddressbook.MBKCastCsaAdminService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.config.addressbook.AddressBookConfig;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.utils.jms.JmsService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import javax.jms.JMSException;

/**
 * Джоб актуализации признака Клиент Сбербанка в CSAAdmin.
 *
 * @author bogdanov
 * @ created 03.10.14
 * @ $Author$
 * @ $Revision$
 */

public class ActualizeSberbankClientPhoneCSAAdminJob extends BaseJob implements StatefulJob
{
	private static long lastUpdateTime = -1L;
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final JmsService jmsService = new JmsService();

	private static final String E_INVOICING_QUEUE_NAME = "com.rssl.iccs.addressbook.einvoicing.queuename";
	private static final String E_INVOICING_QUEUE_FACTORY = "com.rssl.iccs.addressbook.einvoicing.queuefactory";

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			EditPropertiesOperation epo = new EditPropertiesOperation();
			epo.initialize(PropertyCategory.CSAAdmin, new HashSet<String>(Arrays.asList(AddressBookConfig.LAST_UPDATE_TIME)));
			Object obj = epo.getEntity().get(AddressBookConfig.LAST_UPDATE_TIME);
			long lastUpdate = obj != null ? Math.max(Long.parseLong(obj.toString()), lastUpdateTime) : lastUpdateTime;
			Calendar ltt = Calendar.getInstance();
			ltt.setTime(ConfigFactory.getConfig(AddressBookConfig.class).getTimeForStartSynchronization());
			Calendar timeForSync = Calendar.getInstance();
			timeForSync.set(Calendar.HOUR_OF_DAY, ltt.get(Calendar.HOUR_OF_DAY));
			timeForSync.set(Calendar.MINUTE, ltt.get(Calendar.MINUTE));

			Calendar lt = Calendar.getInstance();
			lt.setTimeInMillis(lastUpdate);

			Calendar ct = Calendar.getInstance();
			if (!ct.after(timeForSync) || !lt.before(timeForSync))
				return;

			MBKCastCsaAdminService mbkCastService = new MBKCastCsaAdminService();

			Long lastIdx = mbkCastService.loadByDelta(lastUpdate == -1 ? Calendar.getInstance() : lt, Calendar.getInstance());

			lastUpdateTime = System.currentTimeMillis();
			epo.getEntity().put(AddressBookConfig.LAST_UPDATE_TIME, Long.toString(lastUpdateTime));
			epo.save();

			notifyUpdatePhones(lastIdx);
		}
		catch (BusinessException e)
		{
			PhizICLogFactory.getLog(LogModule.Scheduler).error("ошибка во время синхронизации телефонных номеров", e);
		}
		catch (BusinessLogicException e)
		{
			PhizICLogFactory.getLog(LogModule.Scheduler).error("ошибка во время синхронизации телефонных номеров", e);
		}
	}

	private void notifyUpdatePhones(Long lastIdx)
	{
		if (lastIdx == null)
			return;

		NodeInfoConfig nodeInfoConfig = ConfigFactory.getConfig(NodeInfoConfig.class);
		PhoneUpdate phoneUpdate = new PhoneUpdate();
		phoneUpdate.setNewItem(true);
		phoneUpdate.setUpdateIndex(lastIdx);
		phoneUpdate.setUpdateDate(Calendar.getInstance());
		for (NodeInfo info : nodeInfoConfig.getNodes())
		{
			try
			{
				MQInfo dictionaryMQ = info.getDictionaryMQ();
				jmsService.sendObjectToQueue(phoneUpdate, dictionaryMQ.getQueueName(), dictionaryMQ.getFactoryName(), MBKCastService.START_ACTUALIZE_MESSAGE_NAME, null);
			}
			catch (JMSException e)
			{
				log.error("Ошибка отправки JMS оповещения об изменении справочника " + phoneUpdate.getClass().getSimpleName() + " на узел " + info.getName(), e);
			}
		}

		try
		{
			ApplicationAutoRefreshConfig config = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class);
			jmsService.sendObjectToQueue(phoneUpdate, config.getProperty(E_INVOICING_QUEUE_NAME), config.getProperty(E_INVOICING_QUEUE_FACTORY), MBKCastService.START_ACTUALIZE_MESSAGE_NAME, null);
		}
		catch (JMSException e)
		{
			log.error("Ошибка отправки JMS оповещения об изменении справочника " + phoneUpdate.getClass().getSimpleName() + " на E-Invocing");
		}
	}
}
