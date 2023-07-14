package com.rssl.phizic.web.client.insurance;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.insurance.GetInsuranceDetailOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 01.04.2013
 * @ $Author$
 * @ $Revision$
 * Печать детальной информации по страховому/НПФ продукту
 */

public class PrintInsuranceDetailAction extends OperationalActionBase
{
	public static final String FORWARD_PRINT = "Print";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
    {
	    ShowInsuranceDetailForm frm = (ShowInsuranceDetailForm) form;
	    PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	    ActivePerson user = personData.getPerson();
		frm.setUser(user);

	    GetInsuranceDetailOperation operation = createOperation(GetInsuranceDetailOperation.class);
	    operation.initialize(frm.getId());
	    frm.setLink(operation.getEntity());

	    return mapping.findForward(FORWARD_PRINT);
    }

}
