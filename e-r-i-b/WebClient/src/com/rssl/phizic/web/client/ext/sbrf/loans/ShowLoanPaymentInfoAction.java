package com.rssl.phizic.web.client.ext.sbrf.loans;

import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.gate.loans.ScheduleItem;
import com.rssl.phizic.operations.ext.sbrf.loans.GetLoanInfoOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 29.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowLoanPaymentInfoAction extends OperationalActionBase
{
	public static final String FORWARD_SHOW = "Show";

	protected Map<String, String> getKeyMethodMap()
    {
	    Map<String, String> keyMap = new HashMap<String,String>();
        return keyMap;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
	    ShowLoanPaymentInfoForm frm = (ShowLoanPaymentInfoForm) form;
	    Long linkId = frm.getId();

	    GetLoanInfoOperation operation = createOperation(GetLoanInfoOperation.class);
	    operation.initialize(linkId);

	    LoanLink link = operation.getLoanLink();
	    frm.setLoanLink(link);

	    GetLoanAbstractOperation abstractOperation = createOperation(GetLoanAbstractOperation.class);
	    abstractOperation.initialize(link);

	    Map<LoanLink,ScheduleAbstract> scheduleAbstractMap = abstractOperation.getScheduleAbstract(frm.getStartNumber(),frm.getCount(), false).getFirst();
	    ScheduleAbstract scheduleAbstract = scheduleAbstractMap.get(link);
	    Long paymentNumber = frm.getPaymentNumber();
	    for(ScheduleItem schedule : scheduleAbstract.getSchedules())
	        if(paymentNumber.equals(schedule.getPaymentNumber()))
	            frm.setScheduleItem(schedule);	    	    

	    return mapping.findForward(FORWARD_SHOW);
    }

}
