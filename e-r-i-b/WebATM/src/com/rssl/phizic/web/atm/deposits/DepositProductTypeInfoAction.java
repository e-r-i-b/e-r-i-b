package com.rssl.phizic.web.atm.deposits;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.operations.deposits.DepositDetailsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Получение информации о подвиде вклада
 * @author Pankin
 * @ created 16.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class DepositProductTypeInfoAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		return null;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DepositProductTypeInfoForm frm = (DepositProductTypeInfoForm) form;
		DepositDetailsOperation operation = createOperation(DepositDetailsOperation.class, "AccountOpeningClaim");

		Form filterForm = DepositProductTypeInfoForm.FILTER_FORM;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(getFilterValuesSource(frm), filterForm);
		if (processor.process())
		{
			try
			{
				doFilter(processor.getResult(), operation, frm);
			}
			catch (BusinessLogicException e)
			{
				saveError(request, e);
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}

		return mapping.findForward(FORWARD_START);
	}

	private void doFilter(Map<String, Object> result, DepositDetailsOperation operation, DepositProductTypeInfoForm frm) throws BusinessException, BusinessLogicException
	{
		Long depositType = (Long) result.get(DepositProductTypeInfoForm.DEPOSIT_TYPE_FIELD);
		Calendar openingDate = DateHelper.toCalendar((Date) result.get(DepositProductTypeInfoForm.OPENING_DATE_FIELD));

		String periodString = (String) result.get(DepositProductTypeInfoForm.PERIOD_FIELD);
		DateSpan period = null;
		if (!StringHelper.isEmpty(periodString))
		{
			String[] periodStrings = periodString.split("-");
			period = new DateSpan(Integer.parseInt(periodStrings[0]), Integer.parseInt(periodStrings[1]),
					Integer.parseInt(periodStrings[2]));
		}
		
		String currencyCode = (String) result.get(DepositProductTypeInfoForm.CURRENCY_CODE_FIELD);
		String minBalanceString = (String) result.get(DepositProductTypeInfoForm.MIN_BALANCE_FIELD);
		BigDecimal minBalance = StringHelper.isEmpty(minBalanceString) ? null : new BigDecimal(minBalanceString);
		String depositGroup = (String) result.get(DepositProductTypeInfoForm.DEPOSIT_GROUP_FIELD);

		Map<String, String> depositInfo = operation.getDepositInfo(depositType, openingDate, period, currencyCode, minBalance, depositGroup, null, false);

		for (Map.Entry<String, String> entry : depositInfo.entrySet())
		{
			frm.setField(entry.getKey(), entry.getValue());
		}
	}

	private FieldValuesSource getFilterValuesSource(DepositProductTypeInfoForm frm)
	{
		Map<String,String> source = new HashMap<String,String>();

		source.put(DepositProductTypeInfoForm.DEPOSIT_TYPE_FIELD, frm.getDepositType());
		source.put(DepositProductTypeInfoForm.OPENING_DATE_FIELD, frm.getOpeningDate());
		source.put(DepositProductTypeInfoForm.PERIOD_FIELD, frm.getPeriod());
		source.put(DepositProductTypeInfoForm.CURRENCY_CODE_FIELD, frm.getCurrencyCode());
		source.put(DepositProductTypeInfoForm.MIN_BALANCE_FIELD, frm.getMinBalance());
		source.put(DepositProductTypeInfoForm.DEPOSIT_GROUP_FIELD, frm.getDepositGroup());

		return new MapValuesSource(source);
	}
}
