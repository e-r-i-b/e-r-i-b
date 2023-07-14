package com.rssl.phizic.web.errors;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.errors.ListErrorMessageOperation;
import com.rssl.phizic.operations.errors.RemoveErrorMessageOperation;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author gladishev
 * @ created 16.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListErrorMessageAction extends SaveFilterParameterAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListErrorMessageOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveErrorMessageOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListErrorMessageForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("regExp",    filterParams.get("regExp"));
		query.setParameter("errorType", filterParams.get("errorType"));
		query.setParameter("system",    filterParams.get("system"));
		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			return super.doRemove(operation, id);
		}
		catch (BusinessException e)
		{
			ActionMessage error = new ActionMessage(
					"Невозможно удалить сообщение об ошибке с id=" + operation.getEntity());

			msgs.add(ActionMessages.GLOBAL_MESSAGE, error);
		}
		return msgs;
	}
}
