package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.business.ermb.migration.list.config.ErmbListMigrationConfig;
import com.rssl.phizic.business.ermb.migration.list.task.TaskLog;
import com.rssl.phizic.business.ermb.migration.list.task.TaskType;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ClientService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Операция запуска миграции клиентов из МБК/МБВ в ЕРИБ
 * @author Puzikov
 * @ created 05.12.13
 * @ $Author$
 * @ $Revision$
 */

public class StartListMigrationOperation extends OperationBase
{
	private static final String SUCCESS_MESSAGE = "Процесс миграции запущен. Файл отчета: %s";

	private final ClientService service = new ClientService();
	private TaskLog log;

	//Доступные для миграции сегменты
	private List<Segment> segments;

	public void initialize(List<Segment> segments)
	{
		this.segments = segments;
	}

	/**
	 * Запустить процесс миграции
	 * @param segments сегменты, из которых необходимо мигрировать клиентов
	 * @return статус задачи
	 * @throws BusinessException
	 */
	public String migrate(List<Segment> segments) throws BusinessException
	{
		try
		{
			log = new TaskLog(TaskType.START_MIGRATION);
			final Long firstBlock = ConfigFactory.getConfig(ErmbListMigrationConfig.class).getMigrationBlockSequence().get(0);

			int clientsCount = service.markClientsBlockBySegments(segments, firstBlock);

			log.add("Запущена миграция для сегментов: " + StringUtils.join(segments, ", "));
			log.add("Количество клиентов, отправленных на миграцию в блок №" + firstBlock + ": " + clientsCount);
			log.add("Миграция будет произведена джобами в каждом блоке по отдельности. Протоколы миграции будут записаны отдельно.");

			return String.format(SUCCESS_MESSAGE, log.getLogFileName());
		}
		catch (Exception e)
		{
			if (log != null)
				log.add(e);

			throw new BusinessException(e);
		}
		finally
		{
			if (log != null)
				log.flush();
		}
	}

	public List<Segment> getSegments()
	{
		return segments;
	}
}
