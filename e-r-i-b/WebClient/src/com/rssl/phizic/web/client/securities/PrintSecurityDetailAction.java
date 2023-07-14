package com.rssl.phizic.web.client.securities;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.securities.GetSecurityDetailOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 */

public class PrintSecurityDetailAction extends OperationalActionBase
{
	public static final String FORWARD_PRINT = "Print";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
	{
		ShowSecurityDetailForm frm = (ShowSecurityDetailForm) form;
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ActivePerson user = personData.getPerson();
		frm.setUser(user);

		GetSecurityDetailOperation operation = createOperation(GetSecurityDetailOperation.class);
		operation.initialize(frm.getId());
		frm.setLink(operation.getEntity());

		return mapping.findForward(FORWARD_PRINT);
	}

}
