package com.rssl.phizic.web.common.dictionaries.groupsRisk;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.groupsRisk.ListGroupRiskOperation;
import com.rssl.phizic.operations.groupsRisk.RemoveGroupRiskOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author basharin
 * @ created 01.08.2012
 * @ $Author$
 * @ $Revision$
 * экшен просмотра групп риска
 */

public class ListGroupRiskAction  extends ListActionBase
{
	public static final String FORWARD_SUCCESS = "Success";

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListGroupRiskOperation operation = createOperation(ListGroupRiskOperation.class, "GroupRiskManagment");
		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListGroupRiskForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveGroupRiskOperation.class, "GroupRiskManagment");
	}

	protected ActionMessages doRemove(RemoveEntityOperation op, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		RemoveGroupRiskOperation operation = (RemoveGroupRiskOperation) op;
		try
		{
			operation.initialize(id);
			operation.remove();
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(currentRequest(), e);
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		catch (BusinessException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Невозможно удалить группу риска " + operation.getEntity().getName() + ".", false));
		}
		return msgs;
	}
	
}
