package com.rssl.phizic.web.common.client.finances;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.operations.finances.FinancesStatus;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.component.DatePeriodFilter;
import com.rssl.phizic.web.component.PeriodFilter;
import com.rssl.phizic.web.log.FormLogParametersReader;
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
 * @author rydvanskiy
 * @ created 28.07.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract class FinanceFilterActionBase extends FinanceActionBase
{
	private static final String FORWARD_ERROR = "Error";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.filter", "filter");
		return map;
	}

	protected ActionForward getErrorForward(ActionMapping mapping)
	{
		return mapping.findForward(FORWARD_ERROR);
	}

	/**
	 * ����� ����������� ����������� ���������� ��������,
	 * ��� ������ ���������� true
	 * @param operation ������� ��������
	 * @param form �����
	 * @return true  -- ������� ������
	 *         false -- ������ ���
	 */
	protected boolean haveError(FinancesOperationBase operation, FinanceFormBase form)
	{
		form.setLastModified(operation.getLastModified());
		form.setFinancesStatus(operation.getStatus());
		return operation.getStatus() != FinancesStatus.allOk;
	}

	protected Form getFilterForm(ActionForm frm)
	{
		return FinanceFormBase.FILTER_FORM;
	}

	/**
	 * ��������� ������
	 * @param filterData ����
	 * @param operation ��������
	 * @param frm �����
	 */
	protected abstract void doFilter(FinanceFilterData filterData, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException, BusinessLogicException;

	/**
	 * @param filterParams ���� ���������� �������
	 * @return ��������� ������ �������
	 */
	protected PeriodFilter getDefaultPeriodFilter(Map<String, Object> filterParams)
	{
		return new DatePeriodFilter(filterParams);
	}

	/**
	 * ��������� �������� �������
	 * @param frm �����
	 * @param operation ��������
	 * @return �������� ���������� �� ���������.
	 */
	protected Map<String, Object> getDefaultFilterParameters(FinanceFormBase frm, FinancesOperationBase operation)
	{
		Map<String, Object> params = new HashMap<String, Object>();

		params.put(DatePeriodFilter.TYPE_PERIOD, DatePeriodFilter.TYPE_PERIOD_MONTH);
		params.put("showOtherAccounts", "on");
		params.put("incomeType", FinanceFormBase.OUTCOME_TYPE);
		params.put("showCash", "on");
		DatePeriodFilter datePeriodFilter = new DatePeriodFilter(params);

		return datePeriodFilter.normalize().getParameters();
	}

	/**
	 * �������� ������ ����������
	 * @param filterParams
	 * @param operation
	 * @param frm
	 * @throws Exception
	 */
	protected void doFilter(Map filterParams, FinancesOperationBase operation, FinanceFormBase frm) throws Exception
	{
		FinanceFilterData filterData = new FinanceFilterData();
		filterData.setFromDate( DateHelper.toCalendar((Date)filterParams.get("fromDate")));
		filterData.setToDate(DateHelper.toCalendar((Date)filterParams.get("toDate")));
		filterData.setCash(filterParams.get("showCash")!= null);
		filterData.setIncome(filterParams.get("incomeType").equals(FinanceFormBase.INCOME_TYPE));
		filterData.setOnlyOwnCards(filterParams.get("showOtherAccounts")== null);

		doFilter(filterData, operation, frm);
	}

	/**
	 * ����� ������������� �� ������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FinanceFormBase frm = (FinanceFormBase) form;

		FinancesOperationBase operation = createFinancesOperation(frm);

		if (haveError(operation, frm))
			return getErrorForward(mapping);

		frm.setLastModified(operation.getLastModified());
		//��������� ���� �� ���������� ������
		checkFailedClaims(operation, request);

		Map<String, Object> filterParameters = getDefaultFilterParameters(frm, operation);

		//��������� ������ ���������� �� ���������
		addLogParameters(new FormLogParametersReader("��������� ����������", getFilterForm(frm), filterParameters));

		doFilter(filterParameters, operation, frm);
		frm.setFilters(filterParameters);
		return mapping.findForward(FORWARD_SHOW);
	}

	/**
	 * ����� ������������� ��� ������� �� ������ "�����������"
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FinanceFormBase frm = (FinanceFormBase) form;

		FinancesOperationBase operation = createFinancesOperation(frm);

		if (haveError(operation, frm))
			return getErrorForward(mapping);

		frm.setLastModified(operation.getLastModified());
		//��������� ���� �� ���������� ������
		checkFailedClaims(operation, request);
		
		FieldValuesSource valuesSource = getMapValueSource(frm);
		Map<String, Object> filterParameters = frm.getFilters();

		//��������� ������ ����������
		Form filterForm = getFilterForm(frm);
		addLogParameters(new FormLogParametersReader("��������� ����������", filterForm, filterParameters));
		// ���������� ������ � ���� ��������� ���������� �� ��������� ������ �� ���������
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, filterForm);
		if (processor.process())
		{
			PeriodFilter        dateParams   = getDefaultPeriodFilter(processor.getResult());
			Map<String, Object> filterParams = dateParams.normalize().getParameters();
			doFilter(filterParams, operation, frm);
			frm.setFilters(filterParams);
		}
		else
		{
			frm.setHasErrors(true);
			saveErrors(request, processor.getErrors());
		}

		return mapping.findForward(FORWARD_SHOW);
	}

	protected MapValuesSource getMapValueSource(FinanceFormBase frm)
	{
		return new MapValuesSource(frm.getFilters());
	}
}
