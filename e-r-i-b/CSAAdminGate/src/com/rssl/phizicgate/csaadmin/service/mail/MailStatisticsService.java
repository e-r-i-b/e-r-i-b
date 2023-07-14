package com.rssl.phizicgate.csaadmin.service.mail;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mail.statistics.EmployeeStatisticsRecord;
import com.rssl.phizic.gate.mail.statistics.MailDateSpan;
import com.rssl.phizic.gate.mail.statistics.MailStatisticsRecord;
import com.rssl.phizic.utils.xml.xstream.XStreamSerializer;
import com.rssl.phizicgate.csaadmin.service.CSAAdminServiceBase;
import com.rssl.phizicgate.csaadmin.service.generated.*;

import java.util.*;

/**
 * @author mihaylov
 * @ created 04.06.14
 * @ $Author$
 * @ $Revision$
 *
 * —ервис получени€ статистики по письмам из ÷—ј јдмин
 */
public class MailStatisticsService extends CSAAdminServiceBase
{
	/**
	 *  онструктор
	 */
	public MailStatisticsService()
	{
		super(null);
	}

	/**
	 * ѕолучить статистику по письмам
	 * @param parameters - параметры по которым получаем статистику
	 * @return статистика
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<MailStatisticsRecord> getMailStatistics(Map<String,Object> parameters) throws GateException, GateLogicException
	{
		RequestData data = new RequestData();
		GetMailStatisticsParametersType mailStatisticsParameters = new GetMailStatisticsParametersType();
		mailStatisticsParameters.setParameters(XStreamSerializer.serialize(parameters));
		data.setGetMailStatisticsRq(mailStatisticsParameters);
		ResponseData response = process(data);
		List<MailStatisticsRecord> result = new ArrayList<MailStatisticsRecord>();
		for(MailStatisticsDataType statisticsData: response.getGetMailStatisticsRs().getStatisticsData())
		{
			MailStatisticsRecord record = new MailStatisticsRecord();
			record.setState(statisticsData.getState());
			record.setCounter(statisticsData.getCounter());
			result.add(record);
		}
		return result;
	}

	/**
	 * ѕолучить статистику по письмам в разрезе сотрудников
	 * @param parameters - параметры по которым получаем статистику
	 * @return статистика по письмам в разрезе сотрудников
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<EmployeeStatisticsRecord> getMailEmployeeStatistics(Map<String,Object> parameters) throws GateException, GateLogicException
	{
		RequestData data = new RequestData();
		GetMailEmployeeStatisticsParametersType mailStatisticsParameters = new GetMailEmployeeStatisticsParametersType();
		mailStatisticsParameters.setParameters(XStreamSerializer.serialize(parameters));
		data.setGetMailEmployeeStatisticsRq(mailStatisticsParameters);
		ResponseData response = process(data);
		return parseEmployeeStatisticList(response.getGetMailEmployeeStatisticsRs().getStatisticsData());
	}

	/**
	 * ѕолучить среднее врем€ обработки письма
	 * @param parameters - параметры писем по которым получаем статистику
	 * @return среднее врем€ обработки письма
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public MailDateSpan getAverageTime(Map<String,Object> parameters) throws GateException, GateLogicException
	{
		RequestData data = new RequestData();
		GetMailAverageTimeParametersType mailAverageTimeParameters = new GetMailAverageTimeParametersType();
		mailAverageTimeParameters.setParameters(XStreamSerializer.serialize(parameters));
		data.setGetMailAverageTimeRq(mailAverageTimeParameters);
		ResponseData response = process(data);
		MailAverageTimeType averageTime = response.getGetMailAverageTimeRs().getMailAverageTime();
		if (averageTime == null)
			return null;
		return new MailDateSpan(averageTime.getMilliseconds(),averageTime.getCount());
	}

	/**
	 * ѕолучить дату первого письма
	 * @param parameters - параметры писем по которым получаем статистику
	 * @return среднее врем€ обработки письма
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public Calendar getFirstMailDate(Map<String,Object> parameters) throws GateException, GateLogicException
	{
		RequestData data = new RequestData();
		GetFirstMailDateParametersType getFirstMailDateParameters = new GetFirstMailDateParametersType();
		getFirstMailDateParameters.setParameters(XStreamSerializer.serialize(parameters));
		data.setGetFirstMailDateRq(getFirstMailDateParameters);
		ResponseData response = process(data);
		return parseCalendar(response.getGetFirstMailDateRs().getFirstDate());
	}

	private List<EmployeeStatisticsRecord> parseEmployeeStatisticList(MailEmployeeStatisticsDataType[] employeeStatisticsData)
	{
		List<EmployeeStatisticsRecord> result = new ArrayList<EmployeeStatisticsRecord>();
		for(MailEmployeeStatisticsDataType statisticsData: employeeStatisticsData)
		{
			EmployeeStatisticsRecord record = new EmployeeStatisticsRecord();
			record.setId(statisticsData.getId());
			record.setNodeId(statisticsData.getNodeId());
			record.setArriveTime(parseCalendar(statisticsData.getArriveTime()));
			record.setProcessingTime(parseCalendar(statisticsData.getProcessingTime()));
			record.setState(statisticsData.getState());
			record.setEmployeeFIO(statisticsData.getEmployeeFIO());
			record.setAreaName(statisticsData.getAreaName());
			result.add(record);
		}
		return result;
	}

}
