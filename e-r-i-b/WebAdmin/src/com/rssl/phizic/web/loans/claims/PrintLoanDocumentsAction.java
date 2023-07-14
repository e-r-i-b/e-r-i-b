package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.loans.loan.PrintLoanDocumentsOperation;
import com.rssl.phizic.business.BusinessException;

import java.util.Map;
import java.util.HashMap;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gladishev
 * @ created 06.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class PrintLoanDocumentsAction extends OperationalActionBase
{
    public static final String FORWARD_START   = "Start";

    protected Map<String, String> getKeyMethodMap()
    {
	    return new HashMap<String,String>();
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    PrintLoanDocumentsForm frm = (PrintLoanDocumentsForm) form;
	    PrintLoanDocumentsOperation operation = createOperation(PrintLoanDocumentsOperation.class);
	    try{
	        operation.initialize(frm.getId()); //инициализация по id заявки на кредит

		    frm.setClaim(operation.getClaim());
	        frm.setLoan(operation.getLoan());
	        frm.setDocuments(operation.getDocuments());
	        frm.setSchedule(operation.getSchedule());

		    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getClaim()));
	    }
		catch(BusinessException e)
		{
		}

		return mapping.findForward(FORWARD_START);
    }
}
