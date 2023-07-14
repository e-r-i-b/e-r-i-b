package com.rssl.phizic.csaadmin.listeners.mail.converters;

import com.rssl.phizic.csaadmin.listeners.generated.MailStatisticsDataType;
import com.rssl.phizic.gate.mail.statistics.MailStatisticsRecord;

/**
 * @author mihaylov
 * @ created 11.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Конвертер статистики обработки писем в гейтовое представление.
 */
public class MailStatisticsConverter implements MultiNodeEntityConverter<MailStatisticsDataType,MailStatisticsRecord>
{

	public MailStatisticsDataType convertToGate(MailStatisticsRecord entity)
	{
		MailStatisticsDataType gateData = new MailStatisticsDataType();
		gateData.setState(entity.getState());
		gateData.setCounter(entity.getCounter());
		return gateData;
	}
}
