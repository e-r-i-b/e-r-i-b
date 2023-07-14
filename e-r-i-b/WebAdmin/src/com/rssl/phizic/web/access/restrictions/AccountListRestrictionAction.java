package com.rssl.phizic.web.access.restrictions;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.access.restrictions.AccountListRestrictionOperation;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Roshka
 * @ created 19.04.2006
 * @ $Author$
 * @ $Revision$
 */
public class AccountListRestrictionAction extends ResourceListResstrictionActionBase<AccountListRestrictionOperation>//TODO operationQuery??
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AccountListRestrictionForm frm = (AccountListRestrictionForm) form;

		AccountListRestrictionOperation operation = createOperation(frm);

		List<AccountLink> accountLinks = operation.getRestrictionData().getAccountLinks();
		String[] selectedIds = new String[accountLinks.size()];
		for(int i = 0; i < accountLinks.size(); i++)
		{
			selectedIds[i] = accountLinks.get(i).getId().toString();
		}

		frm.setSelectedIds(selectedIds);
		frm.setAccounts(operation.getAllAccountLinks());

		return super.start(mapping, form, request, response);
	}

	protected AccountListRestrictionOperation createOperation(ActionForm form) throws BusinessException, BusinessLogicException
	{
		AccountListRestrictionForm frm = (AccountListRestrictionForm) form;
		AccountListRestrictionOperation operation = createOperation(AccountListRestrictionOperation.class);
		operation.initialize(frm.getLoginId(), frm.getServiceId(), frm.getOperationId());
		return operation;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AccountListRestrictionForm frm = (AccountListRestrictionForm) form;
		AccountListRestrictionOperation operation = createOperation(frm);
		List<Long> selectedIds = getSelectedIds(form);
		operation.setAccountLinks(selectedIds);
		operation.save();
		return super.save(mapping, form, request, response);
	}
}
