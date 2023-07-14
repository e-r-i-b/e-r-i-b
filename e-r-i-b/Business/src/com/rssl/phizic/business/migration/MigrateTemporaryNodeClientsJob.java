package com.rssl.phizic.business.migration;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.*;

/**
 * @author akrenev
 * @ created 30.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Джоб миграции клиентов из резервного блока
 */

public class MigrateTemporaryNodeClientsJob extends BaseJob implements Job, InterruptableJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);

	private static final MigrationService migrationService = new MigrationService();
	private static final MigrationInfoService migrationInfoService = new MigrationInfoService();

	private volatile boolean isInterrupt = false;

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}

	@Override
	protected void executeJob(JobExecutionContext context)
	{
		try
		{
			ThreadMigrationInfo threadMigrationInfo = migrationInfoService.startThreadMigration();
			if (threadMigrationInfo == null)
				return;

			Long threadId = threadMigrationInfo.getId();
			Long nodeId = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
			MigrationResult resultCode = MigrationResult.MIGRATED;
			while (!isInterrupt && !threadMigrationInfo.isNeedStop() && resultCode == MigrationResult.MIGRATED)
			{
				resultCode = migrationService.migrate(threadId, nodeId, threadMigrationInfo.getBatchSize(), getMigrationTimeout());
				threadMigrationInfo = getThreadMigrationInfo(threadId);
			}
			migrationInfoService.stopThreadMigration(threadId);
		}
		catch (Exception e)
		{
			log.error("Ошибка миграции клиентов.", e);
		}
	}

	private int getMigrationTimeout()
	{
		return ConfigFactory.getConfig(MigrationConfig.class).getMigrationTimeout();
	}

	private ThreadMigrationInfo getThreadMigrationInfo(Long id) throws BusinessException
	{
		return migrationInfoService.getThreadMigrationInfo(id);
	}
}
