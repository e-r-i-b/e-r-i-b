package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.images.AvatarJurnalService;
import com.rssl.phizic.business.profile.images.RemoteAvatarInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.addressbook.AddressBookConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.service.StartupServiceDictionary;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.*;

/**
 * Джоб для обновления справочника аваторов.
 *
 * @author bogdanov
 * @ created 25.10.14
 * @ $Author$
 * @ $Revision$
 */
public class UpdateAvatarsJob extends BaseJob implements StatefulJob
{
	private static AvatarJurnalService avatarJurnalService = new AvatarJurnalService();
	private static long lastUpdateTime = -1L;

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);

	/**
	 * ctor
	 * @throws org.quartz.JobExecutionException
	 */
	public UpdateAvatarsJob() throws JobExecutionException
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
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	private void update() throws Exception
	{
		Map<String, RemoteAvatarInfo> added = new HashMap<String, RemoteAvatarInfo>();
		Map<String, RemoteAvatarInfo> deleted = new HashMap<String, RemoteAvatarInfo>();
		int rows = ConfigFactory.getConfig(AddressBookConfig.class).getSberclietnSyncPackSize();
		int currow = 0;
		Calendar last = Calendar.getInstance();
		last.setTimeInMillis(lastUpdateTime);
		while (true)
		{
			List<RemoteAvatarInfo> phones = avatarJurnalService.getDelta(last, currow, currow + rows);
			currow += rows;
			for (RemoteAvatarInfo phone : phones)
			{
				if (!StringHelper.isEmpty(phone.getAvatarPath()))
				{
					added.put(phone.getPhone(), phone);
					flush(added, false, false);
				}
				else
				{
					deleted.put(phone.getPhone(), phone);
					flush(deleted, false, true);
				}
			}
			if (phones.isEmpty() || phones.size() < rows)
				break;
		}
		flush(added, true, false);
		flush(deleted, true, true);
		lastUpdateTime = System.currentTimeMillis();
	}

	private void flush(final Map<String, RemoteAvatarInfo> list, boolean force, final boolean delete) throws BusinessException
	{
		try
		{
			if (list.size() <= ConfigFactory.getConfig(AddressBookConfig.class).getSberclietnSyncPackSize() && !force)
				return;

			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					for (String key : list.keySet())
					{
						if (!delete)
							avatarJurnalService.addAvatarInner(key, list.get(key).getAvatarPath());
						else
							avatarJurnalService.deleteAvatarInner(key);
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