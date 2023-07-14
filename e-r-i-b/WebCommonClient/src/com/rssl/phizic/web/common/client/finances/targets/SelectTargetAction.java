package com.rssl.phizic.web.common.client.finances.targets;

import com.rssl.phizic.operations.finances.targets.SelectTargetOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшен списка целей АЛФ
 * @author lepihina
 * @ created 19.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class SelectTargetAction extends OperationalActionBase
{
	private static final String FORWARD_LIST = "List";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SelectTargetOperation operation = createOperation(SelectTargetOperation.class);
		operation.initialize();
		SelectTargetForm frm = (SelectTargetForm) form;

		if (operation.tooManyTargets())
		{
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(operation.getTooManyTargetsMessage(), false), null);
			return mapping.findForward(FORWARD_LIST);
		}

		frm.setHasTargets(operation.getTargetsCount() > 0);
		return mapping.findForward(FORWARD_START);
	}
}
