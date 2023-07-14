package com.rssl.phizic.web.common.client.finances;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryGraphAbstract;
import com.rssl.phizic.logging.finances.period.FilterOutcomePeriodLogHelper;
import com.rssl.phizic.logging.finances.period.FilterOutcomePeriodLogRecord;
import com.rssl.phizic.logging.finances.period.PeriodType;
import com.rssl.phizic.operations.finances.CategoriesGraphOperation;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.operations.finances.HintStateOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.component.MonthPeriodFilter;
import com.rssl.phizic.web.component.PeriodFilter;
import org.apache.struts.action.ActionForm;

import java.util.*;

/**
 * @author mihaylov
 * @ created 15.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowFinanceOperationCategoriesAction extends FinanceFilterActionBase
{
	protected FinancesOperationBase createFinancesOperation(FinanceFormBase frm) throws BusinessLogicException, BusinessException
	{
		CategoriesGraphOperation operation = createOperation(CategoriesGraphOperation.class);
		ShowFinanceOperationCategoriesForm form = (ShowFinanceOperationCategoriesForm) frm;
		operation.initialize(form.getSelectedId());
		return operation;
	}

	protected Form getFilterForm(ActionForm frm)
	{
		return ShowFinanceOperationCategoriesForm.createFilterForm();
	}

	protected Map<String, Object> getDefaultFilterParameters(FinanceFormBase frm, FinancesOperationBase operation)
	{
		Map<String, Object> filterParameters = new HashMap<String, Object>();
		Calendar onDate = DateHelper.getCurrentDate();
		filterParameters.put("onDate", DateHelper.getFirstDayOfMonth(onDate).getTime());
		filterParameters.put("fromDate", DateHelper.getFirstDayOfMonth(onDate).getTime());
		filterParameters.put(MonthPeriodFilter.TYPE_PERIOD, MonthPeriodFilter.TYPE_PERIOD_MONTH);
		filterParameters.put("showCreditCards", "on");
		filterParameters.put("showOtherAccounts", "on");
		filterParameters.put("toDate", onDate.getTime());

		return filterParameters;
	}

	@Override
	protected PeriodFilter getDefaultPeriodFilter(Map<String, Object> filterParams)
	{
		return new MonthPeriodFilter(filterParams);
	}

	protected void doFilter(FinanceFilterData filterData, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException
	{
		ShowFinanceOperationCategoriesForm form = (ShowFinanceOperationCategoriesForm) frm;
		CategoriesGraphOperation op = (CategoriesGraphOperation)operation;

		filterData.setIncome(false);
		List<CardOperationCategoryGraphAbstract> outcomeOperations = op.getCategoriesGraphData(filterData, op.getRoot().getSelectedIds());
		form.setOutcomeOperations(outcomeOperations);
		List<CardOperationCategory> outcomeCategories = op.getCategories(filterData);
		form.setOutcomeCategories(outcomeCategories);
		form.setNode(op.getRoot());

		HintStateOperation hintOperation = createOperation(HintStateOperation.class);
		form.setReadAllHints(hintOperation.isReadAllHints());
		form.setOpenPageDate(Calendar.getInstance());
	}

	protected void doFilter(Map filterParams, FinancesOperationBase operation, FinanceFormBase frm) throws Exception
	{
		FinanceFilterData filterData = new FinanceFilterData();
		filterData.setTypeDate((String)filterParams.get("typeDate"));
		filterData.setFromDate(DateHelper.toCalendar((Date)filterParams.get("fromDate")));
		filterData.setToDate(DateHelper.toCalendar((Date)filterParams.get("toDate")));
		filterData.setCreditCards(filterParams.get("showCreditCards")!=null);
		filterData.setCash(filterParams.get("showCash")!= null);
		filterData.setOnlyOwnCards(filterParams.get("showOtherAccounts")==null);
		filterData.setTransfer(filterParams.get("showTransfer")!= null);
		filterData.setShowCashPayments(null);
		saveLog(filterData,operation,frm);
		doFilter(filterData,operation,frm);
	}

	private void saveLog(FinanceFilterData data, FinancesOperationBase operation, FinanceFormBase frm) throws Exception
	{
		Map<String, Object> defaultParams = getDefaultFilterParameters(frm,operation);
		FilterOutcomePeriodLogRecord record = new FilterOutcomePeriodLogRecord();
		record.setDate(DateHelper.getCurrentDate());
		record.setPeriodType(getPeriodType(data.getFromDate(),data.getToDate(),data.getTypeDate()));
		record.setTerBank(operation.getPersonDepartment().getRegion());
		if ( ((String)defaultParams.get("typeDate")).compareTo(data.getTypeDate()) == 0
				&& DateHelper.toCalendar((Date)defaultParams.get("fromDate")).compareTo(data.getFromDate()) == 0
				&& DateHelper.toCalendar((Date)defaultParams.get("toDate")).compareTo(data.getToDate()) == 0 )
			record.setIsDefault(true);
		FilterOutcomePeriodLogHelper.writeEntryToLog(record);
	}

	private PeriodType getPeriodType(Calendar from, Calendar to, String typeDate)
	{
		if (MonthPeriodFilter.TYPE_PERIOD_MONTH.equals(typeDate))
			return PeriodType.MONTH;
		Long days = DateHelper.daysDiff(from,to);
		if (days <= 10)
			return PeriodType.BEFORE_TEN_DAYS;
		if (days <= 20)
			return PeriodType.FROM_TEN_TO_TWENTY_DAYS;
		if (days <= 30)
			return PeriodType.FROM_TWENTY_TO_THIRTY_DAYS;
		if (days <= 90)
			return PeriodType.FROM_THIRTY_TO_NINETY_DAYS;
		if (days <= 183)
			return PeriodType.FROM_NINETY_DAYS_TO_HALF_YEAR;
		return PeriodType.MORE_THAN_HALF_YEAR;
	}
}
