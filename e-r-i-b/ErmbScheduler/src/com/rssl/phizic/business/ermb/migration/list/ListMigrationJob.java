package com.rssl.phizic.business.ermb.migration.list;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.ermb.migration.list.config.ErmbListMigrationConfig;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;
import com.rssl.phizic.business.ermb.migration.list.task.migration.MigratorList;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.scheduler.StatefulModuleJob;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import java.util.List;

/**
 * Джоб для запуска списковой миграции сегментированных клиентов
 * @author Puzikov
 * @ created 16.01.14
 * @ $Author$
 * @ $Revision$
 */

public class ListMigrationJob extends StatefulModuleJob implements InterruptableJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private volatile boolean interrupt = false;

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		MigratorList migrator = null;
		try
		{
			final Long currentBlock = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
			final int migrationBatchSize = ConfigFactory.getConfig(ErmbListMigrationConfig.class).getMigrationBatchSize();

			while (!interrupt)
			{
				List<Client> clients = HibernateExecutor.getInstance("Migration").execute(new HibernateAction<List<Client>>()
				{
					public List<Client> run(Session session) throws Exception
					{
						//noinspection JpaQueryApiInspection
						Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.Client.findReadyForMigration");
						query.setParameter("block", currentBlock);
						query.setMaxResults(migrationBatchSize);
						//noinspection unchecked
						return query.list();
					}
				});
				if (CollectionUtils.isEmpty(clients))
					return;

				if (migrator == null)
					migrator = new MigratorList(currentBlock);

				for (Client client : clients)
				{
					if (interrupt)
						return;
					migrator.migrate(client);
				}
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка выполнения джоба списковой миграции МБК->ЕРМБ", e);
		}
		finally
		{
			if (migrator != null)
				migrator.flushInfo();
		}
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		interrupt = true;
	}

	@Override
	public Class<? extends Module> getModuleClass()
	{
		return ASMigrator.class;
	}
}
