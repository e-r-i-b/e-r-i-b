package com.rssl.phizic.web.erkcemployee;

import com.rssl.phizic.operations.erkc.ERKCEmployeeMultiBlockOperation;
import com.rssl.phizic.operations.erkc.context.ERKCEmployeeContext;
import com.rssl.phizic.operations.erkc.context.ERKCEmployeeData;
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
 * Ёкшен сотрудников ≈– ÷, срабатывающий после смены блока
 */

public class ERKCEmployeeAfterChangeBlockAction extends OperationalActionBase
{
	private static final String GO_TO_LIST_FORWARD = "ToList";
	private static final String GO_TO_FUNCTIONAL_FORWARD = "ToERKC";

	@Override
	public final ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ERKCEmployeeMultiBlockOperation operation = createOperation(ERKCEmployeeMultiBlockOperation.class);
		ERKCEmployeeAfterChangeBlockForm form = (ERKCEmployeeAfterChangeBlockForm) frm;
		if (form.getId() == null)
			operation.initializeRestore();
		else
			operation.initializeRestoreByPersonId(form.getId());

		operation.restoreNodeContext();

		if (operation.isClientDefined())
		{
			ERKCEmployeeData erkcEmployeeData = ERKCEmployeeContext.getData();
			Map<String, Object> parameters = erkcEmployeeData.getAdditionalParameters();
			if (erkcEmployeeData.getCurrentFunctional() == Functional.paymentWaitConfirm)
				parameters.put("person", erkcEmployeeData.getCurrentPersonId());
			ActionRedirect redirect = new ActionRedirect(mapping.findForward(GO_TO_FUNCTIONAL_FORWARD));
			for (Map.Entry<String, Object> entry : parameters.entrySet())
				redirect.addParameter("parameters(" + entry.getKey() + ")", entry.getValue());

			return redirect;
		}

		form.setData(operation.getPersons());
		return mapping.findForward(GO_TO_LIST_FORWARD);
	}
}
