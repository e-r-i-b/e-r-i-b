package com.rssl.phizic.business.migration;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import oracle.jdbc.OracleTypes;
import org.apache.commons.logging.Log;
import org.hibernate.Session;

import java.sql.CallableStatement;

/**
 * @author akrenev
 * @ created 29.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Сервис миграции клиентов из резервного блока
 */

public class MigrationService
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final int RESULT_PARAMETER_NAME     = 1;
	private static final int THREAD_ID_PARAMETER_NAME  = 2;
	private static final int NODE_ID_PARAMETER_NAME    = 3;
	private static final int BATCH_SIZE_PARAMETER_NAME = 4;
	private static final int PERSON_ID_PARAMETER_NAME  = 5;

	/**
	 * смигрировать конкретного клиента
	 * @param personId идентификатор клиента
	 * @param migrationTimeout максимальное время работы процедуры миграции
	 * @return результат миграции
	 * @throws BusinessException
	 */
	public MigrationResult migrate(Long personId, int migrationTimeout) throws BusinessException
	{
		return migrate(null, null, null, personId, migrationTimeout);
	}

	/**
	 * миграция пачки клиентов
	 * @param threadId идентификатор потока миграции
	 * @param nodeId идентификатор блока
	 * @param batchSize размер пачки
	 * @param migrationTimeout максимальное время работы процедуры миграции
	 * @return результат миграции
	 * @throws BusinessException
	 */
	public MigrationResult migrate(Long threadId, Long nodeId, Long batchSize, int migrationTimeout) throws BusinessException
	{
		return migrate(threadId, nodeId, batchSize, null, migrationTimeout);
	}

	/**
	 * миграция клиентов из резервного блока
	 * @param threadId идентификатор потока миграции
	 * @param nodeId идентификатор блока
	 * @param batchSize размер пачки
	 * @param personId идентификатор клиента
	 * @param migrationTimeout максимальное время работы процедуры миграции
	 * @return результат миграции
	 * @throws BusinessException
	 */
	private MigrationResult migrate(final Long threadId, final Long nodeId, final Long batchSize, final Long personId, final int migrationTimeout) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<MigrationResult>()
			{
				public MigrationResult run(Session session) throws Exception
				{
					CallableStatement statement = null;

					try
					{
						statement = session.connection().prepareCall("{? = call stand_in_pkg.SyncNode(" +
								"?, " + //gStreamId in number
								"?, " + //gNodeId in number
								"?, " + //gPackSize in number default 0
								"?)}"); //gCSAProfileId in number default null

						statement.setObject(THREAD_ID_PARAMETER_NAME,  threadId,  OracleTypes.NUMERIC);
						statement.setObject(NODE_ID_PARAMETER_NAME,    nodeId,    OracleTypes.NUMERIC);
						statement.setObject(BATCH_SIZE_PARAMETER_NAME, batchSize, OracleTypes.NUMERIC);
						statement.setObject(PERSON_ID_PARAMETER_NAME,  personId,  OracleTypes.NUMERIC);

						statement.registerOutParameter(RESULT_PARAMETER_NAME, OracleTypes.INTEGER);
						statement.setQueryTimeout(migrationTimeout);
						statement.execute();
						int result = statement.getInt(RESULT_PARAMETER_NAME);
						return MigrationResult.getFromCode(result);
					}
					catch (Exception e)
					{
						log.error("Ошибка миграции клиентов.", e);
						return MigrationResult.ERROR;
					}
					finally
					{
						if (statement != null)
						{
							statement.close();
						}
					}
				}
			});
		}
		catch (Exception e)
		{
			log.error("Ошибка миграции клиентов.", e);
			return MigrationResult.ERROR;
		}
	}
}
