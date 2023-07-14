package com.rssl.phizic.web.atm.finances;

import com.rssl.phizic.operations.finances.ShowFinanceOperation;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;
import com.rssl.phizic.web.common.client.finances.ShowFinanceStructureAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mescheryakova
 * @ created 23.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowFinanceStructureATMAction extends ShowFinanceStructureAction
{
	protected Map<String, String> getKeyMethodMap()
    {
        Map<String, String> map = new HashMap<String, String>();
	    map.put("connect", "connect");
	    return map;
    }

	protected ActionForward nextStepShowFinancesStatus(ActionMapping mapping, FinanceFormBase form, HttpServletRequest request, HttpServletResponse response, ShowFinanceOperation operation)
			throws Exception
	{
		ShowFinanceStructureForm frm = (ShowFinanceStructureForm) form;
		checkFailedClaims(operation, request);
		frm.setFinancesStatus(operation.getStatus());
		return mapping.findForward(FORWARD_SHOW);
	}

	protected  ActionForward nextStepConnect(ActionMapping mapping, FinanceFormBase form, HttpServletRequest request, HttpServletResponse response, ShowFinanceOperation operation)
			throws Exception
	{
		return nextStepShowFinancesStatus(mapping, form, request, response, operation);
	}


}
