package com.rssl.phizic.web.atm.ext.sbrf.loans;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.loans.LoanServiceHelper;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.operations.ext.sbrf.loans.ClosedLoanException;
import com.rssl.phizic.operations.ext.sbrf.loans.GetLoanInfoOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
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
 * @ created 24.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowLoanInfoAction extends OperationalActionBase
{
	public static final String FORWARD_SHOW = "Show";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
    {
	    ShowLoanInfoForm frm = (ShowLoanInfoForm) form;
	    Long linkId = frm.getId();
	    GetLoanInfoOperation operation  = createOperation(GetLoanInfoOperation.class);
	    try
	    {
		    operation.initialize(linkId);
	    }
	    catch (ResourceNotFoundBusinessException ex)
	    {
		    log.error(ex);
		    throw new AccessControlException(ex.getMessage());
	    }
        catch (ClosedLoanException e)
        {
            saveError(request, e);
            return mapping.findForward(FORWARD_SHOW);
        }

	    LoanLink link = operation.getLoanLink();



	    GetLoanAbstractOperation abstractOperation = null;
	    if (checkAccess(GetLoanAbstractOperation.class))
	    {
	        abstractOperation = createOperation(GetLoanAbstractOperation.class);
	        abstractOperation.initialize(link);
	    }

	    Map<String, Object> filterParams = getDefaultFilterParams();
	    doFilter(filterParams, abstractOperation, link, frm);

	    return mapping.findForward(FORWARD_SHOW);
    }

	protected void getLoneAbstract (Date from, Date to, GetLoanAbstractOperation operation, LoanLink link, ShowLoanInfoForm form) throws BusinessException
	{
		Loan loan = link.getLoan();
		Calendar nextPaymentDate = loan.getNextPaymentDate();
		Calendar fromDate = DateHelper.toCalendar(from);
		Calendar toDate = DateHelper.toCalendar(to);

		Pair<Long, Long> countRec = LoanServiceHelper.getPaymentCountByDates(nextPaymentDate, fromDate, toDate);
		long startNumber = countRec.getFirst(); // номер платежа в графике, начиная с которого строим график
		long count = countRec.getSecond();       // количество платеже в графике

		if (operation != null)
		{
			Map<LoanLink, ScheduleAbstract> scheduleAbstract = operation.getScheduleAbstract(startNumber,count, false).getFirst();
			form.setScheduleAbstract(scheduleAbstract.get(link));
		}
		form.setField("startNumber",startNumber);
		form.setField("count",count);
	}

	protected void getLoneAbstract (Long count, GetLoanAbstractOperation operation, LoanLink link, ShowLoanInfoForm form) throws BusinessException
	{
		Calendar fromDate = DateHelper.getCurrentDate();
		fromDate.add(Calendar.MONTH,-1);

		Calendar toDate = DateHelper.getCurrentDate();
		toDate.add(Calendar.MONTH, count.intValue()-1);

		getLoneAbstract (fromDate.getTime(), toDate.getTime(), operation, link, form);
	}

	private void doFilter(Map<String, Object> filterParameters, GetLoanAbstractOperation operation, LoanLink link, ShowLoanInfoForm form) throws BusinessException
	{
		getLoneAbstract ((Date)filterParameters.get("fromPeriod"), (Date)filterParameters.get("toPeriod"), operation, link, form);

		form.setLoanLink(link);
		form.setField("loanName",link.getName());
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
		
		filterParameters.put("toPeriod",toDate.getTime());
		filterParameters.put("fromPeriod",fromDate.getTime());

		return filterParameters;
	}

}
