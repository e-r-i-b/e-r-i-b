package com.rssl.phizic.web.finances;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.finances.configuration.ListCardOperationCategoryOperation;
import com.rssl.phizic.operations.finances.configuration.RemoveCardOperationCategoryOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author koptyaev
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ListCardOperationCategoryAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListCardOperationCategoryOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveCardOperationCategoryOperation.class);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();

		try
		{
			super.doRemove(operation, id);
		}
		catch (ConstraintViolationException e)
		{
			CardOperationCategory category = (CardOperationCategory)operation.getEntity();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Вы не можете удалить категорию  \""+category.getName() +"\", т.к. к ней привязаны операции", false));
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		return msgs;
	}
}
