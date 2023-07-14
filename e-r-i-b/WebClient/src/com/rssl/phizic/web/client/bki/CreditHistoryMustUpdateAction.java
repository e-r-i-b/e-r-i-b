package com.rssl.phizic.web.client.bki;

import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gulov
 * @ created 17.10.14
 * @ $Author$
 * @ $Revision$
 */
public class CreditHistoryMustUpdateAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonContext.getPersonDataProvider().getPersonData().setMustUpdateCreditReport(Boolean.FALSE);
		response.getOutputStream().println(" ");
		return null;
	}

	@Override
	protected boolean isAjax()
	{
		return true;
	}
}
