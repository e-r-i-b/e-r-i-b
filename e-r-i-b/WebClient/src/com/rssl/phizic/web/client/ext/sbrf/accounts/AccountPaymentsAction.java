package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.operations.account.GetAccountAbstractOperation;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.finances.targets.GetTargetOperation;
import com.rssl.phizic.operations.payment.ListTemplatesOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.common.forms.doc.CreationType;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author filimonova
 * @ created 10.03.2011
 * @ $Author$
 * @ $Revision$
 *
 * Список шаблонов и платежей по счету\вкладу
 */

public class AccountPaymentsAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
    {
	    Map<String, String> map = new HashMap<String, String>();
		map.put("button.filter", "start");
		return map;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
    {
	    AccountPaymentsForm frm = (AccountPaymentsForm) form;
	    Long linkId = frm.getId();

	    frm.setAccountOperation(true);
	    GetAccountAbstractOperation operation = createOperation(GetAccountAbstractOperation.class);
		operation.initialize(linkId);
	    updateFormData(frm, operation);

	    if (operation.isUseStoredResource())
	    {
		    saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) operation.getAccount().getAccount()));
	    }

	    return mapping.findForward(FORWARD_START);
    }

	private void updateFormData(AccountPaymentsForm form, GetAccountAbstractOperation operation) throws BusinessException, BusinessLogicException
	{
		AccountLink accountLink = operation.getAccount();
		form.setAccountLink(accountLink);
		form.setCardOperation(false);

	    GetAccountsOperation operationAccounts = createOperation(GetAccountsOperation.class);
		List<AccountLink> otherAccountsLinks = operationAccounts.getActiveAccounts();
		if (otherAccountsLinks.contains(accountLink))
		{
			otherAccountsLinks.remove(accountLink);
		}
		form.setOtherAccounts(otherAccountsLinks);

		if (checkAccess(GetTargetOperation.class))
		{
			GetTargetOperation targetOperation = createOperation(GetTargetOperation.class);
			form.setTarget(targetOperation.getTargetByAccountId(accountLink.getId()));
		}

		if (checkAccess(ListTemplatesOperation.class))
		{
			ListTemplatesOperation templateOperation = createOperation(ListTemplatesOperation.class);
			templateOperation.initialize(CreationType.internet, accountLink.getNumber());
			form.setTemplates(templateOperation.getEntity());
		}
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		AccountPaymentsForm frm = (AccountPaymentsForm) form;
		return mapping.getPath().concat((frm.getTarget()!=null?  "/target" : ""));
	}
}
