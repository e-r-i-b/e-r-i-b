package com.rssl.phizic.web.atm.ext.sbrf.accounts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.MoneyBoxListOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.security.AccessControlException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountInfoAction extends OperationalActionBase
{
	public static final String FORWARD_SHOW = "Show";
	protected static final String PERIOD_TYPE_MONTH = "month";

	protected Map<String, String> getKeyMethodMap()
    {
	    Map<String, String> keyMap = new HashMap<String, String>();
        return keyMap;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
    {
	    ShowAccountInfoForm frm = (ShowAccountInfoForm) form;
	    Long linkId = frm.getId();

	    GetAccountAbstractExtendedOperation operation = createOperation(GetAccountAbstractExtendedOperation.class);
	    try
	    {
			operation.initialize(linkId);
	    }
	    catch (ResourceNotFoundBusinessException ex)
	    {
		    log.error(ex);
		    throw new AccessControlException(ex.getMessage());
	    }
	    Map<String, Object> filterParameters = getFilterParameters(PERIOD_TYPE_MONTH);
	    doFilter(filterParameters,operation);
	    updateFormData(frm, operation);
	    setDefaultFilter(PERIOD_TYPE_MONTH, frm);

	    return mapping.findForward(FORWARD_SHOW);
    }

	protected void updateFormData(ShowAccountInfoForm form, GetAccountAbstractExtendedOperation operation) throws BusinessException, BusinessLogicException
	{
		AccountLink accountLink = operation.getAccount();
		AccountAbstract accountAbstract = null;
		try
		{
			accountAbstract = operation.getAccountAbstract();
			form.setError(operation.isBackError());
			//Получаем список копилок
			if (checkAccess(MoneyBoxListOperation.class, "MoneyBoxManagement")) {
				MoneyBoxListOperation moneyBoxOperation = createOperation(MoneyBoxListOperation.class,"MoneyBoxManagement");
				moneyBoxOperation.initialize(accountLink);
				form.setMoneyBoxes(moneyBoxOperation.getData());
			}
		}
		catch (BusinessLogicException ex)
		{
			log.error("Невозможно получить информацию по счету " + accountLink.getNumber(), ex);
		}

		form.setAccountLink(accountLink);
		form.setAccountAbstract(accountAbstract);
		form.setField("accountName",accountLink.getName());
	}

	protected void setDefaultFilter(String typePeriod, ShowAccountInfoForm frm)
	{
		Map<String, Object> filterParameters = getFilterParameters(typePeriod);
		frm.setFilter("toPeriod", filterParameters.get("toPeriod"));
		frm.setFilter("fromPeriod", filterParameters.get("fromPeriod"));
		frm.setFilter("typePeriod", filterParameters.get("typePeriod"));
	}

	protected void doFilter(Map<String, Object> filterParameters, GetAccountAbstractExtendedOperation operation)
	{
		String periodType = (String)filterParameters.get("typePeriod");
		filterParameters = getFilterParameters(periodType);
		Calendar fromDate = DateHelper.toCalendar((Date)filterParameters.get("fromPeriod"));
		Calendar toDate = DateHelper.toCalendar((Date)filterParameters.get("toPeriod"));
		operation.setDateTo(toDate);
		operation.setDateFrom(fromDate);
	}

	protected Map<String, Object> getFilterParameters(String typePeriod)
	{
		Map<String, Object> filterParameters = new HashMap<String, Object>();
		Calendar toDate = DateHelper.getCurrentDate();
		Calendar fromDate = DateHelper.getCurrentDate();
		
		if(PERIOD_TYPE_MONTH.equals(typePeriod))
			fromDate.add(Calendar.MONTH, -1);
		else
			fromDate.add(Calendar.WEEK_OF_MONTH, -1);

		filterParameters.put("typePeriod", typePeriod);
		filterParameters.put("toPeriod",toDate.getTime());
		filterParameters.put("fromPeriod",fromDate.getTime());

		return filterParameters;
	}
}