package com.rssl.phizic.web.erkcemployee;

import com.rssl.phizic.operations.erkc.ERKCEmployeeMultiBlockOperation;
import com.rssl.phizic.operations.erkc.context.ERKCEmployeeContext;
import com.rssl.phizic.operations.erkc.context.Functional;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 12.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен смены блока сотрудников ≈– ÷
 */

public class ERKCEmployeeChangeBlockAction extends OperationalActionBase
{
	private static final String DO_CHANGE_BLOCK_ACTION_FORWARD = "DoChange";
	private static final String AFTER_CHANGE_BLOCK_ACTION_FORWARD = "AfterChange";

	@Override
	public final ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ERKCEmployeeChangeBlockForm form = (ERKCEmployeeChangeBlockForm) frm;
		ERKCEmployeeMultiBlockOperation operation = createOperation(ERKCEmployeeMultiBlockOperation.class);
		if (StringHelper.isNotEmpty(form.getFunctional()))
			ERKCEmployeeContext.getData().setCurrentFunctional(Functional.valueOf(form.getFunctional()));

		ERKCEmployeeContext.getData().setAdditionalParameters(form.getParameters());
		operation.storeNodeContext();
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(DO_CHANGE_BLOCK_ACTION_FORWARD));
		redirect.addParameter("nodeId", form.getNodeId());
		redirect.addParameter("action", mapping.findForward(AFTER_CHANGE_BLOCK_ACTION_FORWARD).getPath());
		return redirect;
	}
}
