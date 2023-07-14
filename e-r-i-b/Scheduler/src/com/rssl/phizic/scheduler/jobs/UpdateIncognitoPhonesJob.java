package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.incognitoDictionary.IncognitoPhonesService;
import com.rssl.phizic.business.profile.IncognitoPhone;
import com.rssl.phizic.business.profile.RemoteIncognitoService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.config.Property;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.config.addressbook.AddressBookConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.service.StartupServiceDictionary;
import org.hibernate.Session;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Джоб для обновления справочника "инкогнито"
 *
 * @author EgorovaA
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 */
public class UpdateIncognitoPhonesJob extends BaseJob implements StatefulJob
{
	private static final String LAST_UPDATE_TIME_INCOGNITO_PHONES_KEY = "com.rssl.phizic.web.configure.addressBookSettings.last.update.time.incognito.phones";

	private static IncognitoPhonesService phonesService = new IncognitoPhonesService();
	private static RemoteIncognitoService remotePhonesService = new RemoteIncognitoService();
	private static long lastUpdateTime = -1L;


	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);

	/**
	 * ctor
	 * @throws JobExecutionException
	 */
	public UpdateIncognitoPhonesJob() throws JobExecutionException
	{
		runStartupServices();
	}

	private void runStartupServices()
	{
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.startMBeans();
	}

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		if (lastUpdateTime + ConfigFactory.getConfig(AddressBookConfig.class).getIncognitoPeriodSynchronization() > System.currentTimeMillis())
			return;

		try
		{
			update();
		}
		catch (Exception e)
		{
			throw new JobExecutionException(e);
		}
	}

	/**
	 * @throws BusinessException
	 */
	private void update() throws Exception
	{
		Set<String> added = new HashSet<String>();
		Set<String> deleted = new HashSet<String>();
		int rows = ConfigFactory.getConfig(AddressBookConfig.class).getSberclietnSyncPackSize();
		int currow = 0;
		Calendar last = Calendar.getInstance();
		if (lastUpdateTime == -1L)
		{
			Property property = DbPropertyService.findProperty(LAST_UPDATE_TIME_INCOGNITO_PHONES_KEY, PropertyCategory.Phizic.getValue(), DbPropertyService.getDbInstance(PropertyCategory.Phizic.getValue()));
			if (property != null)
				lastUpdateTime = Long.valueOf(property.getValue());
		}
		last.setTimeInMillis(lastUpdateTime);
		while (true) {
			List<IncognitoPhone> phones = remotePhonesService.getDelta(last, currow, currow + rows);
			currow += rows;
			for (IncognitoPhone phone : phones)
			{
				if (phone.isActive())
				{
			        added.add(phone.getPhone());
					flush(added, false, false);
				}
				else
				{
					deleted.add(phone.getPhone());
					flush(deleted, false, true);
				}
			}
			if (phones.isEmpty() || phones.size() < rows)
				break;
		}
		flush(added, true, false);
		flush(deleted, true, true);
		lastUpdateTime = System.currentTimeMillis();
		DbPropertyService.updateProperty(LAST_UPDATE_TIME_INCOGNITO_PHONES_KEY, String.valueOf(lastUpdateTime), PropertyCategory.Phizic);
	}

	private void flush(final Set<String> list, boolean force, final boolean delete) throws BusinessException
	{
		try
		{
			if (list.size() <= ConfigFactory.getConfig(AddressBookConfig.class).getSberclietnSyncPackSize() && !force)
				return;

			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					for (String ph : list) {
						if (!delete)
							phonesService.add(ph);
						else
							phonesService.delete(ph);
					}
					return null;
				}
			});
			list.clear();
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}
}