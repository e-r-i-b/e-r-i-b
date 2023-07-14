package com.rssl.phizic.web.common.client.payments.internetShops;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.Period;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.payment.internetShops.InternetOrderListOperation;
import com.rssl.phizic.web.actions.FilterPeriodHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.rssl.phizic.web.common.client.payments.internetShops.InternetOrderListForm.*;

/**
 * Акшн для формы просмотра списка интернет заказов.
 *
 * @author bogdanov
 * @ created 30.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class InternetOrderListAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ActionMessages msgs = HttpSessionUtils.getSessionAttribute(currentRequest(), Globals.ERROR_KEY);
		if (msgs != null && !msgs.isEmpty())
			saveErrors(currentRequest(), msgs);
		HttpSessionUtils.removeSessionAttribute(currentRequest(), Globals.ERROR_KEY);

		return createOperation(InternetOrderListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return InternetOrderListForm.FILTER_FORM;
	}

	@Override
	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(InternetOrderListForm.TO_DATE, Calendar.getInstance().getTime());
		Calendar monthBefore = Calendar.getInstance();
		monthBefore.add(Calendar.MONTH, -1);
		params.put(FROM_DATE, monthBefore.getTime());
		params.put(TYPE_DATE, "month");
		params.put(IS_DEFAULT, "true");

		return params;
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		InternetOrderListOperation op = (InternetOrderListOperation) operation;
		fill(frm, op, filterParams);
		frm.setFilters(filterParams);
	}

	protected void fill(ListFormBase form, InternetOrderListOperation operation, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData == null)
			throw new BusinessException("не указана personData");

		Period period = FilterPeriodHelper.getPeriod(
				(String) filterParams.get(FilterPeriodHelper.PERIOD_TYPE_FIELD_NAME),
				(Date) filterParams.get(FilterPeriodHelper.FROM_DATE_FIELD_NAME),
				(Date) filterParams.get(FilterPeriodHelper.TO_DATE_FIELD_NAME)
		);

		Calendar toDate = period.getToDate();
		Calendar fromDate =  period.getFromDate();
		BigDecimal toAmount = filterParams.get(TO_AMOUNT) != null ? new BigDecimal(filterParams.get(TO_AMOUNT).toString()) : null;
		BigDecimal fromAmount = filterParams.get(FROM_AMOUNT) != null ? new BigDecimal(filterParams.get(FROM_AMOUNT).toString()) : null;
		String currency = (String) filterParams.get(CURRENCY);
		String state = "true".equals(filterParams.get(InternetOrderListForm.IS_DEFAULT)) ? STATE_CODE_ALL : (String) filterParams.get(STATE_CODE);

		form.setData(operation.getOrders(fromDate, toDate, fromAmount, toAmount, currency, state));
	}
}
