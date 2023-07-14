package com.rssl.phizic.web.common.client.finances.financeCalendar;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.operations.finances.financeCalendar.GetAutoSubscriptionsToFinanceCalendarOperation;
import com.rssl.phizic.operations.finances.financeCalendar.GetInvoiceSubscriptionsToFinanceCalendarOperation;
import com.rssl.phizic.operations.finances.financeCalendar.GetRemindersToFinanceCalendarOperation;
import com.rssl.phizic.operations.finances.financeCalendar.ShowFinanceCalendarOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.client.finances.FinanceFilterActionBase;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;
import com.rssl.phizic.web.component.CalendarMonthPeriodFilter;
import com.rssl.phizic.web.component.PeriodFilter;
import org.apache.struts.action.ActionForm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lepihina
 * @ created 26.03.14
 * $Author$
 * $Revision$
 * Просмотр финансового календаря.
 */
public class ShowFinanceCalendarAction extends FinanceFilterActionBase
{
	private static final String AUTO_SUBSCRIPTIONS_ERROR_MESSAGE = "В календаре показаны не все Ваши автоплатежи. Информация по ним временно недоступна.";
	private static final String CALC_CARD_BALANCE_ERROR_MESSAGE = "Информация по остаткам средств на банковских картах временно недоступна.";

	protected FinancesOperationBase createFinancesOperation(FinanceFormBase frm) throws BusinessLogicException, BusinessException
	{
		ShowFinanceCalendarOperation operation = createOperation(ShowFinanceCalendarOperation.class);
		ShowFinanceCalendarForm form = (ShowFinanceCalendarForm) frm;
		operation.initialize(form.getSelectedId());
		return operation;
	}

	protected Form getFilterForm(ActionForm frm)
	{
		return ShowFinanceCalendarForm.createFilterForm();
	}

	protected Map<String, Object> getDefaultFilterParameters(FinanceFormBase frm, FinancesOperationBase operation)
	{
		Map<String, Object> filterParameters = new HashMap<String, Object>();
		filterParameters.put("onDate", DateHelper.getCurrentDate().getTime());
		CalendarMonthPeriodFilter filter = new CalendarMonthPeriodFilter(filterParameters);

		return filter.normalize().getParameters();
	}

	protected void doFilter(Map filterParams, FinancesOperationBase operation, FinanceFormBase frm) throws Exception
	{
		FinanceFilterData filterData = new FinanceFilterData();
		filterData.setFromDate(DateHelper.toCalendar((Date) filterParams.get(CalendarMonthPeriodFilter.FROM_DATE)));
		filterData.setToDate(DateHelper.toCalendar((Date) filterParams.get(CalendarMonthPeriodFilter.TO_DATE)));
		filterData.setCash(true);
		doFilter(filterData, operation, frm);
	}

	protected PeriodFilter getDefaultPeriodFilter(Map<String, Object> filterParams)
	{
		return new CalendarMonthPeriodFilter(filterParams);
	}

	protected void doFilter(FinanceFilterData filterData, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException, BusinessLogicException
	{
		ShowFinanceCalendarForm form = (ShowFinanceCalendarForm) frm;
		ShowFinanceCalendarOperation op = (ShowFinanceCalendarOperation)operation;
		op.calculateCalendarData(filterData, op.getRoot().getSelectedIds());

		if (checkAccess(GetAutoSubscriptionsToFinanceCalendarOperation.class, "ViewAutoSubscriptionsInFinanceCalendarService"))
		{
			GetAutoSubscriptionsToFinanceCalendarOperation getAutoSubscriptionsOperation = createOperation(GetAutoSubscriptionsToFinanceCalendarOperation.class,
																											"ViewAutoSubscriptionsInFinanceCalendarService");
			try
			{
				getAutoSubscriptionsOperation.fillAutoSubscriptionData(op.getCalendarData(), op.getSelectedCards());
			}
			catch (BusinessException e)
			{
				saveMessage(currentRequest(), AUTO_SUBSCRIPTIONS_ERROR_MESSAGE);
				log.error(e.getMessage(), e);
			}
		}

		if (checkAccess(GetInvoiceSubscriptionsToFinanceCalendarOperation.class, "PaymentBasketManagment"))
		{
			GetInvoiceSubscriptionsToFinanceCalendarOperation getInvoiceSubscriptionsOperation = createOperation(GetInvoiceSubscriptionsToFinanceCalendarOperation.class,
																												"PaymentBasketManagment");
			getInvoiceSubscriptionsOperation.fillInvoiceSubscriptionData(op.getCalendarData(), op.getSelectedCards());
		}

		if (checkAccess(GetRemindersToFinanceCalendarOperation.class, "ReminderManagment"))
		{
			GetRemindersToFinanceCalendarOperation getRemindersOperation = createOperation(GetRemindersToFinanceCalendarOperation.class, "ReminderManagment");
			getRemindersOperation.fillRemindersData(op.getCalendarData());
		}

		form.setNode(op.getRoot());
		form.setCalendarData(op.getCalendarData());
		form.setOpenPageDate(Calendar.getInstance());

		try
		{
			form.setCardsBalance(op.getCardsBalance(filterData, op.getRoot().getSelectedIds()));
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения суммы средств на картах клиента в рублевом эквиваленте по курсу ЦБ.", e);
			saveMessage(currentRequest(), CALC_CARD_BALANCE_ERROR_MESSAGE);
		}
	}
}
