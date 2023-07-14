package com.rssl.phizic.web.common.mobile.autosubscriptions;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionInfoAction;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionInfoForm;
import com.rssl.phizic.web.common.mobile.common.FilterFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action для отображения графика автоплатежей типа AutoSubscription в мобильной версии
 * @ author Rtischeva
 * @ created 08.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowAutoSubscriptionAbstractMobileAction extends ShowAutoSubscriptionInfoAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowAutoSubscriptionAbstractMobileForm frm = (ShowAutoSubscriptionAbstractMobileForm) form;
		String from = frm.getFrom();
		String to = frm.getTo();
		if (StringHelper.isNotEmpty(from) || StringHelper.isNotEmpty(to))
		{
			return showScheduleReport(mapping, form, request, response);
		}
		else
		{
			return super.start(mapping, form, request, response);
		}
	}

	/**
	 * Отображает график платежей.
	 */
	public ActionForward showScheduleReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowAutoSubscriptionAbstractMobileForm frm = (ShowAutoSubscriptionAbstractMobileForm) form;
		MapValuesSource valuesSource = getFilterValues(frm);
		Form filterForm = FilterFormBase.FILTER_DATE_FORM;;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, filterForm);

		if (processor.process())
		{
			doFilter(processor.getResult(), frm);
			GetAutoSubscriptionInfoOperation operation = (GetAutoSubscriptionInfoOperation) createViewEntityOperation(frm);
			updateForm(operation, frm);
		}
		else
		{
			saveErrors(request, processor.getErrors());
			doFilter(getDefaultFilterParams(), frm);
		}

		return mapping.findForward(FORWARD_START);
	}

	private MapValuesSource getFilterValues(ShowAutoSubscriptionAbstractMobileForm frm)
	{
	    Map<String,Object> filter = new HashMap<String,Object>();
	    filter.put(FilterFormBase.FROM_DATE_NAME, frm.getFrom());
	    filter.put(FilterFormBase.TO_DATE_NAME, frm.getTo());
	    return new MapValuesSource(filter);
	}

	protected GetAutoSubscriptionInfoOperation operationInitialize(ShowAutoSubscriptionInfoForm form, GetAutoSubscriptionInfoOperation operation) throws BusinessException, BusinessLogicException
	{
		ShowAutoSubscriptionAbstractMobileForm frm = (ShowAutoSubscriptionAbstractMobileForm) form;

		Date fromDate = (Date) frm.getFilter(FilterFormBase.FROM_DATE_NAME);
		Date toDate = (Date) frm.getFilter(FilterFormBase.TO_DATE_NAME);
		if (fromDate != null && toDate != null)
		{
			//сетим время полными днями
			fromDate = DateHelper.setTime(fromDate, 0, 0, 0, 0);
			toDate = DateHelper.setTime(toDate, 23, 59, 59, 0);
			operation.initialize(frm.getId(), DateHelper.toCalendar(fromDate), DateHelper.toCalendar(toDate));
		}
		else
			operation.initialize(frm.getId());

		return operation;
	}
}
