package com.rssl.phizic.web.client.securities;

import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.operations.securities.ListSecuritiesOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 */

public class ListSecuritiesAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListSecuritiesOperation operation = createOperation(ListSecuritiesOperation.class);
		ListSecuritiesForm frm = (ListSecuritiesForm) form;
		operation.initialize();

		List<SecurityAccountLink> securityLinks = operation.getSecurityAccountLinksInSystem();
		frm.setSecurityLinks(securityLinks);
		frm.setSecurityAccounts(operation.getSecurityAccounts());

		if(operation.isUseStoredResource())
			saveInactiveESMessage(request, operation.getInactiveMessage());

		if (operation.isBackError()){
			frm.setBackError(true);
			saveMessage(request, operation.getErrorMessage());
		}
		frm.setUseStoredResource(operation.isUseStoredResource());
		return mapping.findForward(FORWARD_SHOW);
	}
}

