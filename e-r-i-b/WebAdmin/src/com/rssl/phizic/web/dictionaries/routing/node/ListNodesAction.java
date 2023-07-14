package com.rssl.phizic.web.dictionaries.routing.node;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.routing.node.ListNodesOperation;
import com.rssl.phizic.operations.dictionaries.routing.node.RemoveNodeOperation;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author akrenev
 * @ created 08.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class ListNodesAction extends SaveFilterParameterAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListNodesOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveNodeOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListNodesForm.FILTER_FORM;
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		RemoveNodeOperation op = (RemoveNodeOperation) operation;
		try
		{
			super.doRemove(operation, id);
		}
		catch (BusinessLogicException e)
		{
			ActionMessage message = new ActionMessage("Узел " + op.getEntity().getName() + " не может быть удален из справочника, так как имеются адаптеры, использующие его.", false);
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		return msgs;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("name", filterParams.get("name"));
		query.setParameter("URL",  filterParams.get("URL"));
		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}
}
