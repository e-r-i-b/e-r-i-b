package com.rssl.phizic.web.dictionaries.billing;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.billing.ListBillingsOperation;
import com.rssl.phizic.operations.dictionaries.billing.RemoveBillingOperation;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author akrenev
 * @ created 10.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class ListBillingsAction extends SaveFilterParameterAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListBillingsOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveBillingOperation.class);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		RemoveBillingOperation op = (RemoveBillingOperation) operation;

		if (!op.hasProviders(id))
		{
			try
			{
				super.doRemove(operation, id);
			}
			catch (BusinessLogicException e)
			{
				ActionMessage message = new ActionMessage(e.getMessage(), false);
				msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
			}
		}
		else
		{
			ActionMessage message = new ActionMessage("Не возможно удалить биллинговую систему, имеются привязанные к ней поставщики", false);
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		return msgs;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListBillingsForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("name", filterParams.get("name"));
		query.setParameter("code",  filterParams.get("code"));
		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}
}