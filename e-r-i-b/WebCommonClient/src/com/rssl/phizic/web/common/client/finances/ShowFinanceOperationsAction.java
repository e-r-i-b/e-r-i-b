package com.rssl.phizic.web.common.client.finances;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.BalanceByOperationsComparator;
import com.rssl.phizic.business.finances.CardOperationAbstract;
import com.rssl.phizic.business.finances.MonthBalanceByOperations;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.operations.finances.ShowFinanceOperationsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.component.MonthPeriodFilter;
import com.rssl.phizic.web.component.PeriodFilter;
import org.apache.struts.action.ActionForm;

import java.math.BigDecimal;
import java.util.*;

/**
 * Построение графика "Поступления и списания" 
 *
 * @author lepihina
 * @ created 08.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowFinanceOperationsAction extends FinanceFilterActionBase
{
	protected FinancesOperationBase createFinancesOperation(FinanceFormBase frm) throws BusinessLogicException, BusinessException
	{
		ShowFinanceOperationsOperation op = createOperation(ShowFinanceOperationsOperation.class);
		op.initialize();
		return op;
	}

	protected void doFilter(FinanceFilterData filterData, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException
	{
		ShowFinanceOperationsOperation op = (ShowFinanceOperationsOperation)operation;
		ShowFinanceOperationsForm form = (ShowFinanceOperationsForm) frm;

		List<CardOperationAbstract> operations = op.getCategoriesGraphData(filterData);
		String typeDate = (String)form.getFilter("typeDate");
		if (StringHelper.isEmpty(typeDate) || typeDate.equals(MonthPeriodFilter.TYPE_PERIOD_MONTH_PERIOD))
			setPeriodData(filterData, form, operations);
		else
			setMonthData(filterData, form, operations);
	}

	private String getKey(Calendar date, int dateType)
	{
		String result = String.valueOf(date.get(Calendar.YEAR)).concat(String.valueOf(date.get(Calendar.MONTH)));
		if (Calendar.DATE == dateType)
			result = result.concat(String.valueOf(date.get(Calendar.DATE)));
		return result;
	}

	private Map<String, MonthBalanceByOperations> getMonthBalanceByOperationsMap(Calendar startDate, Calendar endDate, int dateType)
	{
		Calendar fromDate = (Calendar) startDate.clone();
		Calendar toDate = (Calendar) endDate.clone();
		Map<String, MonthBalanceByOperations> result = new HashMap<String, MonthBalanceByOperations>();
		while (fromDate.compareTo(toDate) == -1)
		{
			Calendar date = (Calendar) fromDate.clone();
			result.put(getKey(date, dateType), new MonthBalanceByOperations(date, BigDecimal.ZERO, BigDecimal.ZERO));
			fromDate.add(dateType, 1);
			fromDate.get(dateType);
		}
		return result;
	}

	private void setPeriodData(FinanceFilterData filterData, ShowFinanceOperationsForm form, List<CardOperationAbstract> operations)
	{
		setFormData(form, operations, getMonthBalanceByOperationsMap(filterData.getFromDate(), filterData.getToDate(), Calendar.MONTH), Calendar.MONTH);
	}

	private void setMonthData(FinanceFilterData filterData, ShowFinanceOperationsForm form, List<CardOperationAbstract> operations)
	{
		Calendar fromDate = filterData.getFromDate();
		Calendar toDate = (Calendar) fromDate.clone();
		toDate.add(Calendar.MONTH, 1);
		setFormData(form, operations, getMonthBalanceByOperationsMap(fromDate, toDate, Calendar.DATE), Calendar.DATE);
	}

	/**
	 * Вычисляет и сохраняет данные на форму
	 * @param form форма
	 * @param operations список операций
	 * @param map мапа
	 * @param dateType тип периода
	 */
	private void setFormData(ShowFinanceOperationsForm form, List<CardOperationAbstract> operations, Map<String, MonthBalanceByOperations> map, int dateType)
	{
		BigDecimal income = BigDecimal.ZERO;
		BigDecimal outcome = BigDecimal.ZERO;
		BigDecimal maxValue = BigDecimal.ZERO;
		for(CardOperationAbstract cardOperation : operations)
		{
			BigDecimal sum = cardOperation.getOperationSum().abs();
			String key = getKey(cardOperation.getDate(), dateType);
			MonthBalanceByOperations balance = map.get(key);

			if (cardOperation.getIncome())
			{
				income = income.add(sum);
				balance.setIncome(balance.getIncome().add(sum));

				if (balance.getIncome().compareTo(maxValue) == 1)
					maxValue = balance.getIncome();
			}
			else
			{
				outcome = outcome.add(sum);
				balance.setOutcome(balance.getOutcome().add(sum));

				if (balance.getOutcome().compareTo(maxValue) == 1)
					maxValue = balance.getOutcome();
			}
		}

		form.setIncome(income);
		form.setOutcome(outcome);
		form.setMaxVal(maxValue);

		List<MonthBalanceByOperations> list = new ArrayList<MonthBalanceByOperations>(map.values());
		BalanceByOperationsComparator comparator = new BalanceByOperationsComparator();
		Collections.sort(list, comparator);
		form.setMonthList(list);
		form.setOpenPageDate(Calendar.getInstance());
	}

	protected Form getFilterForm(ActionForm frm)
	{
		return ShowFinanceOperationsForm.createFilterForm();
	}

	@Override
	protected PeriodFilter getDefaultPeriodFilter(Map<String, Object> filterParams)
	{
		return new MonthPeriodFilter(filterParams);
	}

	protected Map<String, Object> getDefaultFilterParameters(FinanceFormBase frm, FinancesOperationBase operation)
	{
		Map<String, Object> filterParameters = new HashMap<String, Object>();
		Calendar fromDate = DateHelper.getCurrentDate();
		fromDate.add(Calendar.MONTH, -4);

		filterParameters.put("fromDate", DateHelper.getFirstDayOfMonth(fromDate).getTime());
		filterParameters.put("toDate", DateHelper.getLastDayOfMonth(DateHelper.getCurrentDate()).getTime());
		filterParameters.put(MonthPeriodFilter.TYPE_PERIOD, MonthPeriodFilter.TYPE_PERIOD_MONTH_PERIOD);
		filterParameters.put("showCreditCards", "on");
		filterParameters.put("showOtherAccounts", "on");
		filterParameters.put("showCash", "on");
		MonthPeriodFilter periodFilter = new MonthPeriodFilter(filterParameters);

		return periodFilter.normalize().getParameters();
	}

	protected void doFilter(Map filterParams, FinancesOperationBase operation, FinanceFormBase frm) throws Exception
	{
		FinanceFilterData filterData = new FinanceFilterData();
		filterData.setFromDate( DateHelper.toCalendar((Date)filterParams.get("fromDate")));
		filterData.setToDate(DateHelper.toCalendar((Date)filterParams.get("toDate")));
		filterData.setCreditCards(filterParams.get("showCreditCards")!=null);
		filterData.setCash(filterParams.get("showCash")!= null);
		filterData.setOnlyOwnCards(filterParams.get("showOtherAccounts")==null);

		doFilter(filterData, operation, frm);
	}
}
