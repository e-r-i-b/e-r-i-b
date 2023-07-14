package com.rssl.phizic.web.log;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeDataProvider;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.LogFilterOperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hudyakov
 * @ created 21.04.2009
 * @ $Author$
 * @ $Revision$
 */

public abstract class LogFilterBaseAction extends SaveFilterParameterAction
{
	protected static final List<Object[]> EMPTY_RESULT_LIST = new ArrayList<Object[]>();

	private static final String FORWARD_DOWNLOAD = "Download";
	private static final Integer DEFAULT_MAX_ROWS = 50;

	private static final String DATE_FORMAT_STRING = "%1$td.%1$tm.%1$tY";
	private static final String TIME_FORMAT_STRING = "%1$tH:%1$tM:%1$tS";
	public static final String APPLICATION_FIELD_NAME = "application";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("button.download", "download");
		return map;
	}

	public ActionForward download(ActionMapping mapping, ActionForm form,
	                              HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DownloadLogFilterBaseForm frm = (DownloadLogFilterBaseForm) form;
		LogFilterOperationBase operation = (LogFilterOperationBase) createListOperation(frm);

		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFilters());

		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(mapValuesSource, getFilterForm(frm, operation));
		if (formProcessor.process())
		{
			doFilter(formProcessor.getResult(), operation, frm);
			response.setContentType("application/x-file-download");
			response.setHeader("Content-disposition", "attachment; filename="+ getLogName());

			addLogParameters(new CompositeLogParametersReader(
					new SimpleLogParametersReader("Имя выгружаемого файла", getLogName()),
					new FormLogParametersReader("Параметры выгрузки", getFilterForm(frm, operation), frm.getFilters())
							));

			return mapping.findForward(FORWARD_DOWNLOAD);
		}
		else
		{
			saveErrors(request, formProcessor.getErrors());
			doFilter(getDefaultFilterParameters(frm, operation), operation, frm);
			return mapping.findForward(FORWARD_START);
		}
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase form, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
		Map<String, Object> filterParams = new HashMap<String, Object>();

		Calendar from = getDefaultFilterStartDate();
		Calendar to = getDefaultFilterEndDate();
		filterParams.put("toDate", String.format(DATE_FORMAT_STRING, to));
		filterParams.put("toTime", String.format(TIME_FORMAT_STRING, to));
		filterParams.put("fromDate", String.format(DATE_FORMAT_STRING, from) );
		filterParams.put("fromTime", String.format(TIME_FORMAT_STRING, from));
		filterParams.put("maxRows", DEFAULT_MAX_ROWS);
		filterParams.put(APPLICATION_FIELD_NAME, Application.PhizIC);

		return filterParams;
	}

	protected Calendar getDefaultFilterEndDate()
	{
		return Calendar.getInstance();
	}

	protected Calendar getDefaultFilterStartDate()
    {
	    Calendar date = Calendar.getInstance();
	    date.add(Calendar.DAY_OF_MONTH,-1);
	    return date;
    }

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws DataAccessException, BusinessException, BusinessLogicException
	{
		DownloadLogFilterBaseForm frm = (DownloadLogFilterBaseForm) form;
		boolean isFromStart = frm.isFromStart();

		List<Object[]> list = EMPTY_RESULT_LIST;;
		if (!isFromStart)
		{
			Query query = operation.createQuery(getQueryName(frm));
			fillQuery(query, filterParams);
			completeParametersForIndex(query);
			list = query.executeList();
		}
		frm.setList(list);
		frm.setFilters(filterParams);
	}

	/**
	 * Дополнить запрос параметрами для подключения индексов.
	 * Во всех журналах имеется обязательное проиндексированное поле приложение(Application).
	 * если в запросе недостаточно параметров для подключения сеективного индекса,
	 * передаются все возможные значения для поля приложение
	 * для подключения хотя бы неселективного индекса по приложению и дате.
	 * @param query запрос
	 */
	protected void completeParametersForIndex(Query query)
	{
		for (String indexedParameter : getIndexedParameters())
		{
			Object parameter = query.getParameter(indexedParameter);
			if (parameter != null && StringHelper.isNotEmpty(parameter.toString()))
			{
				return;// если имеется хотя бы 1 параметр для подключения индекса, ничего не делаем.
			}
		}
		//Параметров с индексами нет - передаем все приложения.
		Application[] applications = Application.values();
		List<String> apps = new ArrayList<String>(applications.length);
		for (Application application : applications)
		{
			apps.add(application.toString());
		}
		query.setParameterList("application", apps);
	}

	/**
	 * @return массив параметров, для которых есть индекс. не может быть null
	 */
	protected abstract String[] getIndexedParameters();

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		fillQueryBase(query, filterParams);

		query.setParameter("application", filterParams.get("application"));
		query.setParameter("type", filterParams.get("type") );
		query.setParameter("ipAddres", filterParams.get("ipAddres") );
		query.setParameter("loginId", filterParams.get("loginId"));
		query.setParameter("fio", filterParams.get("fio"));
		query.setParameter("departmentId", filterParams.get("departmentId"));
		query.setParameter("TB", filterParams.get("TB"));
		query.setParameter("OSB", filterParams.get("OSB"));
		query.setParameter("VSP", filterParams.get("VSP"));
		query.setParameter("birthday", filterParams.get("birthday"));
		query.setParameter("series", StringUtils.deleteWhitespace((String)filterParams.get("series")));
		query.setParameter("number", filterParams.get("number"));
		query.setParameter("withChildren", filterParams.get("withChildren"));
		fillQueryForAllowedDepartment(query);
	}

	protected void fillQueryForAllowedDepartment(Query query)
	{
		EmployeeDataProvider provider = EmployeeContext.getEmployeeDataProvider();
		Employee employee = provider.getEmployeeData().getEmployee();
		query.setParameter("personLoginId", employee.getLogin().getId());
	}

	protected void fillQueryBase(Query query, Map<String, Object> filterParams) throws BusinessException
	{
		Object fTime = filterParams.get("fromTime") != null ? filterParams.get("fromTime") : DateHelper.BEGIN_DAY_TIME;
		Object tTime = filterParams.get("toTime")   != null ? filterParams.get("toTime")   : DateHelper.END_DAY_TIME;

		Calendar fDate = createCalendar(filterParams.get("fromDate"), fTime);
		if (fDate!=null) fDate.set(Calendar.MILLISECOND,0);
		Calendar tDate = createCalendar(filterParams.get("toDate"), tTime);
		if (tDate!=null) tDate.set(Calendar.MILLISECOND,999);

		query.setParameter("fromDate", fDate);
		query.setParameter("toDate"  , tDate);

		if (filterParams.get("maxRows") != null) {
			query.setMaxResults(filterParams.get("maxRows") instanceof String ? Integer.parseInt((String) filterParams.get("maxRows")) : (Integer) filterParams.get("maxRows"));

		}
	}

	private Calendar createCalendar(Object date, Object time) throws BusinessException
	{
		if (date==null)
		{
			//если дата не задана делать вообще нечего.
			return null;
		}
		Calendar dateCalendar;
		Calendar timeCalendar;
		try
		{
			dateCalendar = DateHelper.toCalendar((Date) (date instanceof String ? DateHelper.parseDate((String) date) : date));
			timeCalendar = DateHelper.toCalendar((Date) (time instanceof String ? DateHelper.parseTime((String) time) : time));
		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}

		if (time != null)
		{
			dateCalendar.set(Calendar.HOUR_OF_DAY,timeCalendar.get(Calendar.HOUR_OF_DAY));
			dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
			dateCalendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
		}
		return dateCalendar;
	}

	protected abstract String getLogName();

	protected void forwardFilterFail(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		DownloadLogFilterBaseForm frm = (DownloadLogFilterBaseForm) form;
		frm.setList(EMPTY_RESULT_LIST);
		super.forwardFilterFail(form, operation);
	}
}
