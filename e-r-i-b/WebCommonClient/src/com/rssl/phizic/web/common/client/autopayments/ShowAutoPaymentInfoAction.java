package com.rssl.phizic.web.common.client.autopayments;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.filters.FilterParametersByUrl;
import com.rssl.phizic.business.filters.FilterParametersService;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autopayments.GetAutoPaymentInfoOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  Action дл€ отображени€ детальной информации автоплатежей типа AutoPayment
 * @author osminin
 * @ created 10.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class ShowAutoPaymentInfoAction extends ViewActionBase
{
	private static final FilterParametersService filterParametersService = new FilterParametersService();

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.showScheduleReport", "showScheduleReport");
		map.put("button.showAllScheduleReport", "showAllScheduleReport");
		return map;
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowAutoPaymentInfoForm frm = (ShowAutoPaymentInfoForm) form;
		GetAutoPaymentInfoOperation operation = createOperation(GetAutoPaymentInfoOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowAutoPaymentInfoForm frm = (ShowAutoPaymentInfoForm) form;
		doFilter(initFilterParameters(), frm);
		updateForm((GetAutoPaymentInfoOperation) operation, frm, false);
	}

	/**
	 * заполение формы данными дл€ отображени€
	 * @param operation операци€
	 * @param frm форма дл€ заполени€
	 * @param isAllSchedule true - показываеть весь график исполнени€
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void updateForm(GetAutoPaymentInfoOperation operation, ShowAutoPaymentInfoForm frm, boolean isAllSchedule) throws BusinessException, BusinessLogicException
	{
		if(isAllSchedule)
		{
			frm.setScheduleItems(operation.getAllScheduleReport());
		}
		else
		{
			Map<String, Object> parameters = frm.getFilters();
			Calendar fromDate = DateHelper.toCalendar((Date) parameters.get("fromDate"));
			Calendar toDate = DateHelper.toCalendar((Date) parameters.get("toDate"));
			frm.setScheduleItems(operation.getScheduleItems(fromDate, toDate));
		}

		frm.setLink(operation.getEntity());

		String nameRequisite = operation.getEntity().getRequisiteName();
		if (StringHelper.isEmpty(nameRequisite))
			throw new BusinessException("Ќе задано им€ реквизита по умолчанию");
		
		frm.setRequisiteName(nameRequisite);
	}

	public ActionForward showScheduleReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowAutoPaymentInfoForm frm = (ShowAutoPaymentInfoForm) form;
		MapValuesSource valuesSource = new MapValuesSource(frm.getFilters());
		Form filterForm = ShowAutoPaymentInfoForm.FILTER_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, filterForm);

		if (processor.process())
			doFilter(processor.getResult(), frm);
		else
		{
			saveErrors(request, processor.getErrors());
			doFilter(getDefaultFilterParams(), frm);
		}

		GetAutoPaymentInfoOperation operation = createOperation(GetAutoPaymentInfoOperation.class);
		operation.initialize(frm.getId());
		updateForm(operation, frm, false);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward showAllScheduleReport (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowAutoPaymentInfoForm frm = (ShowAutoPaymentInfoForm) form;
		GetAutoPaymentInfoOperation operation = createOperation(GetAutoPaymentInfoOperation.class);
		operation.initialize(frm.getId());

		updateForm(operation, frm, true);

		doFilter(getDefaultFilterParams(), frm);
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * запомниаем в базе и устанавливаем на форму параметры фильтра
	 * @param filterParams параметры фильтра
	 * @param frm форма
	 */
	private void doFilter(Map<String, Object> filterParams, ShowAutoPaymentInfoForm frm) throws BusinessException
	{
		HttpServletRequest request = currentRequest();
		String sessionId = request.getSession().getId();
		String requestURI = request.getRequestURI();
		FilterParametersByUrl filterParameters = filterParametersService.getBySessionIdAndUrl(sessionId, requestURI);
		if (filterParameters == null)
			filterParameters = new FilterParametersByUrl();
		filterParameters.setSessionId(sessionId);
		filterParameters.setUrl(requestURI);
		filterParameters.setParameters(filterParams);
		filterParametersService.addOrUpdate(filterParameters);
		frm.setFilters(filterParams);
	}

	/**
	 * @return мап параметров фильтра по умолчанию
	 */
	private Map<String, Object> getDefaultFilterParams() throws BusinessException
	{
		HttpServletRequest request = currentRequest();
		String sessionId = request.getSession().getId();
		String url = request.getRequestURI();
		FilterParametersByUrl parameters = filterParametersService.getBySessionIdAndUrl(sessionId, url);
		return parameters != null ? parameters.getParameters() : initFilterParameters();
	}

	/**
	 * «аполн€ем начальные данные
	 * ѕо умолчанию, график исполнени€ платежа строитс€ за период 12 мес€цев
	 */
	private Map<String, Object> initFilterParameters()
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		Calendar toDate = Calendar.getInstance();
		Calendar fromDate = DateHelper.toCalendar(DateHelper.add(toDate.getTime(), -1, 0,0));
		parameters.put("fromDate", fromDate.getTime());
		parameters.put("toDate", toDate.getTime());
		return parameters;
	}
}
