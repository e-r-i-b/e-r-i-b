package com.rssl.phizic.web.erkcemployee;

import com.rssl.phizic.operations.erkc.context.ERKCEmployeeContext;
import com.rssl.phizic.operations.erkc.context.Functional;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 12.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Маршрутизирующий экшен для работы ЕРКЦ сотрудника
 */

public class ERKCEmployeeRoutingAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ERKCEmployeeRoutingForm form = (ERKCEmployeeRoutingForm) frm;
		Functional currentFunctional = ERKCEmployeeContext.getData().getCurrentFunctional();
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(currentFunctional.name()));
		for (Map.Entry<String, Object> entry : form.getParameters().entrySet())
			redirect.addParameter(entry.getKey(), entry.getValue());

		return redirect;
	}
}
