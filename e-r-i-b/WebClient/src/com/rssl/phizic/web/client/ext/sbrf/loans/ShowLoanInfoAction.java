package com.rssl.phizic.web.client.ext.sbrf.loans;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loans.LoanServiceHelper;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.LoanState;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.operations.ext.sbrf.loans.GetLoanInfoOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanListOperation;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MoneyHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.client.loans.ShowLoanInfoForm;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 24.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowLoanInfoAction extends OperationalActionBase
{
	private static final String FROM_PERIOD_FILTER = "fromPeriod";
	private static final String TO_PERIOD_FILTER = "toPeriod";
    private static final String PERIOD_FILTER = "period";
    protected static final String FORWARD_SHOW_JMS = "ShowJMS";

    private ClientConfig clientConfig = ConfigFactory.getConfig(ClientConfig.class);

	protected Map<String, String> getKeyMethodMap()
    {
	    Map<String, String> keyMap = new HashMap<String, String>();
	    keyMap.put("button.filter",  "filter");
        return keyMap;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
    {
	    ShowLoanInfoForm frm = (ShowLoanInfoForm) form;
	    Long linkId = frm.getId();

	    GetLoanInfoOperation operation = createOperation(GetLoanInfoOperation.class);
	    operation.initialize(linkId);

	    LoanLink link = operation.getLoanLink();

	    GetLoanAbstractOperation abstractOperation = createOperation(GetLoanAbstractOperation.class);
	    abstractOperation.initialize(link);

	    Map<String, Object> filterParams = getDefaultFilterParams();
	    doFilter(request, filterParams, abstractOperation, link, frm);
	    setAnotherLoans(frm,link);
	    setFilter(frm, filterParams);

        frm.setLoanAccountInfo(operation.getLoanAccountsList());
	    frm.setEarlyLoanRepaymentAllowed(operation.isEarlyLoanRepaymentAllowed());
	    frm.setEarlyLoanRepaymentPossible(operation.isEarlyLoanRepaymentPossible());
	    if (operation.isUseStoredResource())
	    {
		    saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) link.getLoan()));
	    }

	    return chooseStartForward(mapping);
    }

	public ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowLoanInfoForm frm = (ShowLoanInfoForm) form;
		Map<String, Object> filterParams;

		GetLoanInfoOperation operation = createOperation(GetLoanInfoOperation.class);
	    operation.initialize(frm.getId());

	    LoanLink link = operation.getLoanLink();
	    frm.setLoanLink(link);

		GetLoanAbstractOperation abstractOperation = createOperation(GetLoanAbstractOperation.class);
	    abstractOperation.initialize(link);

		Form filterForm = ShowLoanInfoForm.FILTER_FORM;
		FieldValuesSource valuesSource = new MapValuesSource(frm.getFilters());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, filterForm);
		if(processor.process())
		{
			Map<String, Object> result = processor.getResult();
			Calendar fromDate = DateHelper.toCalendar((Date) result.get(FROM_PERIOD_FILTER));
			fromDate.set(Calendar.DAY_OF_MONTH, 1);
			Calendar toDate = DateHelper.toCalendar((Date) result.get(TO_PERIOD_FILTER));
			toDate.add(Calendar.MONTH, 1);
			toDate.add(Calendar.DAY_OF_MONTH,-1);
			filterParams = new HashMap<String, Object>();
			filterParams.put(FROM_PERIOD_FILTER, fromDate.getTime());
			filterParams.put(TO_PERIOD_FILTER, toDate.getTime());
			filterParams.put("typePeriod", PERIOD_FILTER);
		}
		else
		{
			filterParams = getDefaultFilterParams();
			saveErrors(request, processor.getErrors());
		}

		doFilter(request, filterParams,abstractOperation, link, frm);
		setFilter(frm, filterParams);
		setAnotherLoans(frm,link);

		if (operation.isUseStoredResource())
		{
			saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) link.getLoan()));
		}
        return chooseStartForward(mapping);
	}

	private Map<String, Object> getDefaultFilterParams()
	{
		Map<String, Object> filterParameters = new HashMap<String, Object>();

		Calendar fromDate = DateHelper.getCurrentDate();
		fromDate.add(Calendar.MONTH,-1);
		fromDate.set(Calendar.DAY_OF_MONTH, 1);

		Calendar toDate = DateHelper.getCurrentDate();
		toDate.add(Calendar.MONTH,11);
		toDate.set(Calendar.DAY_OF_MONTH, 1);
		toDate.add(Calendar.DAY_OF_MONTH,-1);

		filterParameters.put(TO_PERIOD_FILTER,toDate.getTime());
		filterParameters.put(FROM_PERIOD_FILTER,fromDate.getTime());
		filterParameters.put("typePeriod", PERIOD_FILTER);
		return filterParameters;
	}

	private void doFilter(HttpServletRequest request, Map<String, Object> filterParameters, GetLoanAbstractOperation operation, LoanLink link, ShowLoanInfoForm form) throws Exception
	{
		Loan loan = link.getLoan();
		Calendar nextPaymentDate = loan.getNextPaymentDate();
		Calendar fromDate = DateHelper.toCalendar((Date)filterParameters.get(FROM_PERIOD_FILTER));
		Calendar toDate = DateHelper.toCalendar((Date)filterParameters.get(TO_PERIOD_FILTER));

		Pair<Long, Long> countRec = LoanServiceHelper.getPaymentCountByDates(nextPaymentDate, fromDate, toDate);
		long startNumber = countRec.getFirst(); // номер платежа в графике, начиная с которого строим график
		long count = countRec.getSecond();       // количество платеже в графике
		Pair<Map<LoanLink, ScheduleAbstract>, Map<LoanLink, String>> scheduleAbstract = operation.getScheduleAbstract(startNumber,count, false);

		form.setScheduleAbstract(scheduleAbstract.getFirst().get(link));
		form.setLoanLink(link);
		form.setField("startNumber",startNumber);
		form.setField("count",count);
		form.setField("loanName",link.getName());

		if (scheduleAbstract.getSecond().size()>0)
		{
			ActionMessages errors = new ActionMessages();
			for (String message : scheduleAbstract.getSecond().values())
			{
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
			}
			saveErrors(request, errors);
		}
	}

	private void setFilter(ShowLoanInfoForm frm, Map<String, Object> filterParameters)
	{
		frm.setFilters(filterParameters);
	}

    private ActionForward chooseStartForward(ActionMapping mapping) {
        if (clientConfig.isJmsForLoanEnabled())
            return mapping.findForward(FORWARD_SHOW_JMS);
        return mapping.findForward(FORWARD_SHOW);
	}

	/**
	 * Устанавливает остальные кредиты
	 *
	 * @param frm форма
	 * @param link ссылка на кредит
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private void setAnotherLoans(ShowLoanInfoForm frm, LoanLink link) throws BusinessException, BusinessLogicException
	{
		GetLoanListOperation listOperation = createOperation(GetLoanListOperation.class);
	    List<LoanLink> anotherLoans = listOperation.getLoans();
	    anotherLoans.remove(link);

		Iterator iterator = anotherLoans.iterator();
		while(iterator.hasNext())
		{
			LoanLink loanLink = (LoanLink)iterator.next();
			if(loanLink.getLoan().getState()==LoanState.closed)
				iterator.remove();
		}
	    frm.setAnotherLoans(anotherLoans);
	}

}
