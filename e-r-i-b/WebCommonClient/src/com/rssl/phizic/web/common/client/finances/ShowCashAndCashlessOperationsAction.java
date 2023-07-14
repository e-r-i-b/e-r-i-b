package com.rssl.phizic.web.common.client.finances;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.BalanceByOperationsComparator;
import com.rssl.phizic.business.finances.CashAndCashlessOperationsGraphAbstract;
import com.rssl.phizic.business.finances.CashBalanceByOperations;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.operations.finances.ShowCashAndCashlessOperationsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.component.MonthPeriodFilter;
import com.rssl.phizic.web.component.PeriodFilter;
import org.apache.struts.action.ActionForm;

import java.math.BigDecimal;
import java.util.*;

/**
 * Построение графика наличных и безналичных операций в структуре финансовых потоков
 *
 * @author lepihina
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowCashAndCashlessOperationsAction extends FinanceFilterActionBase
{
	protected FinancesOperationBase createFinancesOperation(FinanceFormBase frm) throws BusinessLogicException, BusinessException
	{
		ShowCashAndCashlessOperationsOperation op = createOperation(ShowCashAndCashlessOperationsOperation.class);
		op.initialize();
		return op;
	}

	protected void doFilter(FinanceFilterData filterData, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException
	{
		ShowCashAndCashlessOperationsOperation op = (ShowCashAndCashlessOperationsOperation)operation;
		ShowCashAndCashlessOperationsForm form = (ShowCashAndCashlessOperationsForm) frm;

		List<CashAndCashlessOperationsGraphAbstract> operations = op.getCashOperationsGraphData(filterData);
		String typeDate = filterData.getTypeDate();
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

	private Map<String, CashBalanceByOperations> getMonthBalanceByOperationsMap(Calendar startDate, Calendar endDate, int dateType)
	{
		Calendar fromDate = (Calendar) startDate.clone();
		Calendar toDate = (Calendar) endDate.clone();
		Map<String, CashBalanceByOperations> result = new HashMap<String, CashBalanceByOperations>();
		while (fromDate.compareTo(toDate) == -1)
		{
			Calendar date = (Calendar) fromDate.clone();
			result.put(getKey(date, dateType), new CashBalanceByOperations(DateHelper.toCalendar(date.getTime()), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
			fromDate.add(dateType, 1);
			fromDate.get(dateType);
		}
		return result;
	}

	private void setPeriodData(FinanceFilterData filterData, ShowCashAndCashlessOperationsForm form, List<CashAndCashlessOperationsGraphAbstract> operations) throws BusinessException
	{
		setFormData(form, operations, getMonthBalanceByOperationsMap(filterData.getFromDate(), filterData.getToDate(), Calendar.MONTH), Calendar.MONTH);
	}

	private void setMonthData(FinanceFilterData filterData, ShowCashAndCashlessOperationsForm form, List<CashAndCashlessOperationsGraphAbstract> operations) throws BusinessException
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
	private void setFormData(ShowCashAndCashlessOperationsForm form, List<CashAndCashlessOperationsGraphAbstract> operations, Map<String, CashBalanceByOperations> map, int dateType)
	{
		BigDecimal income = BigDecimal.ZERO;
		BigDecimal outcome = BigDecimal.ZERO;
		BigDecimal maxValue = BigDecimal.ZERO;
		for(CashAndCashlessOperationsGraphAbstract cardOperation : operations)
		{
			BigDecimal sum = cardOperation.getOperationSum().abs();
			String key = getKey(cardOperation.getDate(), dateType);
			CashBalanceByOperations balance = map.get(key);

			if (cardOperation.getIncome())
			{
				income = income.add(sum);
				if (cardOperation.getCash())
				{
					balance.setCashIncome(balance.getCashIncome().add(sum));
					if (balance.getCashIncome().compareTo(maxValue) == 1)
						maxValue = balance.getCashIncome();
				}
				else
				{
					balance.setCashlessIncome(balance.getCashlessIncome().add(sum));
					if (balance.getCashlessIncome().compareTo(maxValue) == 1)
						maxValue = balance.getCashlessIncome();
				}
			}
			else
			{
				outcome = outcome.add(sum);
				if (cardOperation.getCash())
				{
					balance.setCashOutcome(balance.getCashOutcome().add(sum));
					if (balance.getCashOutcome().compareTo(maxValue) == 1)
						maxValue = balance.getCashOutcome();
				}
				else
				{
					balance.setCashlessOutcome(balance.getCashlessOutcome().add(sum));
					if (balance.getCashlessOutcome().compareTo(maxValue) == 1)
						maxValue = balance.getCashlessOutcome();
				}
			}

			map.put(key, balance);
		}

		form.setIncome(income);
		form.setOutcome(outcome);
		form.setMaxVal(maxValue);
		List<CashBalanceByOperations> list = new ArrayList<CashBalanceByOperations>(map.values());
		BalanceByOperationsComparator comparator = new BalanceByOperationsComparator();
		Collections.sort(list, comparator);
		form.setMonthList(list);
		form.setOpenPageDate(Calendar.getInstance());
	}

	protected Form getFilterForm(ActionForm frm)
	{
		return ShowCashAndCashlessOperationsForm.createFilterForm();
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
		fromDate.add(Calendar.MONTH, -4); // по умолчанию отображаем период в 5 месяцев

		filterParameters.put("fromDate", fromDate.getTime());
		filterParameters.put("toDate", DateHelper.getCurrentDate().getTime());
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
		filterData.setTypeDate((String)filterParams.get(MonthPeriodFilter.TYPE_PERIOD));
		filterData.setFromDate( DateHelper.toCalendar((Date)filterParams.get("fromDate")));
		filterData.setToDate(DateHelper.toCalendar((Date)filterParams.get("toDate")));
		filterData.setCreditCards(filterParams.get("showCreditCards")!=null);
		filterData.setCash(filterParams.get("showCash")!= null);
		filterData.setOnlyOwnCards(filterParams.get("showOtherAccounts")==null);

		doFilter(filterData, operation, frm);
	}
}
