package com.rssl.phizic.business.ext.sbrf.reports;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.hibernate.DataBaseTypeQueryHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

/**
 * @author Mescheryakova
 * @ created 29.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class ReportServiceSBRF
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * �������� ���������� �� ���� �� ����������� ������
	 * @param date - ����, �� ������� ����������� ����� (�������� ����� ��� ���� �������)
	 * @param ids - ������ ����������� ; id �������������, �� ������� ����������� �����
	 * @param name - �������� �������
	 * @param params - �������������� ��������� ��� ������� (� ������� ������ ����� ���� ����)
	 * @param instanceName - ��� ���������� ��
	 * @return ����� (������ �������)
	 * @throws BusinessException
	 */
	public List getReportInfo(final Calendar date, final String ids, final String name, final Map params, String instanceName) throws BusinessException
	{
		List<String> idsList = null;
		if (ids != null)
		{
			idsList = Arrays.asList(StringUtils.split(ids, ';'));
		}
		return getReportInfo(date, idsList, name, params, instanceName);
	}

	/**
	 * �������� ���������� �� ���� �� ����������� ������
	 * @param date - ����, �� ������� ����������� ����� (�������� ����� ��� ���� �������)
	 * @param ids - ������ id �������������, �� ������� ����������� �����
	 * @param name - �������� �������
	 * @param params - �������������� ��������� ��� ������� (� ������� ������ ����� ���� ����)
	 * @param instanceName - ��� ���������� ��
	 * @return ����� (������ �������)
	 * @throws BusinessException
	 */
	public List getReportInfo(final Calendar date, final List<String> ids, final String name, final Map params, String instanceName) throws BusinessException
	{
		try
        {
	        return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List>()
            {
                public List run(Session session) throws Exception
                {
	                Query query =  DataBaseTypeQueryHelper.getNamedQuery(session, name);

	                // ����� ��� ���� ������� ���������
	                query.setParameter("startDate", date);

	                // �� �� ���� ������� ����������� id �������������
	                if (ids != null)
	                    query.setParameterList("ids", ids);

	                // �������� �������������� ��������� ��� �������
	                if (MapUtils.isNotEmpty(params))
	                {
		                Set<String> keys = params.keySet();
		                Iterator iterator = keys.iterator();

		                while (iterator.hasNext())
		                {

		                    String key = (String) iterator.next();
			                Object value = params.get(key);
			                if(value instanceof List)
								query.setParameterList(key, (List)value);
			                else
			                    query.setParameter(key, value);
		                }
	                }
				    return  query.list();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
	}

	public ReportAbstract  update(ReportAbstract report, String instanceName) throws BusinessException
	{
		return simpleService.update(report, instanceName);
	}

	public <T> T add(final T obj, String instanceName) throws BusinessException
	{
		return simpleService.add(obj, instanceName);
	}

	/*
	* ���� ������ � �������� ��������
	* @param state - ������
	* @param instanceName - ��� ���������� ��
	* return - ������ ������� � �������� ��������
	*/
	public List<ReportAbstract> findByState(final char state, String instanceName ) throws BusinessException
	{
		try
        {
	        return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List>()
            {
                public List run(Session session) throws Exception
                {
	                Query query =  DataBaseTypeQueryHelper.getNamedQuery(session, "com.rssl.phizic.business.ext.sbrf.reports.findByState");
	                query.setParameter("state", state);
				    return  query.list();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
	}

	public ReportAbstract findById(final Long id, String instanceName) throws BusinessException
	{
		return simpleService.findById(ReportAbstract.class, id, instanceName);
	}

	/**
	 * �������� ������ � ���� ��� ����������
	 * @param report
	 * @param instanceName
	 * @throws BusinessException
	 */
	public void remove(final ReportAbstract report,  String instanceName) throws BusinessException
	{
		List<String> queryNames = getQueryNames(report);        // �������� ������ ������ ��� ������������ ��������� ������� ������ �������

		for (String queryName : queryNames)
		{
			 List reports = getReports(report, queryName, instanceName);     // ��������� ������ ������� ������
			 removeList(reports, instanceName);                              // �������� ������ �������
		}

		simpleService.remove(report, instanceName);                          // �������� ��������� ������
	}

	private void removeList(final List list, String instanceName) throws BusinessException
	{
		simpleService.removeList(list, instanceName);
	}

	/**
	 * ���������� ������ ���� �������� ��� ��������� ������ ������� �� ��������
	 */
	private List<String> getQueryNames(final ReportAbstract report) throws BusinessException
	{
		List<String> queryNames = new ArrayList<String>();

		switch(report.getType())         // ��������� ��� ������
		{
			case TypeReport.CONTRACT_TB:
				queryNames.add("ContractTBReport");
				break;
			case TypeReport.CONTRACT_OSB:
				queryNames.add("ContractOSBReport");
				break;
			case TypeReport.CONTRACT_VSP:
				queryNames.add("ContractVSPReport");
				break;
			case TypeReport.CONTRACT_OKR:
				queryNames.add("ContractOKRReport");
				break;
			case TypeReport.ACTIVE_USERS_TB:
				queryNames.add("ActiveUsersTBReport");
				break;
			case TypeReport.ACTIVE_USERS_VSP:
				queryNames.add("ActiveUsersVSPReport");
				break;
			case TypeReport.OPERATIONS_SBRF:
				queryNames.add("OperationsSBRFReport");
				break;
			case TypeReport.OPERATIONS_TB:
				queryNames.add("OperationsTBReport");
				break;
			case TypeReport.OPERATIONS_VSP:
				queryNames.add("OperationsVSPReport");
				break;
			case TypeReport.BUSINESS_PARAMS:
				queryNames.add("BusinessParamsReportTB");
				queryNames.add("BusinessParamsReportSBRF");
				break;
			case TypeReport.QUALITY_PERIOD:
				queryNames.add("QualityOperationsPeriod");
				break;
			case TypeReport.QUALITY_DATE:
				queryNames.add("QualityOperationsDate");
				break;
			case TypeReport.PROACTIVE_MONITORING:
				queryNames.add("ProactiveMonitoring");
				break;
			case TypeReport.SYSTEM_IDLE:
				queryNames.add("SystemIdleReportRemove");
				queryNames.add("SystemIdleAdditReportRemove");
				break;
			case TypeReport.COUNT_IOS_USER:
				queryNames.add("CountIOSReport");
				break;
			default:
				throw new BusinessException("����������� ��� ������");
		}

		return queryNames;
	}


	/**
	 * �������� ������ ���� ��������� �������, ����������� � ������ report
	 * @param report  - �����, ��� �������� ���� ���������
	 * @param query - ��� �����
	 * @param instanceName  -  ��� ��
	 * @return - ������ ����������
	 * @throws BusinessException
	 */
	private List getReports(final ReportAbstract report, final String query, final String instanceName) throws BusinessException
	{
		final String pathQuery = "com.rssl.phizic.operations.ext.sbrf.reports.ReportsListOperation.";
		final String fullQuery = pathQuery + query;

		try
        {
	        return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List>()
            {
                public List run(Session session) throws Exception
                {
	                Query query =  DataBaseTypeQueryHelper.getNamedQuery(session, fullQuery);
	                query.setParameter("extra_id", report);
				    return  query.list();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }

	}

	/**
	 * ���������� ����������� � ������������ ���� ������� �� Userlog
	 * @param instanceName - �������� ��
	 * @return ������ ��������
	 */
	public Calendar[] getPeriodUserLog(final String instanceName)    throws BusinessException
	{
		try
        {
	        return  HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Calendar[]>()
            {
                public Calendar[] run(Session session) throws Exception
                {
	                Query query =  DataBaseTypeQueryHelper.getNamedQuery(session, "com.rssl.phizic.business.ext.sbrf.reports.getMinMaxStartDate");
	                List list = query.list();
	                Calendar min = (Calendar) list.get(0);
	                Calendar max = (Calendar) list.get(1);

	                return  new Calendar[]{min, max};
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
	}
}
