package com.rssl.phizic.operations.ext.sbrf.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.mail.statistics.MailDateSpan;
import com.rssl.phizic.gate.mail.statistics.MailStatisticsRecord;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.List;

/**
 * Выгрузка статистики обработки обращения клиентов
 * @author komarov
 * @ created 04.09.13 
 * @ $Author$
 * @ $Revision$
 */

public class MailStatisticsExportOperation extends MailStatisticsOperation implements ViewEntityOperation
{
	private static final String DELIMITER = ";";
	private static final String STRING_DELIMITER = "\n";


	private byte[] data;

	/**
	 * Инициализация операции
	 * @param fromDate - дата начала выгрузки
	 * @param toDate - дата окончания выгрузки
	 * @param query - кверя для получения статистики по статусам
	 * @param averageTimeQuery - кверя для получения среднего времени ответа
	 * @param firsMailQuery - кверя для получения даты певого письма в статистике
	 * @throws BusinessException
	 */
	public void initialize(Calendar fromDate, Calendar toDate, Query query, Query averageTimeQuery, Query firsMailQuery) throws BusinessException
	{
		List<MailStatisticsRecord> statistics =  null;
		String averageResponseTime = "0";
		Calendar firstMailDate = null;
		try
		{
			statistics = query.executeList();
			List<MailDateSpan> mailDateSpanList = averageTimeQuery.executeList();
			if(CollectionUtils.isNotEmpty(mailDateSpanList))
				averageResponseTime = mailDateSpanList.get(0) != null ? mailDateSpanList.get(0).toString(): "0";

			firstMailDate = firsMailQuery.executeUnique();
		}
		catch (DataAccessException dae)
		{
			throw new BusinessException("Не удалось получить данные для выгрузки" , dae);
		}
		StringBuilder builder = new StringBuilder();

		addEarliestMailRow(builder, fromDate, toDate, firstMailDate);
		addResponseTimeRow(builder,averageResponseTime);
		addHeader(builder);
		addStatistics(builder, statistics);
		data = builder.toString().getBytes();
	}

	private void addEarliestMailRow(StringBuilder builder, Calendar fromDate, Calendar toDate, Calendar firstMailDate)
	{
		builder.append("Обращения клиентов  c ");
		builder.append(DateHelper.formatDateToStringWithPoint(fromDate));
		builder.append(" по ");
		builder.append(DateHelper.formatDateToStringWithPoint(toDate));
		builder.append(" (первое письмо от ");
		builder.append(DateHelper.formatDateToStringWithPoint(firstMailDate));
		builder.append(")");
		builder.append(STRING_DELIMITER);
	}

	private void addResponseTimeRow(StringBuilder builder, String averageTime) throws BusinessException
	{
		builder.append("Среднее время обработки: ");
		builder.append(averageTime);
		builder.append(STRING_DELIMITER);
	}

	private void addHeader(StringBuilder builder)
	{
		builder.append("Статус");
		builder.append(DELIMITER);
		builder.append("Количество писем");
		builder.append(STRING_DELIMITER);
	}

	private void addStatistics(StringBuilder builder, List<MailStatisticsRecord> statistics)
	{
		for(MailStatisticsRecord stat : statistics)
		{
			builder.append(MailHelper.getStateDescription(stat.getState()));
			builder.append(DELIMITER);
			builder.append(stat.getCounter());
			builder.append(STRING_DELIMITER);
		}
	}

	public byte[] getEntity()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return data;
	}
}
