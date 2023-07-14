package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.business.connectUdbo.MultiInstanceUDBOClaimRulesService;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author basharin
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ViewUDBOTermAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewUDBOTermForm frm = (ViewUDBOTermForm) form;
		frm.setTerm(MultiInstanceUDBOClaimRulesService.findActiveRulesText());
		return mapping.findForward(FORWARD_START);
	}
}
