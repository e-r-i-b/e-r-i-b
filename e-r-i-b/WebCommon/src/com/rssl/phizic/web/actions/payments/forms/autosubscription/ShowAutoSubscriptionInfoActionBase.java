package com.rssl.phizic.web.actions.payments.forms.autosubscription;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.filters.FilterParametersByUrl;
import com.rssl.phizic.business.filters.FilterParametersService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.utils.DateHelper;
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
 * Action дл€ отображени€ детальной информации автоплатежей типа AutoSubscription
 * @author basharin
 * @ created 30.01.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class ShowAutoSubscriptionInfoActionBase extends ViewActionBase
{
	private static final FilterParametersService filterParametersService = new FilterParametersService();

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.showScheduleReport", "showScheduleReport");
		map.put("button.filter","showScheduleReport");
		return map;
	}

	protected void updateFormData(ViewEntityOperation op, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowAutoSubscriptionInfoForm frm = (ShowAutoSubscriptionInfoForm) form;
		doFilter(initFilterParameters(), frm);
		updateForm((GetAutoSubscriptionInfoOperation) op, frm);
	}

	/**
	 * заполение формы данными дл€ отображени€
	 * @param operation операци€
	 * @param frm форма дл€ заполени€
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void updateForm(GetAutoSubscriptionInfoOperation operation, ShowAutoSubscriptionInfoForm frm) throws BusinessException, BusinessLogicException
	{
	    frm.setLink(operation.getEntity());
		frm.setAutoSubscriptionInfo(operation.getAutoSubscriptionInfo());
		CardLink cardLink = operation.getEntity().getCardLink();
		if (cardLink != null)
			frm.setAutoSubscriptionCardState(cardLink.getCard().getCardAccountState());
		if (!operation.isUpdateSheduleItemsError())
			frm.setScheduleItems(operation.getScheduleItems());
		else
			frm.setTextUpdateSheduleItemsError(operation.getTextUpdateSheduleItemsError());

	}

	protected GetAutoSubscriptionInfoOperation operationInitialize(ShowAutoSubscriptionInfoForm form, GetAutoSubscriptionInfoOperation operation) throws BusinessException, BusinessLogicException
	{
		if (form.isShowPaymentForPeriod())
		{
			Map<String, Object> parameters = form.getFilters();
			Date fromDate = (Date) parameters.get("fromDate");
			Date toDate = (Date) parameters.get("toDate");
			//сетим врем€ полными дн€ми
			fromDate = DateHelper.setTime(fromDate,0,0,0,0);
			toDate = DateHelper.setTime(toDate,23,59,59,0);
			operation.initialize(form.getId(), DateHelper.toCalendar(fromDate), DateHelper.toCalendar(toDate));
		}
		else
			operation.initialize(form.getId());
		return operation;
	}

	/**
	 * ќтображает график платежей.
	 */
	public ActionForward showScheduleReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowAutoSubscriptionInfoForm frm = (ShowAutoSubscriptionInfoForm) form;

		MapValuesSource valuesSource = new MapValuesSource(frm.getFilters());
		Form filterForm = ShowAutoSubscriptionInfoForm.FILTER_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, filterForm);

		if (processor.process())
			doFilter(processor.getResult(), frm);
		else
		{
			saveErrors(request, processor.getErrors());
			doFilter(getDefaultFilterParams(), frm);
		}

		GetAutoSubscriptionInfoOperation operation = (GetAutoSubscriptionInfoOperation) createViewEntityOperation(frm);
		updateForm(operation, frm);
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * @return параметры фильтра по умолчанию
	 */
	protected Map<String, Object> getDefaultFilterParams() throws BusinessException
	{
		HttpServletRequest request = currentRequest();
		String sessionId = request.getSession().getId();
		String url = request.getRequestURI();
		FilterParametersByUrl parameters = filterParametersService.getBySessionIdAndUrl(sessionId, url);
		return parameters != null ? parameters.getParameters() : initFilterParameters();
	}

	/**
	 * запомниаем в базе и устанавливаем на форму параметры фильтра.
	 * @param filterParams параметры фильтра.
	 * @param frm форма.
	 */
	protected void doFilter(Map<String, Object> filterParams, ShowAutoSubscriptionInfoForm frm) throws BusinessException
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
	 * «аполн€ем начальные данные
	 * ѕо умолчанию, график исполнени€ платежа строитс€ за период 12 мес€цев
	 */
	private Map<String, Object> initFilterParameters()
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		Calendar toDate = Calendar.getInstance();
		Calendar fromDate = DateHelper.toCalendar(DateHelper.add(toDate.getTime(), 0, -1,0));
		parameters.put("fromDate", fromDate.getTime());
		parameters.put("toDate", toDate.getTime());
		return parameters;
	}
}
