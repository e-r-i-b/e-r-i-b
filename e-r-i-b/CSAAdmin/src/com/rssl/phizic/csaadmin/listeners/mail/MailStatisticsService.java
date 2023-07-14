package com.rssl.phizic.csaadmin.listeners.mail;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.listeners.generated.*;
import com.rssl.phizic.csaadmin.listeners.mail.converters.MailEmployeeStatisticsConverter;
import com.rssl.phizic.csaadmin.listeners.mail.converters.MailStatisticsConverter;
import com.rssl.phizic.csaadmin.listeners.mail.processors.JoinListProcessor;
import com.rssl.phizic.csaadmin.listeners.mail.processors.UnionListProcessor;
import com.rssl.phizic.gate.mail.statistics.EmployeeStatisticsRecord;
import com.rssl.phizic.gate.mail.statistics.MailDateSpan;
import com.rssl.phizic.gate.mail.statistics.MailStatisticsRecord;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 04.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ��������� ���������� �� �������
 */
public class MailStatisticsService
{
	private static final MultiNodeDataService multiNodeService = new MultiNodeDataService();
	private static final MailStatisticsConverter mailStatisticsConverter = new MailStatisticsConverter();
	private static final MailEmployeeStatisticsConverter mailEmployeeStatisticsConverter = new MailEmployeeStatisticsConverter();

	/**
	 * �������� ���������� �� ������� � ������� �������� �����
	 * @param parameters - ��������� ��� ��������� ����������
	 * @return - �����, ���������� ���������� �� ������� � ������� �������� �����
	 */
	public GetMailStatisticsResultType getMailStatistics(GetMailStatisticsParametersType parameters) throws AdminException
	{
		GetMailStatisticsResultType result = new GetMailStatisticsResultType();
		String request = multiNodeService.buildRequest(parameters, MultiNodeRequestType.MAIL_STATISTICS);
		Map<Long,List<MailStatisticsRecord>> multiNodeMap = multiNodeService.getMultiNodeData(request, MultiNodeRequestType.MAIL_STATISTICS);
		List<MailStatisticsRecord> resultList = JoinListProcessor.process(multiNodeMap, MultiNodeRequestType.MAIL_STATISTICS);
		MailStatisticsDataType[] mailStatisticsData = new MailStatisticsDataType[resultList.size()];
		for(int i=0; i < resultList.size(); i++)
			mailStatisticsData[i] = mailStatisticsConverter.convertToGate(resultList.get(i));
		result.setStatisticsData(mailStatisticsData);
		return result;
	}

	/**
	 * ��������� ���������� �� ������� � ������� �����������
	 * @param parameters - ��������� ��� ��������� ����������
	 * @return - �����, ���������� ���������� �� ������� � ������� �����������
	 */
	public GetMailEmployeeStatisticsResultType getEmployeeStatistics(GetMailEmployeeStatisticsParametersType parameters) throws AdminException
	{
		GetMailEmployeeStatisticsResultType result = new GetMailEmployeeStatisticsResultType();
		String request = multiNodeService.buildRequest(parameters, MultiNodeRequestType.MAIL_EMPLOYEE_STATISTICS);
		Map<Long,List<EmployeeStatisticsRecord>> multiNodeMap = multiNodeService.getMultiNodeData(request, MultiNodeRequestType.MAIL_EMPLOYEE_STATISTICS);
		List<EmployeeStatisticsRecord> resultList = UnionListProcessor.process(multiNodeMap);
		MailEmployeeStatisticsDataType[] mailStatisticsData = new MailEmployeeStatisticsDataType[resultList.size()];
		for(int i=0; i < resultList.size(); i++)
			mailStatisticsData[i] = mailEmployeeStatisticsConverter.convertToGate(resultList.get(i));
		result.setStatisticsData(mailStatisticsData);
		return result;
	}

	/**
	 * ��������� �������� ������� ������ �� ������ �����������
	 * @param parameters - ��������� ��� ��������� ����������
	 * @return �����, ���������� ������� ������� ������ �� ������ �����������
	 */
	public GetMailAverageTimeResultType getMailAverageTime(GetMailAverageTimeParametersType parameters) throws AdminException
	{
		GetMailAverageTimeResultType result = new GetMailAverageTimeResultType();
		String request = multiNodeService.buildRequest(parameters, MultiNodeRequestType.MAIL_AVERAGE_TIME);
		Map<Long,MailDateSpan> multiNodeMap = multiNodeService.getMultiNodeData(request, MultiNodeRequestType.MAIL_AVERAGE_TIME);
		long count = 0;
		long milliseconds = 0;
		for(MailDateSpan nodeMailAverageTime: multiNodeMap.values())
		{
			milliseconds += nodeMailAverageTime.getMilliseconds() * nodeMailAverageTime.getCount();
			count += nodeMailAverageTime.getCount();
		}
		if(count == 0)
			return result;
		MailAverageTimeType averageTime = new MailAverageTimeType(milliseconds/count,count);
		result.setMailAverageTime(averageTime);
		return result;
	}

	/**
	 * ��������� ���� ������� ������ ���������������� ���������� ����������
	 * @param parameters - ��������� ��� ��������� ����������
	 * @return �����, ���������� ���� ������� ������ ���������������� ���������� ����������
	 */
	public GetFirstMailDateResultType getFirstMailDate(GetFirstMailDateParametersType parameters) throws AdminException
	{
		GetFirstMailDateResultType result = new GetFirstMailDateResultType();
		String request = multiNodeService.buildRequest(parameters, MultiNodeRequestType.MAIL_FIRST_DATE);
		Map<Long,Calendar> multiNodeMap = multiNodeService.getMultiNodeData(request, MultiNodeRequestType.MAIL_FIRST_DATE);
		Calendar mailFirstDate = null;
		for(Calendar nodeMailFirstDate: multiNodeMap.values())
			if(mailFirstDate == null || DateHelper.nullSafeCompare(nodeMailFirstDate,mailFirstDate) < 0)
				mailFirstDate = nodeMailFirstDate;
		if(mailFirstDate != null)
			result.setFirstDate(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(mailFirstDate));
		return result;
	}


}
