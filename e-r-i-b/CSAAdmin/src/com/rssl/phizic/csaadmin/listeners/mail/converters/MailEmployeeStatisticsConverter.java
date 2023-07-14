package com.rssl.phizic.csaadmin.listeners.mail.converters;

import com.rssl.phizic.csaadmin.listeners.generated.MailEmployeeStatisticsDataType;
import com.rssl.phizic.gate.mail.statistics.EmployeeStatisticsRecord;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

/**
 * @author mihaylov
 * @ created 18.06.14
 * @ $Author$
 * @ $Revision$
 *
 *  онвертер статистики по письмам в разрезе сотрудника в гейтовое представление.
 */
public class MailEmployeeStatisticsConverter implements MultiNodeEntityConverter<MailEmployeeStatisticsDataType,EmployeeStatisticsRecord>
{

	public MailEmployeeStatisticsDataType convertToGate(EmployeeStatisticsRecord entity)
	{
		MailEmployeeStatisticsDataType gateData = new MailEmployeeStatisticsDataType();
		gateData.setId(entity.getId());
		gateData.setArriveTime(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(entity.getArriveTime()));
		if(entity.getProcessingTime() != null)
			gateData.setProcessingTime(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(entity.getProcessingTime()));
		gateData.setState(entity.getState());
		gateData.setEmployeeFIO(entity.getEmployeeFIO());
		gateData.setAreaName(entity.getAreaName());
		return gateData;
	}
}
