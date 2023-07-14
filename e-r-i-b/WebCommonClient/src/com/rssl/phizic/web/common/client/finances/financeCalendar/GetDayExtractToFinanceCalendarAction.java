package com.rssl.phizic.web.common.client.finances.financeCalendar;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.financeCalendar.GetAutoSubscriptionsToFinanceCalendarOperation;
import com.rssl.phizic.operations.finances.financeCalendar.GetDayExtractToFinanceCalendarOperation;
import com.rssl.phizic.operations.finances.financeCalendar.GetInvoiceSubscriptionsToFinanceCalendarOperation;
import com.rssl.phizic.operations.finances.financeCalendar.GetRemindersToFinanceCalendarOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import com.rssl.phizic.web.component.DatePeriodFilter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lepihina
 * @ created 24.04.14
 * $Author$
 * $Revision$
 * Получение выписки за день для финансового календаря
 */
public class GetDayExtractToFinanceCalendarAction extends AsyncOperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GetDayExtractToFinanceCalendarForm frm = (GetDayExtractToFinanceCalendarForm) form;
		GetDayExtractToFinanceCalendarOperation operation = createOperation(GetDayExtractToFinanceCalendarOperation.class);
		operation.initialize();

		FieldValuesSource valuesSource = getFieldValuesSource(frm);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, getForm());
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			Map<String, Object> filterParams = getNormalizedDateParams(result);
			FinanceFilterData filterData = new FinanceFilterData();
			filterData.setFromDate(DateHelper.toCalendar((Date) filterParams.get("fromDate")));
			filterData.setToDate(DateHelper.toCalendar((Date)filterParams.get("toDate")));
			filterData.setSelectedCardIds((String)result.get("selectedCardIds"));
			filterData.setMaxOperationLoadDate(DateHelper.toCalendar((Date)filterParams.get("openPageDate")));
			operation.calculateDayExtract(filterData);

			frm.setDayExtractByOperations(operation.getDayExtract());

			if (checkAccess(GetAutoSubscriptionsToFinanceCalendarOperation.class, "ViewAutoSubscriptionsInFinanceCalendarService"))
			{
				GetAutoSubscriptionsToFinanceCalendarOperation getAutoSubscriptionsOperation = createOperation(GetAutoSubscriptionsToFinanceCalendarOperation.class,
						"ViewAutoSubscriptionsInFinanceCalendarService");

				frm.setDayExtractByAutoSubscriptions(getAutoSubscriptionsOperation.getAutoSubscriptionData(filterData.getSelectedCardIds(), filterData.getFromDate(), filterData.getToDate()));
			}

			if (checkAccess(GetInvoiceSubscriptionsToFinanceCalendarOperation.class, "PaymentBasketManagment"))
			{
				GetInvoiceSubscriptionsToFinanceCalendarOperation getInvoiceSubscriptionsOperation = createOperation(GetInvoiceSubscriptionsToFinanceCalendarOperation.class,
						"PaymentBasketManagment");
				frm.setDayExtractByInvoiceSubscriptions(getInvoiceSubscriptionsOperation.getInvoiceSubscriptionData(filterData.getSelectedCardIds(), filterData.getFromDate(), filterData.getToDate()));
			}

			if (checkAccess(GetRemindersToFinanceCalendarOperation.class, "ReminderManagment"))
			{
				GetRemindersToFinanceCalendarOperation getRemindersOperation = createOperation(GetRemindersToFinanceCalendarOperation.class, "ReminderManagment");
				frm.setDayExtractByReminders(getRemindersOperation.getRemindersDate(filterData.getFromDate()));
			}

			frm.setExtractDate(DateHelper.toCalendar((Date) filterParams.get("fromDate")));
			frm.setDateType(operation.getDateType());
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(FORWARD_START);
	}

	protected Map<String, Object> getNormalizedDateParams(Map<String, Object> filterParams)
	{
		filterParams.put(DatePeriodFilter.TYPE_PERIOD, DatePeriodFilter.TYPE_PERIOD_DAY);
		DatePeriodFilter datePeriodFilter = new DatePeriodFilter(filterParams);
		return datePeriodFilter.normalize().getParameters();
	}

	protected FieldValuesSource getFieldValuesSource(GetDayExtractToFinanceCalendarForm frm)
	{
		return new MapValuesSource(frm.getFilters());
	}

	protected Form getForm()
	{
		return GetDayExtractToFinanceCalendarForm.FILTER_FORM;
	}
}
