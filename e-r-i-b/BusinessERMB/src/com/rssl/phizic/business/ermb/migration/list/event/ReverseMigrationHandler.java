package com.rssl.phizic.business.ermb.migration.list.event;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.ermb.migration.list.config.ErmbListMigrationConfig;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationInfo;
import com.rssl.phizic.business.ermb.migration.list.task.migration.Reverser;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.events.EventHandler;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Обработчик событий отката миграции МБ - ЕРМБ на определенную дату
 * @author Puzikov
 * @ created 28.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ReverseMigrationHandler implements EventHandler<ReverseMigrationEvent>
{
	public void process(final ReverseMigrationEvent event) throws Exception
	{
		final Long currentBlock = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
		final int migrationBatchSize = ConfigFactory.getConfig(ErmbListMigrationConfig.class).getMigrationBatchSize();

		Reverser reverser = null;
		while (true)
		{
			List<MigrationInfo> migrationInfoList = HibernateExecutor.getInstance("Migration").execute(new HibernateAction<List<MigrationInfo>>()
			{
				public List<MigrationInfo> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationInfo.findMigrationInfoAfterDate");
					query.setParameter("date", event.getDate());
					query.setMaxResults(migrationBatchSize);
					//noinspection unchecked
					return query.list();
				}
			});
			if (reverser == null)
				reverser = new Reverser(currentBlock);

			if (CollectionUtils.isEmpty(migrationInfoList))
				break;

			for (MigrationInfo migrationInfo : migrationInfoList)
			{
				reverser.reverse(migrationInfo);
			}
		}

		reverser.flushInfo();
	}
}
