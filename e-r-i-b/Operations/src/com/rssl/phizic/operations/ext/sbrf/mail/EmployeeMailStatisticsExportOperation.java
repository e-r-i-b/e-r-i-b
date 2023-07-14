package com.rssl.phizic.operations.ext.sbrf.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.mail.statistics.EmployeeStatisticsRecord;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.DateHelper;

import java.util.List;

/**
 * Выгрузка отчёта по сотрудникам
 * @author komarov
 * @ created 22.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class EmployeeMailStatisticsExportOperation extends MailStatisticsOperation implements ViewEntityOperation
{
	private static final String DELIMITER = ";";
	private static final String STRING_DELIMITER = "\n";

	private byte[] data;

	/**
	 * Инициализирует операцию
	 * @param query кверя для получения статистики
	 * @throws BusinessException
	 */
	public void initialize(Query query) throws BusinessException
	{
		List<EmployeeStatisticsRecord> statistics =  null;
		try
		{
			statistics = query.executeList();
		}
		catch (DataAccessException dae)
		{
			throw new BusinessException("Не удалось получить данные для выгрузки" , dae);
		}
		StringBuilder builder = new StringBuilder();

		addHeader(builder);
		addStatistics(builder, statistics);
		data = builder.toString().getBytes();
	}

	private void addHeader(StringBuilder builder)
	{
		builder.append("Дата поступления");
		builder.append(DELIMITER);
		builder.append("Дата обработки");
		builder.append(DELIMITER);
		builder.append("Статус");
		builder.append(DELIMITER);
		builder.append("ФИО сотрудника");
		builder.append(DELIMITER);
		builder.append("Площадка");
		builder.append(STRING_DELIMITER);
	}

	private void addStatistics(StringBuilder builder, List<EmployeeStatisticsRecord> statistics)
	{
		for(EmployeeStatisticsRecord stat : statistics)
		{
			builder.append(DateHelper.toStringTimeWithoutSecond(DateHelper.toDate(stat.getArriveTime())));
			builder.append(DELIMITER);
			builder.append(DateHelper.toStringTimeWithoutSecond(DateHelper.toDate(stat.getProcessingTime())));
			builder.append(DELIMITER);
			builder.append(MailHelper.getStateDescription(stat.getState()));
			builder.append(DELIMITER);
			builder.append(stat.getEmployeeFIO());
			builder.append(DELIMITER);
			builder.append(stat.getAreaName());
			builder.append(STRING_DELIMITER);
		}
	}

	public byte[] getEntity()
	{
		return data;
	}
}
