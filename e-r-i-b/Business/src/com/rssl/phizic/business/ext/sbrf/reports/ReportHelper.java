package com.rssl.phizic.business.ext.sbrf.reports;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.ext.sbrf.reports.it.CountOperationsDateITReport;
import com.rssl.phizic.business.ext.sbrf.reports.it.ProactiveReport;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.LogModule;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.CollectionUtils;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Mescheryakova
 * @ created 27.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class ReportHelper
{
	private final static String  CURRENCY = "Currency";
	private final static String COUNT = "Count";
	private final static String SUCCESS_COUNT = "SuccessCount";
	private final static String PERCENT_ERROR  = "PercentError";
	private final static String SMALL_TIME_OPERATION  = "SmallTimeOperation";
	private final static String LONG_TIME_OPERATION  = "LongTimeOperation";
	private final static String AVERAGE_TIME_OPERATION  = "AverageTimeOperation";
	private final static String AMOUNT = "Amount";
	private final static String ERROR = "Error";
	private final static String SPACE_SEPARATOR = " ";

	private static final DepartmentService departmentService = new DepartmentService();
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * �������������� ������ '�������� � ���� �� ������ �� ���' � ��� �����
	 * @param reports - ������ �������� ��� ������ '�������� � ���� �� ������ �� ���'
	 * @return
	 *  key => { key2 => value2}
	 * ��� key - ��� ������ ���� "id ��|��� ��|id ���|id ���",
	 *     key2 - ������������ ��������
	 *     value2 - ���������� �������� ���� key2
	 */

	public static Map<String, Map<String, Object>> getMapOperationVSPReport(List<CountOperationsVSPReport> reports)
	{
		Map<String, Map<String, Object>>  mapReports = new TreeMap<String, Map<String, Object>>();

		for (CountOperationsVSPReport report : reports)
		{
			String key = report.getTb_id() + "|" + report.getTb_name() + "|" + report.getOsb_id() + "|" + report.getVsp_id();
			Map<String, Object> mapReportsValue = new HashMap<String, Object>();
			if (mapReports.containsKey(key))
			{
				mapReportsValue = mapReports.get(key);
			}

			mapReportsValue.put(report.getType(), report.getCount());
			mapReports.put(key, mapReportsValue);
		}

		return mapReports;
	}

	/**
	 * �������������� ������ "�������� � ���� �� ������ �� ���������" � ��� ������ �����
	 * @param reports - ������ �������� ��� ������ "�������� � ���� �� ������ �� ���������"
	 * @return
	 *  key => {
	 *      [
	 *          CURRENCY => currency,
	 *          AMOUNT   => amount,
	 *          COUNT    => count 
	 *      ]
	 * }
	 * ��� key - ��� ��������
	 * CURRENCY => currency  - ��� ������ ��� ��������
	 * AMOUNT   => amount    - ����� ��������
	 * COUNT    => count     -  ���������� ��������
	 */
	public static Map<String, List< Map<String, Object> >> getMapOperationSBRFReport(List<CountOperationsSBRFReport> reports)
	{
		Map<String, List< Map<String, Object> >>  mapReports = new HashMap<String, List< Map<String, Object> >>();

		for (CountOperationsSBRFReport report : reports)
		{
			String key = report.getType();
			List< Map<String, Object> > params = new ArrayList< Map<String, Object> >();
			if (mapReports.containsKey(key))
			{
				params = mapReports.get(key);
			}

			Map<String, Object> mapReportsValue = new HashMap<String, Object>();

			if (!StringHelper.isEmpty(report.getCurrency()) && !report.getCurrency().equals(SPACE_SEPARATOR))
			{
				mapReportsValue.put(AMOUNT, String.format("%.2f%n", report.getAmount()));
				mapReportsValue.put(CURRENCY, report.getCurrency());
			}
			else
			{
				mapReportsValue.put(AMOUNT, null);
				mapReportsValue.put(CURRENCY, null);
			}

			mapReportsValue.put(COUNT, report.getCount());
			params.add(mapReportsValue);
			mapReports.put(key, params);
		}
		// ����� �� ������ ����� ������ � 10 ��������� ���������
		mapReports.put("cardsReport",  mapReports.get("cardsReportForLast10Operation"));
		return mapReports;
	}

	/**
	 * �������������� ������ "�������� � ���� �� ������ �� ��"   � ��� ������
	 * @param reports - ������ �������� ��� ������ "�������� � ���� �� ������ �� ��"
	 * @return
	 *  "id ��, �������� ��" => {
	 *
	 *      �������� �������� => [
	 *               CURRENCY => ������ ��������
	 *               AMOUNT => �����
	 *               COUNT  => ���������� ��������
	 *          ]
	 *  }
	 */

	public static Map<String, Map<String, List< Map<String, Object> >>> getMapOperationTBReport(List<CountOperationsTBReport> reports)
	{
		Map<String, Map<String, List< Map<String, Object> >>> mapReports = new TreeMap<String, Map<String, List< Map<String, Object> >>>();

		for (CountOperationsTBReport report : reports)
		{
			String tbKey = report.getTb_id() + ", " + report.getTb_name();

			Map<String, Object> reportValue = new HashMap<String, Object>();

			if (!StringHelper.isEmpty(report.getCurrency()) && !report.getCurrency().equals(SPACE_SEPARATOR))
			{
				reportValue.put(AMOUNT, String.format("%.2f%n", report.getAmount()));
				reportValue.put(CURRENCY, report.getCurrency());
			}
			else
			{
				reportValue.put(AMOUNT, null);
				reportValue.put(CURRENCY, null);
			}
			
			reportValue.put(COUNT, report.getCount());


			Map<String, List< Map<String, Object> >>  mapParams = new HashMap<String, List< Map<String, Object> >>() ;
			if (mapReports.containsKey(tbKey))
			{
				mapParams = mapReports.get(tbKey);
			}

			List< Map<String, Object> > params = new ArrayList< Map<String, Object> >();

			if ( mapParams.containsKey(report.getType()) )
			{
				 params = mapParams.get( report.getType() );
			}
			params.add(reportValue);
			mapParams.put( report.getType(), params);

			// ����� �� ������ ����� ������ � 10 ��������� ���������
			if (report.getType().equals("cardsReportForLast10Operation") && mapParams.get("cardsReportForLast10Operation") != null)
				mapParams.put("cardsReport",  mapParams.get("cardsReportForLast10Operation"));

			mapReports.put( tbKey, mapParams);
		}

		return mapReports;
	}

	/**
	 * �������������� ������ "����� � �������� ���������� �������� �� ���� (����������)" � ��� ����� �����
	 * @param reports - ������ ������ ��� ������ "����� � �������� ���������� �������� �� ���� (����������)"
	 * @return
	 *      key => {
	 *          typeOperation => {
	 *              COUNT => count,
	 *              ERROR => error,
	 *              PERCENT_ERROR => percent_error,
	 *              SUCCESS_COUNT => success_count
	 *          }
	 *      }
	 *
	 *  ��� key - ��� ������ ����: id ��| �������� ��|���� �������� (������ � ���� yyyy.mm.dd)
	 *      typeOperation  - �������� ��������
	 *      count - ����� ���������� �������� typeOperation
	 *      error - ��������� ���������� ��������
	 *      percent_error - ������� ������
	 *      success_count - ���������� �������� ��������
	 */

	public static Map<String, Map<String, Map<String, Object> >> getMapOperationTBPeriodReport(List<CountOperationsDateITReport> reports, Calendar startDate, Calendar endDate)
	{
		Map<String, Map<String,  Map<String, Object> >> mapReports = getEmptyMapOperationTBPeriodReport(reports, startDate, endDate);

		for (CountOperationsDateITReport report : reports)
		{
			String tbInfoDate = report.getTb_id() + "|" + report.getTb_name() + "|" + String.format("%1$tY.%1$tm.%1$td", report.getOperation_date());

			Map< String, Map<String, Object> > typeOperation = new HashMap<String, Map<String, Object>>();
			if (mapReports.containsKey(tbInfoDate))
			{
			 	typeOperation = mapReports.get(tbInfoDate);
			}

			Map<String, Object> valueReport = new HashMap<String, Object>();
			valueReport.put(COUNT, report.getCount());
			valueReport.put(ERROR, report.getCountErrorOperations());
			valueReport.put(PERCENT_ERROR, report.getPercentErrorOperations());
			valueReport.put(SUCCESS_COUNT, report.getCountSuccessOperations());

			typeOperation.put(report.getType(), valueReport);
			mapReports.put(tbInfoDate, typeOperation);
		}

		return mapReports;
	}

	/**
	 * ���������� ��� ��� ������   "����� � �������� ���������� �������� �� ���� (����������)"
	 * �� ����� ���������� ���������� �� ������� �� � ��������� ������� ������������ ������.
	 * �����, �.�. � ���� �� �������� ������ �� ����, � ���. �� ����������� �� ����� ��������,
	 * � � ������ ����� �������� ���� �� ��� ����.
	 * @param reports  - ������ �������� ����� ������
	 * @param startDate  - ����  ������ ������� ������������ ������
	 * @param endDate   - ���� ��������� ������� ������������ ������
	 * @return  - ���, ��������� �� ������ � ������ ��������.
	 */
	private static Map<String,  Map<String, Map<String, Object> >> getEmptyMapOperationTBPeriodReport(List<CountOperationsDateITReport> reports, Calendar startDate, Calendar endDate)
	{
		Map<String, Map<String,  Map<String, Object> >> mapReports = new TreeMap<String, Map< String, Map<String, Object> >> ();
		for (CountOperationsDateITReport report : reports)
		{
			Calendar firstDate = (Calendar) startDate.clone();
			while (firstDate.compareTo(endDate) <= 0 )
			{
				String tbInfoDate = report.getTb_id() + "|" + report.getTb_name() + "|" + String.format("%1$tY.%1$tm.%1$td", firstDate);
				firstDate.add(Calendar.DATE, 1);
				mapReports.put(tbInfoDate, new HashMap<String, Map<String, Object>>());
			}
		}

		return mapReports;
	}

	/**
	 * �������������� ������ "����� �� ������������ �����������" � ��� ����� �����
	 * @param reports - ������ �������� ��� ������ "����� �� ������������ �����������"
	 * @return
	 *  key => {
	 *      typeOperation => {
				 count,
				 percentError,
				 SmallTimeOperation,
				 LongTimeOperation,
				 AverageTimeOperation
	 *      }
	 * }
	 * ���  key  - ��� ������ ����: id ��| �������� ��
	 * typeOperation - ��� ��������
	 * COUNT    => count     -  ���������� ��������
	 * PERCENT_ERROR => percentError    - ������� ���������� ��������
	 * SMALL_TIME_OPERATION  => SmallTimeOperation   - ������� �������� �� �������� ������� ����� 5 ������
	 * AVERAGE_TIME_OPERATION  => LongTimeOperation  - ������� �������� �� �������� ������� �� 5 �� 10 ������
	 * LONG_TIME_OPERATION  => AverageTimeOperation  - ������� �������� �� �������� ������� ����� 10 ������
	 */
	public static Map<String, Map<String,  Map<String, Object> >> getMapProactiveMonitoringReport(List<ProactiveReport> reports)
	{
		Map<String, Map<String,  Map<String, Object> >> mapReports = new TreeMap<String, Map<String, Map<String, Object> >>();

		for (ProactiveReport report : reports)
		{
			String tbInfo = report.getTb_id() + "|" + report.getTb_name();

			Map<String, Map<String, Object> > typeOperation = new HashMap<String, Map<String, Object> >();

			if (mapReports.containsKey(tbInfo))
			{
			 	typeOperation = mapReports.get(tbInfo);
			}

			Map<String, Object> valueReport = new HashMap<String, Object>();
			valueReport.put(COUNT, report.getCount());
			valueReport.put(PERCENT_ERROR, report.getPercentOperations(report.getCountErrorOperations()));
			valueReport.put(SMALL_TIME_OPERATION, report.getPercentOperations(report.getSmallTime()));
			valueReport.put(AVERAGE_TIME_OPERATION, report.getPercentOperations(report.getAverageTime()));
			valueReport.put(LONG_TIME_OPERATION, report.getPercentOperations(report.getLongTime()));

			typeOperation.put(report.getType(), valueReport);
			mapReports.put(tbInfo, typeOperation);
		}
		return mapReports;
	}

	/**
	 * �������������� ������ "����� �� ������������ �����������" � ��� ������
	 * ������� ����� �� ���� ��������� (���� �����)
	 * @param reports - ������ �������� ��� ������ "����� �� ������������ �����������"
	 * @param allBank - �� ����� ����� �������� ����� ��� � ������� ��
	 * @return
	 * COUNT    => count     -  ���������� ��������
	 * PERCENT_ERROR => percentError    - ������� ���������� ��������
	 * SMALL_TIME_OPERATION  => SmallTimeOperation   - ������� �������� �� �������� ������� ����� 5 ������
	 * AVERAGE_TIME_OPERATION  => LongTimeOperation  - ������� �������� �� �������� ������� �� 5 �� 10 ������
	 * LONG_TIME_OPERATION  => AverageTimeOperation  - ������� �������� �� �������� ������� ����� 10 ������
	 */
	public static  Map<String, Object> getSumMapProactiveMonitoringReport(List<ProactiveReport> reports, boolean allBank)
	{
		long count = 0;
		long error = 0;
		long smallTime = 0;
		long averageTime = 0;
		long longTime = 0;
		for (ProactiveReport report : reports)
		{
			if (!report.getType().equals("loginPage")  || allBank)
			{
				count = count + report.getCount();
				error = error + report.getCountErrorOperations();
				smallTime = smallTime + report.getSmallTime();
				averageTime = averageTime + report.getAverageTime();
				longTime = longTime + report.getLongTime();
			}
		}
		Map<String, Object> valueReport = new HashMap<String, Object>();
		valueReport.put(COUNT, count);
		valueReport.put(PERCENT_ERROR, getPercentOperations(count, error));
		valueReport.put(SMALL_TIME_OPERATION, getPercentOperations(count, smallTime));
		valueReport.put(AVERAGE_TIME_OPERATION, getPercentOperations(count, averageTime));
		valueReport.put(LONG_TIME_OPERATION, getPercentOperations(count, longTime));

		return valueReport;
	}

	/**
	 * ������� ������� �������� �� ������ �����
	 * @param allCount  - ����� ����� ��������
	 * @param count  - ���������� ��������
	 * @return ������ ���� 0.00%
	 */
	private static String getPercentOperations(long allCount, long count)
	{
		double percentErrors = (allCount <= 0 ? 0 : (double) count * 100 / allCount);
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		return decimalFormat.format(percentErrors) + '%';
	}

	/**
	 * @param departmentId - ������������� ������������
	 * @return ���� �������� ������������� ������������. �������� �� -> �������� ��� -> �������� ���
	 */
	public static Stack<String> getDepartmentNames(Long departmentId)
	{
		Stack<String> departmentNames = new Stack<String>();
		try
		{
			Department department = departmentService.findById(departmentId);

			if (DepartmentService.isVSP(department))
			{
				departmentNames.add(department.getName());
			}
			Department osb = departmentService.getOSBByOffice(department);
			if (osb != null)
			{
				departmentNames.add(osb.getName());
			}
			departmentNames.add(departmentService.getTB(department).getName());
		}
		catch (BusinessException e)
		{
			log.info("���������� �������� ����������� �� ����������� �������������� id = " + departmentId, e);
		}
		return departmentNames;
	}

	/**
	 * �������������� ������ ������� ������ � ��� ��� �������� ������ �� �������� 
	 * @param reportList - ������ ������� ������
	 * @return Map<�������� ��, MAP<������������ ���, List<������ ������ ��� ������>>>
	 */
	public static Map<String, Map<String, List<CountIOSReport>>> getCountIOSReportMap(List<CountIOSReport> reportList)
	{
		Map<String, Map<String, List<CountIOSReport>>> reportTbMap = new HashMap<String, Map<String, List<CountIOSReport>>>();
		if(CollectionUtils.isEmpty(reportList))
			return reportTbMap;
		for(CountIOSReport report : reportList)
		{
			String tbName = report.getTbName();
			Map<String, List<CountIOSReport>> reportOsbMap = reportTbMap.get(tbName);
			if(MapUtils.isEmpty(reportOsbMap))
			{
				reportOsbMap = new HashMap<String, List<CountIOSReport>>();
				reportTbMap.put(tbName,reportOsbMap);
			}
			String osbName = report.getOsbName();
			List<CountIOSReport> reportOSBList = reportOsbMap.get(osbName);
			if(CollectionUtils.isEmpty(reportOSBList))
			{
				reportOSBList = new ArrayList<CountIOSReport>();
				reportOsbMap.put(osbName,reportOSBList);
			}
			reportOSBList.add(report);
		}
		return reportTbMap;
	}

	/**
	 * @param reportOsbMap ���� � ������� ��� ����������� ��
	 * @return ���������� ����� � ������ ��� ������� ��
	 */
	public static int getTbColspanValue(Map<String, List<CountIOSReport>> reportOsbMap)
	{
		int colspanValue = 0;
		for(List<CountIOSReport> reportOsbList : reportOsbMap.values())
		{
			colspanValue += reportOsbList.size() + 1;
		}
		return colspanValue;
	}
}
