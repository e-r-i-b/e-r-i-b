package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.migration.list.config.ErmbListMigrationConfig;
import com.rssl.phizic.business.ermb.migration.list.event.ReverseMigrationEvent;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.OperationBase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Операция отката миграции клиентов из МБК/МБВ в ЕРИБ
 * @author Puzikov
 * @ created 09.12.13
 * @ $Author$
 * @ $Revision$
 */

public class RollbackListMigrationOperation extends OperationBase
{
	private static final String SUCCESS_MESSAGE = "Откат миграции успешно запущен. Информация об исполнении " +
			"доступна в логах мигратора вида Revert_%s_N.log для каждого блока приложения отдельно";

	//Дата отката
	private Calendar rollbackDate;

	/**
	 * Проинициализировать операцию
	 * @param rollbackDate дата отката
	 */
	public void initialize(Calendar rollbackDate)
	{
		this.rollbackDate = rollbackDate;
	}

	/**
	 * Запустить процесс отката
	 */
	public void rollback() throws BusinessLogicException, BusinessException
	{
		if (!ConfigFactory.getConfig(ErmbListMigrationConfig.class).getRollbackAccess())
			throw new BusinessLogicException("Нет доступа к операции отката миграции");

		try
		{
			EventSender.getInstance().sendEvent(new ReverseMigrationEvent(rollbackDate));
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		writeLog(rollbackDate);
	}

	/**
	 * @return статус задачи в случае успешного исполнения
	 */
	public String getStatus()
	{
		String currentDateString = new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime());
		return String.format(SUCCESS_MESSAGE, currentDateString);
	}

	private void writeLog(Calendar startTime) throws BusinessException
	{
		try
		{
			LogWriter logWriter = OperationLogFactory.getLogWriter();
			DefaultLogDataReader reader = new DefaultLogDataReader("АС Мигратор. Операция отката миграции.");
			String rollbackDateString = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rollbackDate.getTime());
			SimpleLogParametersReader parametersReader = new SimpleLogParametersReader("Откат миграции на дату " + rollbackDateString);
			reader.addParametersReader(parametersReader);
			logWriter.writeActiveOperation(reader, startTime, Calendar.getInstance());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
