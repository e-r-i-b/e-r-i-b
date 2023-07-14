package com.rssl.phizic.web.client.ext.sbrf.loans;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.operations.ext.sbrf.loans.GetLoanInfoOperation;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;

import java.util.Map;
import java.util.HashMap;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 01.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class PrintLoanInfoAction extends OperationalActionBase
{
	public static final String FORWARD_PRINT = "Print";

	protected Map<String, String> getKeyMethodMap()
    {
	    Map<String, String> keyMap = new HashMap<String, String>();
        return keyMap;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
    {
	    PrintLoanInfoForm frm = (PrintLoanInfoForm) form;
	    PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	    ActivePerson user = personData.getPerson();
		frm.setUser(user);

	    Long linkId = frm.getId();
	    GetLoanInfoOperation operation = createOperation(GetLoanInfoOperation.class);
	    operation.initialize(linkId);

	    LoanLink link = operation.getLoanLink();
	    frm.setLoanLink(link);

	    return mapping.findForward(FORWARD_PRINT);
    }

}
