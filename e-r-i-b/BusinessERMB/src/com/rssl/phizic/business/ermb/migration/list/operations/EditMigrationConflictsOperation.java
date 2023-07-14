package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Conflict;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.ConflictedClient;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ClientService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.rssl.phizic.business.ermb.migration.list.entity.migrator.ConflictStatus.RESOLVED_TO_DELETE;
import static com.rssl.phizic.business.ermb.migration.list.entity.migrator.ConflictStatus.RESOLVED_TO_OWNER;

/**
 * Операция разрешения конфликтов миграции для клиента
 * @author Puzikov
 * @ created 11.12.13
 * @ $Author$
 * @ $Revision$
 */

public class EditMigrationConflictsOperation extends OperationBase
{
	private static final String EDIT_CONFLICT_OPERATION_KEY = "EDIT_CONFLICT_OPERATION_KEY";
	private List<Conflict> conflicts;

	private final ClientService service = new ClientService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Инициализация операции
	 * @param clientIdString id клиента, по которому разрешаются конфликты телефонов
	 * @throws BusinessException
	 */
	public void initialize(String clientIdString) throws BusinessException
	{
		if (StringUtils.isBlank(clientIdString))
			throw new BusinessException("Не указан ID пользователя");
		Long clientId = Long.valueOf(clientIdString);

		conflicts = service.findConflictsByClient(clientId, true);
	}

	/**
	 * сохранить результаты разрешения конфликтов
	 * т.е. сохранить в таблице мигратора выбор сотрудника
	 * @param phonesToResult результат разрешения конфликтов, мапа id телефон на id по которому разрешен конфликт (RESOLVED_TO_THIS - оставить, RESOLVED_TO_DELETE - удалить)
	 * @param employeeFio фио сотрудника, разрешившего конфликт
	 */
	public void save(Map<String, String> phonesToResult, String employeeFio) throws BusinessException
	{
		for (Conflict conflict : conflicts)
		{
			Calendar startTime = Calendar.getInstance();
			String result = phonesToResult.get(conflict.getPhone());
			if (StringUtils.isEmpty(result))
				throw new BusinessException("Неразрешенный конфликт по телефону");
			//удалить телефон у всех конфликтующих клиентов
			else if (result.equals(RESOLVED_TO_DELETE.name()))
			{
				conflict.setStatus(RESOLVED_TO_DELETE);
				conflict.setOwnerId(null);
			}
			//оставить у клиента, выбранного сотрудником
			else
			{
				conflict.setStatus(RESOLVED_TO_OWNER);
				conflict.setOwnerId(Long.parseLong(result));
			}
			conflict.setEmployeeInfo(employeeFio);
			writeLog(startTime, conflict);

			service.setConflictDecision(conflict);
		}
	}

	/**
	 * @return конфликты по клиенту
	 */
	public List<Conflict> getConflicts()
	{
		return Collections.unmodifiableList(conflicts);
	}

	private void writeLog(Calendar startTime, Conflict conflict)
	{
		LogWriter logWriter = OperationLogFactory.getLogWriter();
		DefaultLogDataReader reader = new DefaultLogDataReader("АС Мигратор. Операция разрешение конфликтов.");
		reader.setKey(Constants.EDIT_ERMB_MIGRATION_CONFLICT_KEY);
		reader.setOperationKey(EDIT_CONFLICT_OPERATION_KEY);
		for (ConflictedClient client : conflict.getClients())
		{
			String clientName = String.format("%s %s %s (%s)", client.getFirstName(), client.getMiddleName(), client.getLastName(), client.getDocument());
			String clientChangesMessage = String.format(
					"Клиент %s %s владельцем телефона %s",
					clientName,
					client.getId().equals(conflict.getOwnerId()) ? "стал(а)" : "больше не является",
					conflict.getPhone()
			);
			reader.addParametersReader(new SimpleLogParametersReader(clientName, "информация о состоянии клиента", clientChangesMessage));
		}

		try
		{
			logWriter.writeActiveOperation(reader, startTime, Calendar.getInstance());
		}
		catch (Exception e)
		{
			log.error("Ошибка записи в журнал действий пользователя", e);
		}
	}
}
