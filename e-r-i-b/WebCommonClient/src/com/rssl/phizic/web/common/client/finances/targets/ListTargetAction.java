package com.rssl.phizic.web.common.client.finances.targets;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.StoredAccount;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.finances.targets.ListTargetOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForward;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Ёкшен просмотра списка целей клиента
 * @author lepihina
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class ListTargetAction extends ListActionBase
{
	private static final String FORWARD_SELECT = "Select";
	private static final Long COUNT = 3L;

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListTargetOperation operation = createOperation(ListTargetOperation.class);
		operation.initialize();
		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListTargetForm.FILTER_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		ListTargetForm frm = (ListTargetForm) form;
		ListTargetOperation op = (ListTargetOperation) operation;
		List<AccountTarget> targets = op.getTargets();

		List<AccountLink> accountLinksForAbstract = new ArrayList<AccountLink>();

		for (AccountTarget target : targets)
		{
			if (op.needAbstruct(target))
				accountLinksForAbstract.add(target.getAccountLink());
		}
		frm.setTargets(targets);
		setAccountAbstract(frm, accountLinksForAbstract);
	}

	private void setAccountAbstract(ListTargetForm form, List<AccountLink> accountLinks) throws BusinessException, BusinessLogicException
	{
		GetAccountAbstractExtendedOperation accountAbstractOperation = createOperation(GetAccountAbstractExtendedOperation.class);
		accountAbstractOperation.initialize(accountLinks);

		form.setAccountAbstract(accountAbstractOperation.getAccountAbstract(COUNT));
		form.setAccountAbstractErrors(accountAbstractOperation.getAccountAbstractMsgErrorMap());

		for (AccountLink link : accountLinks)
		{
			if (link.getAccount() instanceof StoredAccount)
			{
				saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) link.getAccount()));
				break;
			}
		}
	}

	protected ActionForward createActionForward(ListFormBase form) throws BusinessException
	{
		ListTargetForm frm = (ListTargetForm) form;
		if (frm.getTargets().isEmpty())
		{
			return getCurrentMapping().findForward(FORWARD_SELECT);
		}
		else
		{
			return getCurrentMapping().findForward(FORWARD_START);
		}
	}
}
