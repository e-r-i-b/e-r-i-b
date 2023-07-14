package com.rssl.phizic.web.atm.ext.sbrf.security;

import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.operations.security.GetRegistrationOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ѕолучение временного логина и парол€
 * @author Jatsky
 * @ created 13.12.13
 * @ $Author$
 * @ $Revision$
 */

public class GetRegistrationAction extends OperationalActionBase
{
	@Override public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GetRegistrationForm frm = (GetRegistrationForm) form;

		GetRegistrationOperation gro = new GetRegistrationOperation(PersonHelper.getLastClientLogin().getLastLogonCardNumber());
		frm.setLogin(gro.getLogin());
		frm.setPassword(gro.getPassword());

		return mapping.findForward(FORWARD_SHOW);
	}
}
