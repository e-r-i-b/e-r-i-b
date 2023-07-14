package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.finances.targets.GetTargetOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.FilterActionForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 24.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountOperationsAction extends OperationalActionBase
{
	public static final String FORWARD_SHOW = "Show";
	private static final String PERIOD_TYPE_WEEK = "week";
	private static final String PERIOD_TYPE_MONTH = "month";

	protected Map<String, String> getKeyMethodMap()
    {
	    Map<String, String> keyMap = new HashMap<String, String>();
	    keyMap.put("button.filter",  "filter");
        return keyMap;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
    {
	    ShowAccountOperationsForm frm = (ShowAccountOperationsForm) form;
	    Long linkId = frm.getId();

	    GetAccountAbstractExtendedOperation operation = createOperation(GetAccountAbstractExtendedOperation.class);
		operation.initialize(linkId);

	    Map<String, Object> filterParameters = getFilterParameters(PERIOD_TYPE_MONTH);
	    doFilter(filterParameters, operation, form);
	    updateFormData(frm, operation);
	    setDefaultFilter(frm);

	    if (operation.isUseStoredResource())
	    {
		    frm.setAbstractMsgError(getAbstarctMessageError());
			saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) operation.getAccount().getAccount()));
	    }

	    return mapping.findForward(FORWARD_SHOW);
    }

	protected String getAbstarctMessageError(){
		return StoredResourceMessages.getUnreachableStatement();
	}

	protected void updateFormData(ShowAccountOperationsForm form, GetAccountAbstractExtendedOperation operation) throws BusinessException, BusinessLogicException
	{
		AccountLink accountLink = operation.getAccount();
		AccountAbstract accountAbstract = null;
		try
		{
			accountAbstract = operation.getAccountAbstract();
		}
		catch (BusinessLogicException ex)
		{
			log.error("Невозможно получить информацию по счету " + accountLink.getNumber(), ex);
		}

		form.setAccountLink(accountLink);
		form.setAccountAbstract(accountAbstract);

	    GetAccountsOperation operationAccounts = createOperation(GetAccountsOperation.class);
		List<AccountLink> otherAccountsLinks = operationAccounts.getActiveAccounts();
		if (otherAccountsLinks.contains(accountLink))
		{
			otherAccountsLinks.remove(accountLink);
		}
		form.setOtherAccounts(otherAccountsLinks);

		if (checkAccess(GetTargetOperation.class))
		{
			GetTargetOperation targetOperation = createOperation(GetTargetOperation.class);
			form.setTarget(targetOperation.getTargetByAccountId(accountLink.getId()));
		}

		if(accountAbstract == null)
		{
			form.setAbstractMsgError(getAbstarctMessageError());
			return;
		}
		if (operation.isBackError())
				form.setAbstractMsgError("Данная операция временно недоступна. Пожалуйста, повторите попытку позже.");
		else
			form.setAbstractMsgError(operation.getAccountAbstractMsgErrorMap().get(accountLink));
	}

	protected Form getFilterForm()
	{
		return ShowAccountOperationsForm.FILTER_FORM;
	}

	public final ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowAccountOperationsForm frm = (ShowAccountOperationsForm) form;
		GetAccountAbstractExtendedOperation operation = createOperation(GetAccountAbstractExtendedOperation.class);
		operation.initialize(frm.getId());

		FieldValuesSource valuesSource = new MapValuesSource(frm.getFilters());
		Form filterForm = getFilterForm();
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, filterForm);
		if(processor.process())
		{
			doFilter(processor.getResult(),operation, form);
	        updateFormData(frm,operation);
			setDefaultFilter(frm);
		}
		else
		{
			String typePeriod = frm.getFilter("typePeriod").toString();
			doFilter(getFilterParameters(typePeriod), operation, form);
			updateFormData(frm, operation);
			setDefaultFilter(frm);
			saveErrors(request, processor.getErrors());
		}

		if (operation.isUseStoredResource())
		{
			saveInactiveESMessage(currentRequest(), String.format("Информация по Вашим вкладам актуальна на %1$td.%1$tm.%1$tY г.", ((AbstractStoredResource) operation.getAccount().getAccount()).getEntityUpdateTime()));
		}
		
		return mapping.findForward(FORWARD_SHOW);
	}

	private void setDefaultFilter(ShowAccountOperationsForm frm)
	{
		if (frm.getFilter("fromPeriod") == null)
		{
			Calendar startDate = new GregorianCalendar();
			startDate.add(Calendar.MONTH, -1);
			frm.setFilter("fromPeriod", startDate.getTime());
		}
		if (frm.getFilter("toPeriod") == null)
			frm.setFilter("toPeriod", new GregorianCalendar().getTime());
		if (StringHelper.isEmpty((String) frm.getFilter("typePeriod")))
			frm.setFilter("typePeriod", PERIOD_TYPE_MONTH);
	}

	private void doFilter(Map<String, Object> filterParameters, GetAccountAbstractExtendedOperation operation, ActionForm form)
	{
		String periodType = (String)filterParameters.get("typePeriod");
		if(PERIOD_TYPE_WEEK.equals(periodType) || PERIOD_TYPE_MONTH.equals(periodType))
		{
			filterParameters = getFilterParameters(periodType);
		}
		Calendar fromDate = DateHelper.toCalendar((Date)filterParameters.get("fromPeriod"));
		Calendar toDate = DateHelper.toCalendar((Date)filterParameters.get("toPeriod"));
		operation.setDateTo(toDate);
		operation.setDateFrom(fromDate);
		FilterActionForm frm = (FilterActionForm)form;
		frm.setFilters(filterParameters);
	}

	private Map<String, Object> getFilterParameters(String typePeriod)
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

	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		ShowAccountOperationsForm frm = (ShowAccountOperationsForm) form;
		return mapping.getPath().concat((frm.getTarget()!=null?  "/target" : ""));
	}
}
