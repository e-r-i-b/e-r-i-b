package com.rssl.phizic.web.pfp.admin;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.pfp.admin.ListPFPPassingJournalOperation;
import com.rssl.phizic.operations.pfp.admin.UnloadPPFJournalOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.download.DownloadAction;
import com.rssl.phizic.web.common.exception.FormProcessorException;
import com.rssl.phizic.web.log.FormLogParametersReader;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author komarov
 * @ created 20.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListPFPPassingJournalAction extends ListActionBase
{
	private static final String FILE_NAME = "PFPReport.csv";                         //название выгружаемого файла
	private static final String PFP_PASSING_JOURNAL_FILE_TYPE = "PFPPassingJournal"; //категория выгружаемого файла

	protected Map<String,String> getAditionalKeyMethodMap()
    {
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("button.unload", "unload");
	    return map;
    }

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException
	{
		if(PermissionUtil.impliesService("ListPFPPassingJournalService"))
			return createOperation(ListPFPPassingJournalOperation.class,"ListPFPPassingJournalService");

		return createOperation(ListPFPPassingJournalOperation.class,"ListPFPPassingJournalServiceTB");
	}

	protected UnloadPPFJournalOperation createDownloadOperation() throws BusinessException
	{
		return createOperation(UnloadPPFJournalOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListPFPPassingJournalForm.FILTER_FORM;
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
	{
		Map<String, Object> filterParam = new HashMap<String, Object>();


		Calendar toDate   = Calendar.getInstance();
		Calendar fromDate = DateHelper.getPreviousWeek(toDate);

		Employee empl = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();

		filterParam.put("fromDate", fromDate.getTime());
		filterParam.put("toDate",   toDate.getTime());
		filterParam.put("manager_id", empl.getManagerId());
		filterParam.put("employee_fio", empl.getFullName());
		return filterParam;
	}
	
	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		Date toDate = (Date)filterParams.get("toDate");
		if (toDate != null)
		{
			// прибавляем 1 день, чтобы учитывались планирования, проведенные после 00:00 выбранной toDate даты.
			toDate = DateHelper.add(toDate, 0, 0, 1);
		}
		String documentSeries = (String)filterParams.get("documentSeries");

		query.setParameter("state", filterParams.get("state"));
		query.setParameter("fromDate", filterParams.get("fromDate"));
		query.setParameter("toDate", toDate);
		query.setParameter("user_fio", filterParams.get("user_fio"));
		query.setParameter("risk_profile", filterParams.get("risk_profile"));
		query.setParameter("documentNumber", filterParams.get("documentNumber"));
		query.setParameter("documentSeries", StringUtils.isNotEmpty(documentSeries) ? (documentSeries).replace(" ", "") : documentSeries);
		query.setParameter("birthday", filterParams.get("birthday"));
		query.setParameter("channel_id", filterParams.get("channelId"));
		if(PermissionUtil.impliesService("ListPFPPassingJournalServiceTB"))
		{
			query.setParameter("employee_fio", filterParams.get("employee_fio"));
			query.setParameter("manager_id", filterParams.get("manager_id"));
		}
		else
		{
			Employee empl = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
			query.setParameter("employee_fio", empl.getFullName());
			query.setParameter("manager_id", empl.getManagerId());
		}
	}

	private Query getUnloadDataQuery(Map<String, Object> filterParams, UnloadPPFJournalOperation operation, ListFormBase frm) throws Exception
	{
		String[] ids = frm.getSelectedIds();
		Query query = operation.createQuery(getQueryName(frm));
		query.setParameterList("ids", ids);
		fillQuery(query, filterParams);
		return query;
	}

	/**
	 * Метод создает данные для выгрузки, сохраняет во временном файле
	 * и преоткрывает страницу выгрузки
	 * @param mapping стратс.маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард для обновления страницы после нажатия на кнопку выгрузить
	 */
	@SuppressWarnings({"UnusedDeclaration"})
	public ActionForward unload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;

		UnloadPPFJournalOperation operation = createDownloadOperation();
		try
		{
			Map<String, Object> filterParameters = getFilterParams(frm, operation);

			//Фиксируем данные фильтрации
			addLogParameters(new FormLogParametersReader("Параметры фильтрации", getFilterForm(frm, operation), filterParameters));
			operation.initialize(getUnloadDataQuery(filterParameters, operation, frm));
			//сохраняем параметры фильтрации
			saveFilterParameters(filterParameters);

			//сохраняем файл во временной директории для последующей выгрузки
			DownloadAction.unload(operation.getEntity(), PFP_PASSING_JOURNAL_FILE_TYPE, FILE_NAME, frm, request);
			doFilter(filterParameters, operation, frm);
		}
		catch (FormProcessorException e)
		{
			saveErrors(request, e.getErrors());
			//устанавливаем данный признак для того чтобы не выполнялся запрос поиска в базе в случае ошибки.
			frm.setFromStart(true);
			forwardFilterFail(frm, operation);
		}
		return mapping.findForward(FORWARD_START);
	}
}
