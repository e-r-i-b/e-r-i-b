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
	 * получаем информацию из базы по запрошенным данным
	 * @param date - дата, на которую формируется отчет (параметр общий для всех отчетов)
	 * @param ids - строка разделенных ; id департаментов, по которым формируется отчет
	 * @param name - название запроса
	 * @param params - дополнительные параметры для запроса (у каждого отчета могут быть свои)
	 * @param instanceName - имя экземпляра БД
	 * @return отчет (список записей)
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
	 * получаем информацию из базы по запрошенным данным
	 * @param date - дата, на которую формируется отчет (параметр общий для всех отчетов)
	 * @param ids - список id департаментов, по которым формируется отчет
	 * @param name - название запроса
	 * @param params - дополнительные параметры для запроса (у каждого отчета могут быть свои)
	 * @param instanceName - имя экземпляра БД
	 * @return отчет (список записей)
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

	                // общие для всех отчетов параметры
	                query.setParameter("startDate", date);

	                // не во всех отчетах испольуются id департаментов
	                if (ids != null)
	                    query.setParameterList("ids", ids);

	                // получаем дополнительные параметры для запроса
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
	* Ищет отчеты с заданным статусом
	* @param state - статус
	* @param instanceName - имя экземпляра БД
	* return - список отчетов с заданным статусом
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
	 * Удаление отчета и всех его подотчетов
	 * @param report
	 * @param instanceName
	 * @throws BusinessException
	 */
	public void remove(final ReportAbstract report,  String instanceName) throws BusinessException
	{
		List<String> queryNames = getQueryNames(report);        // получаем список кверей для последующего получения списков полных отчетов

		for (String queryName : queryNames)
		{
			 List reports = getReports(report, queryName, instanceName);     // получение списка полного отчета
			 removeList(reports, instanceName);                              // удаление списка отчетов
		}

		simpleService.remove(report, instanceName);                          // удаление головного отчета
	}

	private void removeList(final List list, String instanceName) throws BusinessException
	{
		simpleService.removeList(list, instanceName);
	}

	/**
	 * Возвращает список имен запросов для получения списка отчетов на удаление
	 */
	private List<String> getQueryNames(final ReportAbstract report) throws BusinessException
	{
		List<String> queryNames = new ArrayList<String>();

		switch(report.getType())         // проверяем тип отчета
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
				throw new BusinessException("Неизвестный тип отчета");
		}

		return queryNames;
	}


	/**
	 * Получает список всех подробных отчетов, относящихся к общему report
	 * @param report  - отчет, для которого ищем подотчеты
	 * @param query - имя квери
	 * @param instanceName  -  имя БД
	 * @return - список подотчетов
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
	 * Возвращает минимальную и максимальную даты записей из Userlog
	 * @param instanceName - название бд
	 * @return массив объектов
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
