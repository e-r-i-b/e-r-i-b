package com.rssl.phizic.web.client.sberbankForEveryDay;

import com.rssl.phizic.operations.sberbankForEveryDay.ViewSberbankForEveryDayClaimOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Просмотр заявки "Сбербанк на каждый день"
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ViewSberbankForEveryDayClaimAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewSberbankForEveryDayClaimOperation operation = createOperation(ViewSberbankForEveryDayClaimOperation.class, "CreateSberbankForEveryDayClaimService");
		ViewSberbankForEveryDayClaimForm frm = (ViewSberbankForEveryDayClaimForm)form;

		if (frm.getId() != null)
			operation.initialize(frm.getId());
		frm.setClaim(operation.getIssueCardClaim());

		return mapping.findForward(FORWARD_START);
	}
}
